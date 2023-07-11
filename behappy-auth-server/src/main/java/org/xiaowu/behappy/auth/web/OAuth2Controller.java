package org.xiaowu.behappy.auth.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.api.common.vo.MemberResponseVo;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.api.member.vo.SocialUser;
import org.xiaowu.behappy.auth.config.WbConfigProperties;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.result.R;
import org.xiaowu.behappy.common.core.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

import static org.xiaowu.behappy.api.common.constant.AuthServerConstant.LOGIN_USER;

/**
 * 处理社交登录请求
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final MemberFeignService memberFeignService;

    private final WbConfigProperties wbConfigProperties;

    @GetMapping(value = "/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("client_id", wbConfigProperties.getClientId());
        map.put("client_secret", wbConfigProperties.getClientSecret());
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", wbConfigProperties.getRedirectUri());
        map.put("code", code);

        try {
            //1、根据用户授权返回的code换取access_token
            String resStr = HttpClientUtil.doHttpPost("https://api.weibo.com/oauth2/access_token", map, null, null);
            JSONObject jsonObject = JSON.parseObject(resStr);
            //2、处理
            // 获取到access_token,转为通用社交登录对象
            SocialUser socialUser = jsonObject.toJavaObject(SocialUser.class);
            //知道了哪个社交用户
            //1）、当前用户如果是第一次进网站，自动注册进来（为当前社交用户生成一个会员信息，以后这个社交账号就对应指定的会员）
            //登录或者注册这个社交用户
            log.debug("OAuth2Controller - weibo - access_token: {}", socialUser.getAccess_token());
            //调用远程服务
            R oauthLogin = memberFeignService.oauthLogin(socialUser);
            if (oauthLogin.getCode() == 0) {
                MemberResponseVo data = oauthLogin.getData("data", new TypeReference<MemberResponseVo>() {
                });
                log.info("登录成功：用户信息：{}", data.toString());

                //1、第一次使用session，命令浏览器保存卡号，JSESSIONID这个cookie
                //以后浏览器访问哪个网站就会带上这个网站的cookie
                //TODO 1、默认发的令牌。当前域（解决子域session共享问题）
                //TODO 2、使用JSON的序列化方式来序列化对象到Redis中
                session.setAttribute(LOGIN_USER, data);

                //2、登录成功跳回首页
                return "redirect:http://gulimall.com";
            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }
        } catch (GulimallException | ParseException e) {
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
