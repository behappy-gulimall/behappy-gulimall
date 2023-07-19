package org.xiaowu.behappy.api.cart.feign.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.cart.feign.feign.CartFeignService;
import org.xiaowu.behappy.api.cart.feign.feign.impl.CartFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class CartFeignFactory implements FallbackFactory<CartFeignService> {

    @Override
    public CartFeignService create(Throwable cause) {
        CartFeignImpl cartFeign = new CartFeignImpl();
        cartFeign.setCause(cause);
        return cartFeign;
    }
}
