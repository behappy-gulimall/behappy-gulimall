package org.xiaowu.behappy.api.ware.vo;

import lombok.Data;
import org.xiaowu.behappy.api.common.vo.MemberAddressVo;

import java.math.BigDecimal;

@Data
public class FareVo {

    private MemberAddressVo address;

    private BigDecimal fare;

}


