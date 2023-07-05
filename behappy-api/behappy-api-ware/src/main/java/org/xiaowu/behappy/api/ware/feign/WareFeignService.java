package org.xiaowu.behappy.api.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.api.ware.feign.factory.WareFeignFactory;
import org.xiaowu.behappy.api.ware.vo.WareSkuLockVo;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.WARE_SERVICE;

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


    /**
     * 查询运费和收货地址信息
     * @param addrId
     * @return
     */
    @GetMapping(value = "/wareinfo/fare")
    R getFare(@RequestParam("addrId") Long addrId);


    /**
     * 锁定库存
     * @param vo
     * @return
     */
    @PostMapping(value = "/waresku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo vo);
}
