package org.xiaowu.behappy.api.cart.feign.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.cart.feign.feign.CartFeignService;
import org.xiaowu.behappy.api.common.vo.OrderItemVo;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
public class CartFeignImpl implements CartFeignService {

    @Setter
    Throwable cause;

    @Override
    public R getCurrentCartItems() {
        log.error("CartFeignImpl - getCurrentCartItems: ", cause);
        return R.error(cause);
    }
}
