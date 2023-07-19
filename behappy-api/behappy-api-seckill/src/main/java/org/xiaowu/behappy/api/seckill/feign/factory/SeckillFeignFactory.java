package org.xiaowu.behappy.api.seckill.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.seckill.feign.SeckillFeignService;
import org.xiaowu.behappy.api.seckill.feign.impl.SeckillFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class SeckillFeignFactory implements FallbackFactory<SeckillFeignService> {

    @Override
    public SeckillFeignService create(Throwable cause) {
        SeckillFeignImpl seckillFeign = new SeckillFeignImpl();
        seckillFeign.setCause(cause);
        return seckillFeign;
    }
}
