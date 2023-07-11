package org.xiaowu.behappy.coupon.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xiaowu.behappy.api.common.to.SkuReductionTo;
import org.xiaowu.behappy.api.common.vo.MemberPrice;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.coupon.dao.SkuFullReductionDao;
import org.xiaowu.behappy.coupon.entity.MemberPriceEntity;
import org.xiaowu.behappy.coupon.entity.SkuFullReductionEntity;
import org.xiaowu.behappy.coupon.entity.SkuLadderEntity;
import org.xiaowu.behappy.coupon.service.MemberPriceService;
import org.xiaowu.behappy.coupon.service.SkuFullReductionService;
import org.xiaowu.behappy.coupon.service.SkuLadderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author xiaowu
 */
@Service("skuFullReductionService")
@RequiredArgsConstructor
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    private final SkuLadderService skuLadderService;

    private final MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SkuFullReductionEntity> queryWrapper = new QueryWrapper<SkuFullReductionEntity>();

        String key = (String) params.get("key");

        if (!StrUtil.isEmpty(key)) {
            queryWrapper.eq("id",key).or().eq("sku_id",key);
        }

        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }


    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {

        //1、保存满减打折、会员价
        //1、1）、sku的优惠、满减等信息：gulimall_sms--->sms_sku_ladder、sms_sku_full_reduction、sms_member_price
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo,skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());

        if (skuReductionTo.getFullCount() > 0) {
            skuLadderService.save(skuLadderEntity);
        }

        //2、sms_sku_full_reduction
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            this.save(skuFullReductionEntity);
        }


        //3、sms_member_price
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();

        List<MemberPriceEntity> collect = memberPrice.stream().map(mem -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(mem.getId());
            memberPriceEntity.setMemberLevelName(mem.getName());
            memberPriceEntity.setMemberPrice(mem.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(item -> {
            return item.getMemberPrice().compareTo(BigDecimal.ZERO) > 0;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);
    }
}
