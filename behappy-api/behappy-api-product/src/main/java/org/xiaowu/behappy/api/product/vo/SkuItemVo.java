package org.xiaowu.behappy.api.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaowu
 */
@Data
public class SkuItemVo {

    /**
     * 1、sku基本信息的获取  pms_sku_info
     */
    private SkuInfoEntityVo info;

    private boolean hasStock = true;

    /**
     * 2、sku的图片信息    pms_sku_images
     */
    private List<SkuImagesEntityVo> images;

    /**
     * 3、获取spu的销售属性组合
     */
    private List<SkuItemSaleAttrVo> saleAttr;

    /**
     * 4、获取spu的介绍
     */
    private SpuInfoDescEntityVo desc;

    /**
     * 5、获取spu的规格参数信息
     */
    private List<SpuItemAttrGroupVo> groupAttrs;

    /**
     * 6、秒杀商品的优惠信息
     */
    private SeckillSkuVo seckillSkuVo;

}
