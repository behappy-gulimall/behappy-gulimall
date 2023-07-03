package org.xiaowu.behappy.api.order.to;

import lombok.Data;
import org.xiaowu.behappy.api.order.vo.OrderVo;
import org.xiaowu.behappy.api.order.vo.OrderItemEntityVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaowu
 */
@Data
public class OrderCreateTo {

    private OrderVo order;

    private List<OrderItemEntityVo> orderItems;

    /** 订单计算的应付价格 **/
    private BigDecimal payPrice;

    /** 运费 **/
    private BigDecimal fare;

}
