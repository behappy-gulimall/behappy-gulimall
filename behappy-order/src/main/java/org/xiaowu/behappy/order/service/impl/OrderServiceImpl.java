package org.xiaowu.behappy.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.api.common.vo.MemberResponseVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.order.dao.OrderDao;
import org.xiaowu.behappy.order.entity.OrderEntity;
import org.xiaowu.behappy.order.entity.OrderItemEntity;
import org.xiaowu.behappy.order.interceptor.LoginUserInterceptor;
import org.xiaowu.behappy.order.service.OrderItemService;
import org.xiaowu.behappy.order.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private final OrderItemService orderItemService;

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

}
