package org.xiaowu.behappy.common.pay.client;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.pay.config.WxPayProperties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author xiaowu
 */
@Slf4j
public class WxHttpClient {

    private CloseableHttpClient httpClient;

    private WxPayProperties wxPayProperties;

    public void init() {
        try {
            wxPayProperties = SpringUtil.getBean(WxPayProperties.class);
            String mchId = wxPayProperties.getMchId();
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] mchId2charArray = mchId.toCharArray();
            // 这里取得classpath
            Resource certResource = new ClassPathResource(wxPayProperties.getCert());
            keystore.load(certResource.getInputStream(), mchId2charArray);
            SSLContext sslContext = SSLContexts.custom()
                    .setProtocol("TLSv1.2")
                    .loadKeyMaterial(keystore, mchId2charArray)
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
        } catch (KeyManagementException | NoSuchAlgorithmException | CertificateException | KeyStoreException |
                 IOException | UnrecoverableKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void close() {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException ignored) {
        }
    }

    public String execute(String url, Map<String, String> paramMap) throws Exception {
        HttpPost http = new HttpPost(url);
        // set http post,put param
        if (MapUtil.isNotEmpty(paramMap)) {
            String paramXml = WXPayUtil.generateSignedXml(paramMap, wxPayProperties.getMchKey());
            http.setEntity(new StringEntity(paramXml, UTF_8));
        }
        try (CloseableHttpResponse response = httpClient.execute(http)) {
            int statusCode = response.getCode();
            if (HttpStatus.SC_OK == statusCode) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    // 响应内容
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            } else if (StrUtil.startWith(String.valueOf(statusCode), "20")) {
                return StrUtil.EMPTY;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        log.error("WxHttpClient - execute: {}", "微信支付调用失败");
        throw new GulimallException(BizCodeEnum.WX_PAY_EXCEPTION);
    }
}
