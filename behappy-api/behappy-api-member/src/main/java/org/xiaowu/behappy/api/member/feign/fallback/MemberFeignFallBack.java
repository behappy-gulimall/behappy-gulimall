package org.xiaowu.behappy.api.member.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.api.member.vo.SocialUser;
import org.xiaowu.behappy.api.member.vo.UserLoginVo;
import org.xiaowu.behappy.api.member.vo.UserRegisterVo;
import org.xiaowu.behappy.common.core.result.R;

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

    @Override
    public R register(UserRegisterVo vo) {
        log.error("MemberFeignFallBack - regist: {}", vo);
        return R.error(cause);
    }

    @Override
    public R login(UserLoginVo vo) {
        log.error("MemberFeignFallBack - login: {}", vo);
        return R.error(cause);
    }

    @Override
    public R oauthLogin(SocialUser socialUser) throws Exception {
        log.error("MemberFeignFallBack - oauthLogin: {}", socialUser);
        return R.error(cause);
    }
}
