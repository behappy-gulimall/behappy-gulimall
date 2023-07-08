package org.xiaowu.behappy.thirdparty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云sms配置模板
 *
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliSmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String signName = "ABC商城";

    private String templateCode = "SMS_206546316";

    private String regionId = "cn-hangzhou";

}
