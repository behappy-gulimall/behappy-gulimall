package org.xiaowu.behappy.thirdparty.config;

import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import lombok.AllArgsConstructor;

/**
 * OssCredentials
 * @author xiaowu
 */
@AllArgsConstructor
public class OssCredentials implements CredentialsProvider {

    private String accessKeyId;

    private String accessKeySecret;

    @Override
    public Credentials getCredentials() {
        return new DefaultCredentials(accessKeyId, accessKeySecret);
    }

    @Override
    public void setCredentials(Credentials credentials) {

    }
}
