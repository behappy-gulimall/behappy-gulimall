package org.xiaowu.behappy.api.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.api.ware.feign.factory.WareFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

import static org.xiaowu.behappy.common.core.constants.ServiceConstants.WARE_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = WARE_SERVICE,
        path = "/ware",
        fallbackFactory = WareFeignFactory.class)
public interface WareFeignService {

    /**
     * 获取订单统计数据
     */
    @PostMapping(value = "/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);
}
