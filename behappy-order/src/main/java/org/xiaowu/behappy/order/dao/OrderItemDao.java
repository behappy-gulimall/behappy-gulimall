package org.xiaowu.behappy.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.order.entity.OrderItemEntity;

/**
 * 订单项信息
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:39:13
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {

}
