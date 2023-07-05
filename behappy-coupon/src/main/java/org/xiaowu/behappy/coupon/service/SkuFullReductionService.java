package org.xiaowu.behappy.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.common.to.SkuReductionTo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:47:48
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

