package org.xiaowu.behappy.api.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.api.common.to.SkuReductionTo;
import org.xiaowu.behappy.api.common.to.SpuBoundTo;
import org.xiaowu.behappy.api.coupon.feign.factory.CouponFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.COUPON_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = COUPON_SERVICE,
        path = "/coupon",
        fallbackFactory = CouponFeignFactory.class)
public interface CouponFeignService {

    /**
     * 1、CouponFeignService.saveSpuBounds(spuBoundTo);
     *      1）、@RequestBody将这个对象转为json。
     *      2）、找到gulimall-coupon服务，给/coupon/spubounds/save发送请求。
     *          将上一步转的json放在请求体位置，发送请求；
     *      3）、对方服务收到请求。请求体里有json数据。
     *          (@RequestBody SpuBoundsEntity spuBounds)；将请求体的json转为SpuBoundsEntity；
     * 只要json数据模型是兼容的。双方服务无需使用同一个to
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);

    /**
     * 查询最近三天需要参加秒杀商品的信息
     * @return
     */
    @GetMapping(value = "/seckillsession/latest3DaySession")
    R getLates3DaySession();
}
