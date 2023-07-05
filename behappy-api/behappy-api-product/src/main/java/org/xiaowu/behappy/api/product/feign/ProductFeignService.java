package org.xiaowu.behappy.api.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiaowu.behappy.api.product.feign.factory.ProductFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.PRODUCT_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = PRODUCT_SERVICE,
        path = "/product",
        fallbackFactory = ProductFeignFactory.class)
public interface ProductFeignService {

    /**
     * /skuinfo/info/{skuId}
     * <p>
     * <p>
     * 1)、让所有请求过网关；
     * 1、@FeignClient("behappy-gateway")：给behappy-gateway所在的机器发请求
     * 2、/api/skuinfo/info/{skuId}
     * 2）、直接让后台指定服务处理
     * 1、@FeignClient("behappy-product")
     * 2、/skuinfo/info/{skuId}
     *
     * @return
     */
    @RequestMapping("/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);


    @GetMapping("/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);


    /**
     * 根据skuId查询sku信息
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/skuinfo/info/{skuId}")
    R getInfo(@PathVariable("skuId") Long skuId);

    /**
     * 根据skuId查询pms_sku_sale_attr_value表中的信息
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/skusaleattrvalue/stringList/{skuId}")
    R getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

    /**
     * 根据skuId查询当前商品的最新价格
     *
     * @param skuId
     * @return
     */
    @GetMapping(value = "/skuinfo/{skuId}/price")
    R getPrice(@PathVariable("skuId") Long skuId);

    /**
     * 根据skuId查询spu的信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/spuinfo/skuId/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);

    @RequestMapping("/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);

}
