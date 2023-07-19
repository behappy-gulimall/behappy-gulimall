package org.xiaowu.behappy.common.core.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * 获取当前用户信息工具类
 * @author xiaowu
 */
public class AuthContextHolder {
    /**
     * 获取当前用户id
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        //jwt从token获取userid
        return JwtHelper.getUserId(token);
    }

    /**
     * 获取当前用户名称
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        //jwt从token获取userid
        return JwtHelper.getUserName(token);
    }
}
