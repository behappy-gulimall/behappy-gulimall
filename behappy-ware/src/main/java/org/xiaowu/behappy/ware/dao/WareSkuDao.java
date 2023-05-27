package org.xiaowu.behappy.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.ware.entity.WareSkuEntity;

/**
 * 商品库存
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:51:24
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

}
