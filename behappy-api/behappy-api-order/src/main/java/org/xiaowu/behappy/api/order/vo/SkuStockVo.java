package org.xiaowu.behappy.api.order.vo;

import lombok.Data;

/**
 * @author xiaowu
 * @Description: 库存vo
 **/

@Data
public class SkuStockVo {

    private Long skuId;

    private Boolean hasStock;

}
