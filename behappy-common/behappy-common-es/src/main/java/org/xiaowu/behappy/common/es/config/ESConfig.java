package org.xiaowu.behappy.common.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author 小五
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.config")
public class ESConfig {

    /**
     * 127.0.0.1
     */
    private String host;

    /**
     * http/https
     */
    private String scheme;

    /**
     * 9200
     */
    private int port;

    /**
     * userName
     */
    private String username;

    /**
     * passWord
     */
    private String password;

    /**
     * 30
     */
    private Integer connectNum;

    /**
     * 10
     */
    private Integer connectPerRoute;

    public HttpHost httpHost() {
        return new HttpHost(host, port, scheme);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ESClientSpringFactory getFactory() {
        return ESClientSpringFactory.
                build(httpHost(), connectNum, connectPerRoute, username, password);
    }

    @Bean
    @Scope("singleton")
    public RestClient restClient() {
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient restHighLevelClient() {
        return getFactory().getRhlClient();
    }

}

