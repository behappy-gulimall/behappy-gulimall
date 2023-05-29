package org.xiaowu.behappy.api.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiaowu.behappy.api.product.feign.factory.ProductFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constants.ServiceConstants.PRODUCT_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = PRODUCT_SERVICE,
        path = "/product",
        fallbackFactory = ProductFeignFactory.class)
public interface ProductFeignService {

    /**
     *  /product/skuinfo/info/{skuId}
     *
     *
     *   1)、让所有请求过网关；
     *          1、@FeignClient("behappy-gateway")：给behappy-gateway所在的机器发请求
     *          2、/api/product/skuinfo/info/{skuId}
     *   2）、直接让后台指定服务处理
     *          1、@FeignClient("behappy-product")
     *          2、/product/skuinfo/info/{skuId}
     *
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

}
