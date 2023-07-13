package org.xiaowu.behappy.product.web;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xiaowu.behappy.api.product.vo.SkuItemVo;
import org.xiaowu.behappy.product.service.SkuInfoService;

import java.util.concurrent.ExecutionException;

/**
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final SkuInfoService skuInfoService;

    /**
     * 展示当前sku的详情
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {

        log.debug("准备查询" + skuId + "详情");

        SkuItemVo vos = skuInfoService.item(skuId);

        model.addAttribute("item",vos);

        return "item";
    }
}
