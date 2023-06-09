package org.xiaowu.behappy.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.product.vo.SkuHasStockVo;
import org.xiaowu.behappy.api.ware.vo.WareSkuLockVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:51:24
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    boolean orderLockStock(WareSkuLockVo vo);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

