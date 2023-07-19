package org.xiaowu.behappy.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.member.vo.MemberUserLoginVo;
import org.xiaowu.behappy.api.member.vo.MemberUserRegisterVo;
import org.xiaowu.behappy.api.member.vo.SocialUser;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:49:42
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户注册
     * @param vo
     */
    void register(MemberUserRegisterVo vo);

    /**
     * 用户登录
     * @param vo
     * @return
     */
    MemberEntity login(MemberUserLoginVo vo);

    /**
     * 社交用户的登录
     * @param socialUser
     * @return
     */
    MemberEntity login(SocialUser socialUser) throws Exception;

    /**
     * 微信登录
     * @param accessTokenInfo
     * @return
     */
    MemberEntity login(String accessTokenInfo);

}

