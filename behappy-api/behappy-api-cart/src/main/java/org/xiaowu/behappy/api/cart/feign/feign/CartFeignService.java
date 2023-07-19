package org.xiaowu.behappy.api.cart.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaowu.behappy.api.cart.feign.feign.factory.CartFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.CART_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = CART_SERVICE,
        fallbackFactory = CartFeignFactory.class)
public interface CartFeignService {
    /**
     * 查询当前用户购物车选中的商品项
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    R getCurrentCartItems();
}
