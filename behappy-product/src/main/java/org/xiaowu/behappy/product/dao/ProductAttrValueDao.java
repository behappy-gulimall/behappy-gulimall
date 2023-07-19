package org.xiaowu.behappy.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.product.entity.ProductAttrValueEntity;

/**
 * spu属性值
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {

}
