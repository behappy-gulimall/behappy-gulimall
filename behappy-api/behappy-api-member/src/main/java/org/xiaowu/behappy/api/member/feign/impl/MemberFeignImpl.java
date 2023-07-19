package org.xiaowu.behappy.api.member.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.api.member.vo.SocialUser;
import org.xiaowu.behappy.api.member.vo.UserLoginVo;
import org.xiaowu.behappy.api.member.vo.UserRegisterVo;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author xiaowu
 */
@Slf4j
public class MemberFeignImpl implements MemberFeignService {

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
    public R oauthLogin(SocialUser socialUser) {
        log.error("MemberFeignFallBack - oauthLogin: {}", socialUser);
        return R.error(cause);
    }

    @Override
    public R getAddress(Long memberId) {
        log.error("MemberFeignImpl - getAddress: {}", memberId);
        return R.error(cause);
    }

    @Override
    public R weixinLogin(String accessTokenInfo) {
        log.error("MemberFeignImpl - weixinLogin: {}", accessTokenInfo);
        return R.error(cause);
    }
}
