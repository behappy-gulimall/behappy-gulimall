package org.xiaowu.behappy.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.coupon.entity.CouponEntity;

/**
 * 优惠券信息
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:47:48
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

}
