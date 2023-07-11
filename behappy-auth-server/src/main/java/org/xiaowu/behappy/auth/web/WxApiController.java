package org.xiaowu.behappy.auth.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.xiaowu.behappy.api.common.vo.MemberResponseVo;
import org.xiaowu.behappy.api.member.feign.MemberFeignService;
import org.xiaowu.behappy.auth.config.WxConfigProperties;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.result.R;
import org.xiaowu.behappy.common.core.utils.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.xiaowu.behappy.api.common.constant.AuthServerConstant.LOGIN_USER;

/**
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/ucenter/wx")
public class WxApiController {

    private final MemberFeignService memberFeignService;

    private final WxConfigProperties wxConfigProperties;

    /**
     * 微信回调后，我们再重定向到auth.gulimall.com下，保证session共享在同一个顶级域名下
     * 否则微信登录后，其他域名无法获取到统一session
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public RedirectView redirect(String code, String state) {
        String url = "http://auth.gulimall.com/api/ucenter/wx/recallback?code=%s&state=%s".formatted(code, state);
        return new RedirectView(url, true);
    }

    /**
     * 获取扫码人的信息，添加数据
     *
     * @return
     */
    @GetMapping(value = "/recallback")
    public String callback(String code, String state, HttpSession session) throws Exception {

        // 1、获取授权票据
        log.debug("state: {} ,code: {}", state, code);
        if (StrUtil.isEmpty(state) || StrUtil.isEmpty(code)) {
            throw new GulimallException(BizCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        try {
            //TODO 从redis中将state获取出来，和当前传入的state作比较; 如果一致则放行，如果不一致则抛出异常：非法访问

            //向认证服务器发送请求换取access_token
            //2、拿着code请求 微信固定的地址，得到两个 access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //拼接三个参数：id 秘钥 和 code 值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    wxConfigProperties.getAppId(),
                    wxConfigProperties.getAppSecret(),
                    code
            );

            String accessTokenInfo = HttpClientUtil.doHttpGet(accessTokenUrl, null, null);
            R r = memberFeignService.weixinLogin(accessTokenInfo);
            if (r.getCode() == 0) {
                MemberResponseVo data = r.getData("data", new TypeReference<MemberResponseVo>() {
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

        } catch (Exception e) {
            log.error("WxApiController - callback: ", e);
        }
        return "redirect:http://auth.gulimall.com/login.html";
    }

    /**
     * 用户点击微信登录后，会走这里 -> 生成微信扫描二维码
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/login")
    public String getWxCode() throws UnsupportedEncodingException {

        //微信开发平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncoder编码
        String redirectUrl = wxConfigProperties.getRedirectUrl();
        redirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);

        // state：企业可以填写a-zA-Z0-9的参数值，长度不可超过128个字节
        // 关于state一种比较有效的用法是： 在授权开始前生成并写入用户的 cookie（还需要加密），授权完成时，比对 url 与 cookie 中的 state，如果不一致便可判定为非法请求
         String state = UUID.randomUUID().toString().replaceAll("-", "");
        // TODO csrf攻击：用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        // 键： "wechar-open-state-" + httpServletRequest.getSession().getId()
        // 值： satte
        // 过期时间： 30分钟
        // 生成qrcodeUrl

        //设置%s中的值
        String url = String.format(
                baseUrl,
                wxConfigProperties.getAppId(),
                redirectUrl,
                state
        );

        //重定向到请求微信地址
        return "redirect:" + url;
    }
}
