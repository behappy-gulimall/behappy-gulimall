package org.xiaowu.behappy.api.thirdparty.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.thirdparty.feign.ThirdPartFeignService;
import org.xiaowu.behappy.api.thirdparty.feign.impl.ThirdPartFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class ThirdPartFeignFactory implements FallbackFactory<ThirdPartFeignService> {

    @Override
    public ThirdPartFeignService create(Throwable cause) {
        ThirdPartFeignImpl thirdPartFeignImpl = new ThirdPartFeignImpl();
        thirdPartFeignImpl.setCause(cause);
        return thirdPartFeignImpl;
    }
}
