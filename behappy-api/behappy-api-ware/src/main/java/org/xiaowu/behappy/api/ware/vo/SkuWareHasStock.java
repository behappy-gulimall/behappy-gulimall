package org.xiaowu.behappy.api.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuWareHasStock {
    private Long skuId;
    private Integer num;
    private List<Long> wareId;
}
