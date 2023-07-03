package org.xiaowu.behappy.api.order.vo;

import lombok.Data;

@Data
public class SubmitOrderResponseVo {

    private OrderVo order;

    /** 错误状态码 **/
    private Integer code;

}
