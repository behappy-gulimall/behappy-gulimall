package org.xiaowu.behappy.api.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author xiaowu
 */
@Data
public class SkuItemSaleAttrVo {

    private Long attrId;

    private String attrName;

    private List<AttrValueWithSkuIdVo> attrValues;

}
