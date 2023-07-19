package org.xiaowu.behappy.api.seckill.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.seckill.feign.SeckillFeignService;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author xiaowu
 */
@Slf4j
public class SeckillFeignImpl implements SeckillFeignService {

    @Setter
    Throwable cause;

    @Override
    public R getSkuSeckilInfo(Long skuId) {
        log.error("SeckillFeignImpl - getSkuSeckilInfo: {}", skuId);
        return R.error(cause);
    }
}
