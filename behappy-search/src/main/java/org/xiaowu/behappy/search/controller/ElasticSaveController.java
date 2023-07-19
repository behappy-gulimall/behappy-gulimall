package org.xiaowu.behappy.search.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.api.common.vo.SkuEsModel;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.result.R;
import org.xiaowu.behappy.search.service.ProductSaveService;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@RequestMapping(value = "/search/save")
@RestController
@RequiredArgsConstructor
public class ElasticSaveController {

    private final ProductSaveService productSaveService;

    /**
     * 上架商品
     *
     * @param skuEsModels
     * @return
     */
    @PostMapping(value = "/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) throws IOException {
        boolean status = productSaveService.productStatusUp(skuEsModels);
        if(status){
            throw new GulimallException(BizCodeEnum.PRODUCT_UP_EXCEPTION);
        }else {
            return R.ok();
        }
    }
}
