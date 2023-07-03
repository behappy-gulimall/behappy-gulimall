package org.xiaowu.behappy.api.cart.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaowu.behappy.api.cart.feign.feign.factory.CartFeignFactory;
import org.xiaowu.behappy.api.common.vo.OrderItemVo;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

import static org.xiaowu.behappy.common.core.constants.ServiceConstants.CART_SERVICE;
import static org.xiaowu.behappy.common.core.constants.ServiceConstants.WARE_SERVICE;

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
