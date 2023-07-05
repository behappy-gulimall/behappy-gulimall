package org.xiaowu.behappy.coupon.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.coupon.dao.SeckillSkuRelationDao;
import org.xiaowu.behappy.coupon.entity.SeckillSkuRelationEntity;
import org.xiaowu.behappy.coupon.service.SeckillSkuRelationService;

import java.util.Map;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SeckillSkuRelationEntity> queryWrapper = new QueryWrapper<SeckillSkuRelationEntity>();

        //1、获取key
        String key = (String) params.get("key");

        String promotionSessionId = (String) params.get("promotionSessionId");

        if (!StrUtil.isEmpty(key)) {
            queryWrapper.eq("id", key);
        }

        if (!StrUtil.isEmpty(promotionSessionId)) {
            queryWrapper.eq("promotion_session_id", promotionSessionId);
        }
        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
