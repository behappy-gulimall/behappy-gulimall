package org.xiaowu.behappy.api.thirdparty.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.thirdparty.feign.ThirdPartFeignService;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class ThirdPartFeignFallBack implements ThirdPartFeignService {

    @Setter
    Throwable cause;

    @Override
    public R sendCode(String phone, String code) {
        log.error("ThirdPartFeignFallBack - sendCode: {}-{}", phone, code);
        return R.error(cause);
    }
}
