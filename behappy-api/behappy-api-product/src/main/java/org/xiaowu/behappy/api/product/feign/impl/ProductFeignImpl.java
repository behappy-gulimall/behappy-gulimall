package org.xiaowu.behappy.api.product.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.product.feign.ProductFeignService;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author xiaowu
 */
@Slf4j
public class ProductFeignImpl implements ProductFeignService {

    @Setter
    Throwable cause;

    @Override
    public R info(Long skuId) {
        log.error("ProductFeignFallBack - info: {}", skuId);
        return R.error(cause);
    }

    @Override
    public R attrInfo(Long attrId) {
        log.error("ProductFeignFallBack - attrInfo: {}", attrId);
        return R.error(cause);
    }

    @Override
    public R getInfo(Long skuId) {
        log.error("ProductFeignFallBack - getInfo: {}", skuId);
        return R.error(cause);
    }

    @Override
    public R getSkuSaleAttrValues(Long skuId) {
        log.error("ProductFeignFallBack - getSkuSaleAttrValues: {}", skuId);
        return R.error(cause);
    }

    @Override
    public R getPrice(Long skuId) {
        log.error("ProductFeignFallBack - getPrice: {}", skuId);
        return R.error(cause);
    }

    @Override
    public R getSpuInfoBySkuId(Long skuId) {
        log.error("ProductFeignImpl - getSpuInfoBySkuId: {}", skuId);
        return R.error(cause);
    }

    @Override
    public R getSkuInfo(Long skuId) {
        return null;
    }
}
