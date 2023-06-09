package org.xiaowu.behappy.api.member.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.api.member.feign.fallback.MemberFeignFallBack;

/**
 * @author xiaowu
 */
@Component
public class MemberFeignFactory implements FallbackFactory<MemberFeignService> {

    @Override
    public MemberFeignService create(Throwable cause) {
        MemberFeignFallBack memberFeignFallBack = new MemberFeignFallBack();
        memberFeignFallBack.setCause(cause);
        return memberFeignFallBack;
    }
}
