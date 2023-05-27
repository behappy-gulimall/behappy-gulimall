package org.xiaowu.behappy.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.product.entity.CommentReplayEntity;

/**
 * 商品评价回复关系
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {

}
