package org.xiaowu.behappy.api.member.vo;

import lombok.Data;

/**
 * @author xiaowu
 * @Description: 社交用户信息
 **/
@Data
public class SocialUser {

    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;

}
