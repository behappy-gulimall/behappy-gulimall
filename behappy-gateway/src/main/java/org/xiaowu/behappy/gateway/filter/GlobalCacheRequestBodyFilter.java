package org.xiaowu.behappy.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.server.ServerWebExchange;
import org.xiaowu.behappy.gateway.constant.ConstantFilter;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author 94391
 * @calssName AppCacheRequestBodyFilter
 * @Description 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
 */
@Slf4j
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {

    private final int order;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
         log.debug("GlobalCacheRequestBodyFilter ...");
        // 将request body中的内容 copy 一份，记录到 exchange 的一个自定义属性中
        Object cachedRequestBodyObject = exchange.getAttributeOrDefault(ConstantFilter.CACHED_REQUEST_BODY_OBJECT_KEY, null);
        // 如果已经缓存过，略过
        if (Objects.isNull(cachedRequestBodyObject)) {
            return chain.filter(exchange);
        }
        // 如果没有缓存过，获取字节数组存入 exchange 的自定义属性中
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                }).defaultIfEmpty(new byte[0])
                .doOnNext(bytes -> exchange.getAttributes().put(ConstantFilter.CACHED_REQUEST_BODY_OBJECT_KEY, bytes))
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public GlobalCacheRequestBodyFilter(int order) {
        this.order = order;
    }
}
