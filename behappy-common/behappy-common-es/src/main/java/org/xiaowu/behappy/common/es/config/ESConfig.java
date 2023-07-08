package org.xiaowu.behappy.common.es.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
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
    private String host = "127.0.0.1";

    /**
     * http/https
     */
    private String scheme = "http";

    /**
     * 9200
     */
    private int port = 9200;

    /**
     * userName
     */
    private String username = StrUtil.EMPTY;

    /**
     * passWord
     */
    private String password = StrUtil.EMPTY;

    /**
     * 一次最多接收MaxTotal次请求 default:200
     */
    private int connectNum = 200;

    /**
     * 一个服务每次能并行接收的请求数量 default:100
     */
    private int connectPerRoute = 100;

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


    @Bean
    @Scope("singleton")
    public RequestOptions defaultRequestOptions() {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        return builder.build();
    }
}

