package org.xiaowu.behappy.api.product.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.product.feign.ProductFeignService;
import org.xiaowu.behappy.api.product.feign.impl.ProductFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class ProductFeignFactory implements FallbackFactory<ProductFeignService> {

    @Override
    public ProductFeignService create(Throwable cause) {
        ProductFeignImpl productFeignImpl = new ProductFeignImpl();
        productFeignImpl.setCause(cause);
        return productFeignImpl;
    }
}
