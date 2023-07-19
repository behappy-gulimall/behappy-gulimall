package org.xiaowu.behappy.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.product.vo.SkuItemSaleAttrVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.product.entity.SkuSaleAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> getSkuSaleAttrValuesAsStringList(Long skuId);

    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId);
}

