package org.xiaowu.behappy.api.ware.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.ware.feign.WareFeignService;
import org.xiaowu.behappy.api.ware.feign.impl.WareFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class WareFeignFactory implements FallbackFactory<WareFeignService> {

    @Override
    public WareFeignService create(Throwable cause) {
        WareFeignImpl wareFeignImpl = new WareFeignImpl();
        wareFeignImpl.setCause(cause);
        return wareFeignImpl;
    }
}
