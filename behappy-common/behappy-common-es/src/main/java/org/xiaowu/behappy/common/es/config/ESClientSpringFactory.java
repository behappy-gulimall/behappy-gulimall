package org.xiaowu.behappy.common.es.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * @author 小五
 */
@Slf4j
public class ESClientSpringFactory {

    /*-----------------------默认配置---------------------*/
    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    /*-----------------------默认配置---------------------*/

    /*-----------------------auth---------------------*/
    public static String USER_NAME ;
    public static String PASS_WORD;
    /*-----------------------auth---------------------*/

    /**
     * 以下两参数含义
     * 服务1要通过Fluent调用服务2的接口。服务1发送了400个请求，
     * 但由于Fluent默认只支持maxPerRoute=100，MaxTotal=200，比如接口执行时间为500ms，
     * 由于maxPerRoute=100，所以要分为100,100,100,100分四批来执行，全部执行完成需要2000ms。
     * 而如果maxPerRoute设置为400，全部执行完需要500ms。在这种情况下（提供并发能力时）就要对这两个参数进行设置了
     */
    /**
     * 一次最多接收MaxTotal次请求
     */
    public static int MAX_CONN_TOTAL;

    /**
     * 一个服务每次能并行接收的请求数量
     */
    public static int MAX_CONN_PER_ROUTE;

    private static HttpHost HTTP_HOST;
    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;

    private static final ESClientSpringFactory ES_CLIENT_SPRING_FACTORY = new ESClientSpringFactory();

    private ESClientSpringFactory() {
    }

    public static ESClientSpringFactory build(HttpHost httpHost,
                                              int maxConnectNum,
                                              int maxConnectPerRoute,
                                              String username,
                                              String password) {
        HTTP_HOST = httpHost;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        USER_NAME = username;
        PASS_WORD = password;
        return ES_CLIENT_SPRING_FACTORY;
    }

    public void init() {
        builder = RestClient.builder(HTTP_HOST);
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        log.debug("init factory");
    }

    // 配置连接时间延时
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    // 使用异步httpclient时设置并发连接数
    @SneakyThrows
    public void setMutiConnectConfig() {
        // 信任不安全证书
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, null);
        // 账户密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER_NAME, PASS_WORD));
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setSSLContext(sslContext);
            httpClientBuilder.setSSLStrategy(new SSLIOSessionStrategy(sslContext, new AllowAllHostnameVerifier()));
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    public RestClient getClient() {
        return restClient;
    }

    public RestHighLevelClient getRhlClient() {
        return restHighLevelClient;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException ignore) {
            }
        }
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (IOException ignore) {
            }
        }
        log.debug("close client");
    }
}
