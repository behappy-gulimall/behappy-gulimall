package org.xiaowu.behappy.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券领取历史记录
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:47:48
 */
@Data
@TableName("sms_coupon_history")
public class CouponHistoryEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 优惠券id
	 */
	private Long couponId;
	/**
	 * 会员id
	 */
	private Long memberId;
	/**
	 * 会员名字
	 */
	private String memberNickName;
	/**
	 * 获取方式[0->后台赠送；1->主动领取]
	 */
	private Integer getType;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 使用状态[0->未使用；1->已使用；2->已过期]
	 */
	private Integer useType;
	/**
	 * 使用时间
	 */
	private Date useTime;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 订单号
	 */
	private Long orderSn;

}
