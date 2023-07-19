package org.xiaowu.behappy.api.ware.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.ware.feign.WareFeignService;
import org.xiaowu.behappy.api.ware.vo.WareSkuLockVo;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
public class WareFeignImpl implements WareFeignService {

    @Setter
    Throwable cause;

    @Override
    public R getSkuHasStock(List<Long> skuIds) {
        log.error("WareFeignFallBack - getSkuHasStock: {}", skuIds);
        return R.error(cause);
    }

    @Override
    public R getFare(Long addrId) {
        log.error("WareFeignImpl - getFare: {}", addrId);
        return R.error(cause);
    }

    @Override
    public R orderLockStock(WareSkuLockVo vo) {
        log.error("WareFeignImpl - orderLockStock: {}", vo);
        return R.error(cause);
    }
}
