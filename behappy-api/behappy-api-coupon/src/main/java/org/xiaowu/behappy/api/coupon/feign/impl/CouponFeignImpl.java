package org.xiaowu.behappy.api.coupon.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.common.to.SkuReductionTo;
import org.xiaowu.behappy.api.common.to.SpuBoundTo;
import org.xiaowu.behappy.api.coupon.feign.CouponFeignService;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author xiaowu
 */
@Slf4j
public class CouponFeignImpl implements CouponFeignService {

    @Setter
    Throwable cause;

    @Override
    public R saveSpuBounds(SpuBoundTo spuBoundTo) {
        log.error("CouponFeignFallBack - saveSpuBounds: {}", spuBoundTo);
        return R.error(cause);
    }

    @Override
    public R saveSkuReduction(SkuReductionTo skuReductionTo) {
        log.error("CouponFeignFallBack - saveSkuReduction: {}", skuReductionTo);
        return R.error(cause);
    }

    @Override
    public R getLates3DaySession() {
        log.error("CouponFeignImpl - getLates3DaySession: ", cause);
        return R.error(cause);
    }
}
