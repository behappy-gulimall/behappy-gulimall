package org.xiaowu.behappy.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.order.vo.PayAsyncVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:39:13
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderEntity getOrderByOrderSn(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);

    void closeOrder(OrderEntity orderEntity);

    String handlePayResult(PayAsyncVo asyncVo);

    String asyncNotify(String notifyData);
}

