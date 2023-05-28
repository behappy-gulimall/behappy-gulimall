package org.xiaowu.behappy.api.ware.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.ware.feign.WareFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class WareFeignFallBack implements WareFeignService {

    @Setter
    Throwable cause;

    @Override
    public R getSkuHasStock(List<Long> skuIds) {
        log.error("WareFeignFallBack - getSkuHasStock: {}", skuIds);
        return R.error(cause);
    }
}
