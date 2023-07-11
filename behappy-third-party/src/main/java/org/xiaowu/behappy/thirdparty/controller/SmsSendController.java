package org.xiaowu.behappy.thirdparty.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.result.R;
import org.xiaowu.behappy.thirdparty.service.SmsService;

/**
 * @author xiaowu
 */
@RestController
@RequestMapping(value = "/sms")
@RequiredArgsConstructor
public class SmsSendController {

    private final SmsService smsService;

    /**
     * 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @GetMapping(value = "/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        //发送验证码
        smsService.sendSms(phone,code);
        return R.ok();
    }

}
