package org.xiaowu.behappy.coupon.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.coupon.dao.MemberPriceDao;
import org.xiaowu.behappy.coupon.entity.MemberPriceEntity;
import org.xiaowu.behappy.coupon.service.MemberPriceService;

import java.util.Map;


@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceDao, MemberPriceEntity> implements MemberPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MemberPriceEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");

        if (!StrUtil.isEmpty(key)) {
            queryWrapper.eq("id",key).or().eq("sku_id",key).or().eq("member_level_id",key);
        }
        IPage<MemberPriceEntity> page = this.page(
                new Query<MemberPriceEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}
