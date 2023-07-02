package org.xiaowu.behappy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaowu.behappy.api.common.vo.MemberResponseVo;
import org.xiaowu.behappy.api.order.constant.PayConstant;
import org.xiaowu.behappy.api.order.enume.OrderStatusEnum;
import org.xiaowu.behappy.api.order.to.OrderTo;
import org.xiaowu.behappy.api.order.vo.PayAsyncVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.order.dao.OrderDao;
import org.xiaowu.behappy.order.entity.OrderEntity;
import org.xiaowu.behappy.order.entity.OrderItemEntity;
import org.xiaowu.behappy.order.entity.PaymentInfoEntity;
import org.xiaowu.behappy.order.interceptor.LoginUserInterceptor;
import org.xiaowu.behappy.order.service.OrderItemService;
import org.xiaowu.behappy.order.service.OrderService;
import org.xiaowu.behappy.order.service.PaymentInfoService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author xiaowu
 */
@Slf4j
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private final OrderItemService orderItemService;

    private final RabbitTemplate rabbitTemplate;

    private final PaymentInfoService paymentInfoService;

    private final BestPayService bestPayService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 按照订单号获取订单信息
     * @param orderSn
     * @return
     */
    @Override
    public OrderEntity getOrderByOrderSn(String orderSn) {
        OrderEntity orderEntity = this.baseMapper.selectOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
        return orderEntity;
    }

    /**
     * 查询当前用户所有订单数据
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {
        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
                        .eq("member_id",memberResponseVo.getId()).orderByDesc("create_time")
        );
        //遍历所有订单集合
        List<OrderEntity> orderEntityList = page.getRecords().stream().map(order -> {
            //根据订单号查询订单项里的数据
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>()
                    .eq("order_sn", order.getOrderSn()));
            order.setOrderItemEntityList(orderItemEntities);
            return order;
        }).collect(Collectors.toList());

        page.setRecords(orderEntityList);

        return new PageUtils(page);
    }

    /**
     * 关闭订单
     * @param orderEntity
     */
    @Override
    public void closeOrder(OrderEntity orderEntity) {
        //关闭订单之前先查询一下数据库，判断此订单状态是否已支付
        OrderEntity orderInfo = this.getOne(new QueryWrapper<OrderEntity>().
                eq("order_sn",orderEntity.getOrderSn()));

        if (orderInfo.getStatus().equals(OrderStatusEnum.CREATE_NEW.getCode())) {
            //代付款状态进行关单
            OrderEntity orderUpdate = new OrderEntity();
            orderUpdate.setId(orderInfo.getId());
            orderUpdate.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(orderUpdate);

            // 发送消息给MQ
            OrderTo orderTo = new OrderTo();
            BeanUtils.copyProperties(orderInfo, orderTo);

            try {
                //TODO 确保每个消息发送成功，给每个消息做好日志记录，(给数据库保存每一个详细信息)保存每个消息的详细信息
                rabbitTemplate.convertAndSend("order-event-exchange", "order.release.other", orderTo);
            } catch (Exception ignored) {
                //TODO 定期扫描数据库，重新发送失败的消息
            }
        }
    }

    /**
     * 处理支付宝的支付结果
     * @param asyncVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handlePayResult(PayAsyncVo asyncVo) {

        //保存交易流水信息
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(asyncVo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(asyncVo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(asyncVo.getBuyer_pay_amount()));
        paymentInfo.setSubject(asyncVo.getBody());
        paymentInfo.setPaymentStatus(asyncVo.getTrade_status());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackTime(asyncVo.getNotify_time());
        //添加到数据库中
        this.paymentInfoService.save(paymentInfo);

        //修改订单状态
        //获取当前状态
        String tradeStatus = asyncVo.getTrade_status();
        //支付成功状态
        if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
            // 获取订单号
            String orderSn = asyncVo.getOut_trade_no();
            this.updateOrderStatus(orderSn,OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
        }

        return "success";
    }


    /**
     * 修改订单状态
     * @param orderSn
     * @param code
     */
    private void updateOrderStatus(String orderSn, Integer code,Integer payType) {
        this.baseMapper.updateOrderStatus(orderSn,code,payType);
    }

    /**
     * 微信异步通知结果
     * @param notifyData
     * @return
     */
    @Override
    public String asyncNotify(String notifyData) {

        //签名效验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}",payResponse);

        //2.金额效验（从数据库查订单）
        OrderEntity orderEntity = this.getOrderByOrderSn(payResponse.getOrderId());

        //如果查询出来的数据是null的话
        //比较严重(正常情况下是不会发生的)发出告警：钉钉、短信
        if (orderEntity == null) {
            //TODO 发出告警，钉钉，短信
            throw new RuntimeException("通过订单编号查询出来的结果是null");
        }

        //判断订单状态状态是否为已支付或者是已取消
        Integer status = orderEntity.getStatus();
        if (status.equals(OrderStatusEnum.PAYED.getCode()) || status.equals(OrderStatusEnum.CANCLED.getCode())) {
            throw new RuntimeException("该订单已失效,orderNo=" + payResponse.getOrderId());
        }

        /*//判断金额是否一致,Double类型比较大小，精度问题不好控制
        if (orderEntity.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
            //TODO 告警
            throw new RuntimeException("异步通知中的金额和数据库里的不一致,orderNo=" + payResponse.getOrderId());
        }*/

        //3.修改订单支付状态
        //支付成功状态
        String orderSn = orderEntity.getOrderSn();
        this.updateOrderStatus(orderSn,OrderStatusEnum.PAYED.getCode(),PayConstant.WXPAY);

        //4.告诉微信不要再重复通知了
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

}
