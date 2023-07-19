package org.xiaowu.behappy.thirdparty.service;

/**
 * @author xiaowu
 */
public interface SmsService {

    /**
     * 发送短信
     * @param phone
     * @param code
     */
    void sendSms(String phone,String code);
}
