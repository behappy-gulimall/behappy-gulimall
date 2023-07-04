package org.xiaowu.behappy.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xiaowu.behappy.api.product.vo.SkuItemSaleAttrVo;
import org.xiaowu.behappy.product.entity.SkuSaleAttrValueEntity;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);

    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(@Param("spuId") Long spuId);
}
