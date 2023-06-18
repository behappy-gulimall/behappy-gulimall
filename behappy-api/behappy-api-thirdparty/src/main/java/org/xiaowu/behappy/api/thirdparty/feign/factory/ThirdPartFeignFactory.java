package org.xiaowu.behappy.api.thirdparty.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.thirdparty.feign.ThirdPartFeignService;
import org.xiaowu.behappy.api.thirdparty.feign.fallback.ThirdPartFeignFallBack;

/**
 * @author xiaowu
 */
@Component
public class ThirdPartFeignFactory implements FallbackFactory<ThirdPartFeignService> {

    @Override
    public ThirdPartFeignService create(Throwable cause) {
        ThirdPartFeignFallBack thirdPartFeignFallBack = new ThirdPartFeignFallBack();
        thirdPartFeignFallBack.setCause(cause);
        return thirdPartFeignFallBack;
    }
}
