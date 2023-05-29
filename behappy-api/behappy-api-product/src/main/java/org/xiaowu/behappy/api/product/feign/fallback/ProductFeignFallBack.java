package org.xiaowu.behappy.api.product.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.product.feign.ProductFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class ProductFeignFallBack implements ProductFeignService {

    @Setter
    Throwable cause;

    @Override
    public R info(Long skuId) {
        log.error("ProductFeignFallBack - info: {}", skuId);
        return R.error(cause);
    }
}
