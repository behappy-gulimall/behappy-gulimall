package org.xiaowu.behappy.gateway.filter;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.xiaowu.behappy.gateway.constant.ConstantFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 94391
 * @calssName LogFilter
 * @Description 全局日志打印, 请求日志以及返回日志, 并在返回结果日志中添加请求时间
 */
@Slf4j
public class GlobalLogFilter implements GlobalFilter, Ordered {
    private final int order;
    private static final String REQUEST_PREFIX = "\n--------------------------------- Request  Info -----------------------------";
    private static final String REQUEST_TAIL = "\n-----------------------------------------------------------------------------";
    private static final String RESPONSE_PREFIX = "\n--------------------------------- Response Info -----------------------------";
    private static final String RESPONSE_TAIL = "\n-------------------------------------------------------------------------->>>";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        StringBuilder reqMsg = new StringBuilder();
        StringBuilder resMsg = new StringBuilder();
        // 获取请求信息
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress address = request.getRemoteAddress();
        String method = request.getMethod().name();
        URI uri = request.getURI();
        HttpHeaders headers = request.getHeaders();
        // 获取请求body
        Object cachedRequestBodyObject = exchange.getAttributeOrDefault(ConstantFilter.CACHED_REQUEST_BODY_OBJECT_KEY, new byte[0]);
        byte[] body = (byte[]) cachedRequestBodyObject;
        String params = new String(body);
        // 获取请求query
        MultiValueMap<String, String> queryMap = request.getQueryParams();
        String query = JSON.toJSONString(queryMap);
        // 拼接请求日志
        reqMsg.append(REQUEST_PREFIX);
//        reqMsg.append("\n header=").append(headers);
        reqMsg.append("\n query=").append(query);
        reqMsg.append("\n params=").append(params);
//        reqMsg.append("\n address=").append(address.getHostName()).append(address.getPort());
//        reqMsg.append("\n method=").append(method);
        reqMsg.append("\n url=").append(uri.getPath());
        reqMsg.append(REQUEST_TAIL);
        log.info(reqMsg.toString()); // 打印入参日志
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        resMsg.append(RESPONSE_PREFIX);
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        resMsg.append("\n status=").append(this.getStatusCode());
                        HttpHeaders responseHeaders = this.getHeaders();

                        if (responseHeaders.getContentType().equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
                            String responseResult = new String(content, StandardCharsets.UTF_8);
                            resMsg.append("\n responseResult=").append(responseResult);
                        }
//                        resMsg.append("\n header=").append(headers1);
                        resMsg.append(RESPONSE_TAIL);
                        // 计算请求时间
                        long end = System.currentTimeMillis();
                        long time = end - start;
                        resMsg.append("耗时ms:").append(time);
                        log.info(resMsg.toString()); // 打印结果日志
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public GlobalLogFilter(int order) {
        this.order = order;
    }
}
