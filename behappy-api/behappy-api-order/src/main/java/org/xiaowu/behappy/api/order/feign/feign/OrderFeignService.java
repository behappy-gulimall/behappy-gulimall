package org.xiaowu.behappy.api.order.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.api.order.feign.feign.factory.OrderFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import java.util.Map;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.ORDER_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = ORDER_SERVICE,
        path = "/order",
        fallbackFactory = OrderFeignFactory.class)
public interface OrderFeignService {

    /**
     * 分页查询当前登录用户的所有订单信息
     * @param params
     * @return
     */
    @PostMapping("/order/listWithItem")
    R listWithItem(@RequestBody Map<String, Object> params);


    @GetMapping(value = "/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);
}
