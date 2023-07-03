package org.xiaowu.behappy.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.api.cart.vo.CartItemVo;
import org.xiaowu.behappy.cart.service.CartService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartService cartService;

    /**
     * 获取当前用户的购物车商品项
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    public R getCurrentCartItems() {
        List<CartItemVo> userCartItems = cartService.getUserCartItems();
        return R.ok().setData(userCartItems);
    }
}
