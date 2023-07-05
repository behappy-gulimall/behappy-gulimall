package org.xiaowu.behappy.api.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.xiaowu.behappy.api.seckill.feign.factory.SeckillFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.SECKILL_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = SECKILL_SERVICE,
        fallbackFactory = SeckillFeignFactory.class)
public interface SeckillFeignService {

    /**
     * 根据skuId查询商品是否参加秒杀活动
     * @param skuId
     * @return
     */
    @GetMapping(value = "/sku/seckill/{skuId}")
    R getSkuSeckilInfo(@PathVariable("skuId") Long skuId);

}
