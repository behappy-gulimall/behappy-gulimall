package org.xiaowu.behappy.order.service;

import org.xiaowu.behappy.api.order.vo.PayVo;

/**
 * @author xiaowu
 */
public interface AliPayService {

    /**
     * 支付方法
     * @param vo
     * @return
     */
    String pay(PayVo vo);
}
