package org.xiaowu.behappy.api.coupon.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.coupon.feign.CouponFeignService;
import org.xiaowu.behappy.api.coupon.feign.fallback.CouponFeignFallBack;

/**
 * @author xiaowu
 */
@Component
public class CouponFeignFactory implements FallbackFactory<CouponFeignService> {

    @Override
    public CouponFeignService create(Throwable cause) {
        CouponFeignFallBack couponFeignFallBack = new CouponFeignFallBack();
        couponFeignFallBack.setCause(cause);
        return couponFeignFallBack;
    }
}
