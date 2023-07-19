package org.xiaowu.behappy.api.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.api.member.feign.factory.MemberFeignFactory;
import org.xiaowu.behappy.api.member.vo.SocialUser;
import org.xiaowu.behappy.api.member.vo.UserLoginVo;
import org.xiaowu.behappy.api.member.vo.UserRegisterVo;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.MEMBER_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = MEMBER_SERVICE,
        path = "/member",
        fallbackFactory = MemberFeignFactory.class)
public interface MemberFeignService {

    /**
     * 根据id获取用户地址信息
     * @param id
     * @return
     */
    @RequestMapping("/memberreceiveaddress/info/{id}")
    R info(@PathVariable("id") Long id);


    @PostMapping(value = "/member/register")
    R register(@RequestBody UserRegisterVo vo);

    @PostMapping(value = "/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping(value = "/member/oauth2/login")
    R oauthLogin(SocialUser socialUser);

    /**
     * 查询当前用户的全部收货地址
     * @param memberId
     * @return
     */
    @GetMapping(value = "/memberreceiveaddress/{memberId}/address")
    R getAddress(@PathVariable("memberId") Long memberId);

    @PostMapping(value = "/member/weixin/login")
    R weixinLogin(@RequestParam("accessTokenInfo") String accessTokenInfo);
}
