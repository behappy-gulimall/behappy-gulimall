package org.xiaowu.behappy.api.order.feign.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.order.feign.feign.OrderFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.Map;

/**
 * @author xiaowu
 */
@Slf4j
public class OrderFeignImpl implements OrderFeignService {

    @Setter
    Throwable cause;

    @Override
    public R listWithItem(Map<String, Object> params) {
        log.error("OrderFeignImpl - listWithItem: {}", params);
        return R.error(cause);
    }

    @Override
    public R getOrderStatus(String orderSn) {
        log.error("OrderFeignImpl - getOrderStatus: {}", orderSn);
        return R.error(cause);
    }
}
