package org.xiaowu.behappy.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品会员价格
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:47:48
 */
@Data
@TableName("sms_member_price")
public class MemberPriceEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * 会员等级id
	 */
	private Long memberLevelId;
	/**
	 * 会员等级名
	 */
	private String memberLevelName;
	/**
	 * 会员对应价格
	 */
	private BigDecimal memberPrice;
	/**
	 * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
	 */
	private Integer addOther;

}
