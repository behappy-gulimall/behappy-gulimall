package org.xiaowu.behappy.api.member.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class MemberFeignFallBack implements MemberFeignService {

    @Setter
    Throwable cause;

    @Override
    public R info(Long id) {
        log.error("MemberFeignFallBack - info: {}", id);
        return R.error(cause);
    }
}
