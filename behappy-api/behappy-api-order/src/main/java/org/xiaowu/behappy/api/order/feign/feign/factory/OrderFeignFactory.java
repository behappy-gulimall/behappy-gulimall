package org.xiaowu.behappy.api.order.feign.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.order.feign.feign.OrderFeignService;
import org.xiaowu.behappy.api.order.feign.feign.impl.OrderFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class OrderFeignFactory implements FallbackFactory<OrderFeignService> {

    @Override
    public OrderFeignService create(Throwable cause) {
        OrderFeignImpl orderFeign = new OrderFeignImpl();
        orderFeign.setCause(cause);
        return orderFeign;
    }
}
