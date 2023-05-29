package org.xiaowu.behappy.api.coupon.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.common.to.SkuReductionTo;
import org.xiaowu.behappy.api.common.to.SpuBoundTo;
import org.xiaowu.behappy.api.coupon.feign.CouponFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class CouponFeignFallBack implements CouponFeignService {

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
}
