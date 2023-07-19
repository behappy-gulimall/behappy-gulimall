package org.xiaowu.behappy.gateway.filter;

import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.xiaowu.behappy.common.core.constant.HeaderConstant;
import org.xiaowu.behappy.common.core.constant.OrderedConstant;
import org.xiaowu.behappy.common.core.log.Log;
import org.xiaowu.behappy.common.core.utils.EnvironmentUtils;
import org.xiaowu.behappy.common.core.utils.IpUtil;
import org.xiaowu.behappy.common.core.utils.LogHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 请求响应日志打印
 * @author xiaowu
 */
@Slf4j
@Component
public class ResponseLogFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return OrderedConstant.LOGGING_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            ServerRequest serverRequest = ServerRequest.create(exchange,
                    HandlerStrategies.withDefaults().messageReaders());
            URI requestUri = request.getURI();
            String uriQuery = requestUri.getQuery();
            HttpHeaders headers = request.getHeaders();
            MediaType mediaType = headers.getContentType();
            String schema = requestUri.getScheme();
            String method = request.getMethod().name().toUpperCase();

            // 只记录http、https请求
            if ((!"http".equals(schema) && !"https".equals(schema))) {
                return chain.filter(exchange);
            }
            final AtomicReference<String> requestBody = new AtomicReference<>();// 原始请求体
            // 排除流文件类型,比如上传的文件contentType.contains("multipart/form-data")
            if (Objects.nonNull(mediaType) && LogHelper.isUploadFile(mediaType)) {
                requestBody.set("上传文件");
                return chain.filter(exchange);
            } else {
                if ("GET".equals(method)) {
                    if (StringUtils.isNotBlank(uriQuery)) {
                        requestBody.set(uriQuery);
                    }
                } else if (headers.getContentLength() > 0){
                    return serverRequest.bodyToMono(String.class).flatMap(reqBody -> {
                        requestBody.set(reqBody);
                        // 重写原始请求
                        ServerHttpRequestDecorator requestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.putAll(super.getHeaders());
                                return httpHeaders;
                            }

                            @Override
                            public Flux<DataBuffer> getBody() {
                                NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                                DataBuffer bodyDataBuffer = nettyDataBufferFactory.wrap(reqBody.getBytes());
                                return Flux.just(bodyDataBuffer);
//                                return Flux.just(reqBody).map(bx -> exchange.getRequest().bufferFactory().wrap(bx.getBytes()));
                            }
                        };
                        ServerHttpResponseDecorator responseDecorator = getServerHttpResponseDecorator(exchange,
                                requestBody);
                        return chain.filter(exchange.mutate()
                                .request(requestDecorator)
                                .response(responseDecorator)
                                .build());
                    });
                }
                ServerHttpResponseDecorator decoratedResponse = getServerHttpResponseDecorator(exchange,
                        requestBody);
                return chain.filter(exchange.mutate()
                        .response(decoratedResponse)
                        .build());
            }

        } catch (Exception e) {
            log.error("请求响应日志打印出现异常", e);
            return chain.filter(exchange);
        }

    }

    private ServerHttpResponseDecorator getServerHttpResponseDecorator(ServerWebExchange exchange,
                                                                       AtomicReference<String> requestBody) {
        // 获取response的返回数据
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        HttpStatusCode httpStatus = originalResponse.getStatusCode();
        ServerHttpRequest request = exchange.getRequest();
        URI requestUri = request.getURI();
        String path = requestUri.getPath();

        // 封装返回体
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBuffer join = bufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);

                        String responseBody;
                        MediaType contentType = originalResponse.getHeaders().getContentType();

                        // 下载文件时 不需要打印具体响应内容 标识即可
                        if (Objects.nonNull(contentType) && LogHelper.isUploadFile(contentType)) {
                            responseBody = "下载文件";
                        } else if (LogHelper.isHtml(contentType)) {
                            responseBody = "html";
                        } else {
                            Charset charset = LogHelper.getMediaTypeCharset(contentType);
                            responseBody = new String(content, charset);
                        }

                        Log logDTO = buildLog(responseBody, requestBody.get(), httpStatus, request);
                        exchange.getSession().subscribe(webSession -> {
                            logDTO.setSessionId(webSession.getId());
                        });

                        log.info(LogHelper.toJsonString(logDTO));
                        return bufferFactory.wrap(content);
                    }));
                }
                return super.writeWith(body);
            }
        };
    }

    private Log buildLog(String responseBody, String requestBody,
                         HttpStatusCode httpStatus, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String requestId = headers.getFirst(HeaderConstant.REQUEST_ID);
        String method = request.getMethod().name().toUpperCase();
        URI requestUri = request.getURI();
        String uriQuery = requestUri.getQuery();
        String path = requestUri.getPath();
        String url = path + (StringUtils.isNotBlank(uriQuery) ? "?" + uriQuery : "");
        long handleTime = LogHelper.getHandleTime(headers);

        Log logDTO = new Log(Log.TYPE.RESPONSE);
        logDTO.setLevel(Log.LEVEL.INFO);
        logDTO.setRequestUrl(url);
        logDTO.setRequestBody(requestBody);
        logDTO.setResponseBody(responseBody);
        logDTO.setRequestMethod(method);
        if (Objects.nonNull(httpStatus)) {
            logDTO.setStatus(httpStatus.value());
        }
        logDTO.setHandleTime(handleTime);
        logDTO.setRequestId(requestId);
        logDTO.setIp(IpUtil.getClientIp(request));
        return logDTO;
    }

}
