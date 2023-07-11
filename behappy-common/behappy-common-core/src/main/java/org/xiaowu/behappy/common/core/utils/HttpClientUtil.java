package org.xiaowu.behappy.common.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.util.StringUtils;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.result.R;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("all")
@Slf4j
public class HttpClientUtil {
    private static CloseableHttpClient httpClient = null;

    static {
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .setProtocol("TLS")
                    .build();
            // 忽略ssl验证
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 总连接池数量
            connectionManager.setMaxTotal(150);
            // 设置每个主机（域名）的最大连接数（并发量大时，避免某一个使用过大而其他过小）
            connectionManager.setDefaultMaxPerRoute(10);
            // 可为每个域名设置单独的连接池数量
            // connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("xx.xx.xx.xx")), 80);
            // setConnectTimeout：设置建立连接的超时时间
            // setConnectionRequestTimeout：从连接池中拿连接的等待超时时间
            // setResponseTimeout：发出请求后等待对端应答的超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(Timeout.ofMinutes(1L))
                    .setConnectionRequestTimeout(Timeout.ofMinutes(1L))
                    .setResponseTimeout(Timeout.ofMinutes(1L))
                    .build();
            // 重试处理器，StandardHttpRequestRetryHandler
            DefaultHttpRequestRetryStrategy retryHandler = new DefaultHttpRequestRetryStrategy();

            httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryStrategy(retryHandler)
                    .build();
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static String doHttpGet(String uri, Map<String, Object> urlParams, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            // params
            if (null != urlParams && !urlParams.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> param : urlParams.entrySet()) {
                    params.add(new BasicNameValuePair(param.getKey(), StrUtil.toString(param.getValue())));
                }
                uriBuilder.setParameters(params);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // headers
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, Object> header : headers.entrySet()) {
                    httpGet.setHeader(header.getKey(), StrUtil.toString(header.getValue()));
                }
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getCode();
            // 如果HttpStatus.SC_OK == statusCode，那么entity一定不为null
            if (HttpStatus.SC_OK == statusCode) {
                // entity响应示例：
                // HTTP/1.1 200 OK
                // Date: Mon, 05 Jul 2023 10:40:00 GMT
                // Server: Apache/2.4.6 (CentOS) OpenSSL/1.0.2k-fips PHP/5.4.16
                // Content-Length: 12
                // Content-Type: text/plain; charset=UTF-8
                //
                // Hello World!
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            }else if (StrUtil.startWith(String.valueOf(statusCode), "20")){
                return StrUtil.EMPTY;
            }
        } catch (Exception e) {
            log.error("CloseableHttpClient-get-请求异常: {}", e);
            throw new GulimallException(BizCodeEnum.HTTP_ERROR_EXCEPTION);
        } finally {
            try {
                if (null != response)
                    response.close();
            } catch (IOException ignored) {
            }
        }
        log.error("CloseableHttpClient-get-请求结果错误: {}", response);
        throw new GulimallException(BizCodeEnum.HTTP_ERROR_EXCEPTION);
    }

    public static String doHttpPost(String uri, Map<String, Object> urlParams, Map<String, Object> postParams, Map<String, Object> headers) {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            // urlParams
            if (null != urlParams && !urlParams.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> param : urlParams.entrySet()) {
                    params.add(new BasicNameValuePair(param.getKey(), StrUtil.toString(param.getValue())));
                }
                uriBuilder.setParameters(params);
            }
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            // postParams
            if (null != postParams && !postParams.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> param : postParams.entrySet()) {
                    params.add(new BasicNameValuePair(param.getKey(), StrUtil.toString(param.getValue())));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);
                httpPost.setEntity(httpEntity);
            }
            // headers
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, Object> header : headers.entrySet()) {
                    httpPost.setHeader(header.getKey(), StrUtil.toString(header.getValue()));
                }
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getCode();
            // 如果HttpStatus.SC_OK == statusCode，那么entity一定不为null
            if (HttpStatus.SC_OK == statusCode) {
                // entity响应示例：
                // HTTP/1.1 200 OK
                // Date: Mon, 05 Jul 2023 10:40:00 GMT
                // Server: Apache/2.4.6 (CentOS) OpenSSL/1.0.2k-fips PHP/5.4.16
                // Content-Length: 12
                // Content-Type: text/plain; charset=UTF-8
                //
                // Hello World!
                HttpEntity entity = response.getEntity();
                // 如果
                if (null != entity) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            }else if (StrUtil.startWith(String.valueOf(statusCode), "20")){
                return StrUtil.EMPTY;
            }
        } catch (Exception e) {
            log.error("CloseableHttpClient-post-请求异常: {}", e);
            throw new GulimallException(BizCodeEnum.HTTP_ERROR_EXCEPTION);
        } finally {
            try {
                if (null != response)
                    response.close();
            } catch (IOException ignored) {
            }
        }
        log.error("CloseableHttpClient-post-请求结果错误: {}", response);
        throw new GulimallException(BizCodeEnum.HTTP_ERROR_EXCEPTION);
    }
}
