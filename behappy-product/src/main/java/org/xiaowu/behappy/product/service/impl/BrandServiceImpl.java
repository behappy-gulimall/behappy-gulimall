package org.xiaowu.behappy.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.product.dao.BrandDao;
import org.xiaowu.behappy.product.entity.BrandEntity;
import org.xiaowu.behappy.product.service.BrandService;
import org.xiaowu.behappy.product.service.CategoryBrandRelationService;

import java.util.Map;

/**
 * (onConstructor_ = {@Lazy})：fix circle dependency
 * For JDK7 is onConstructor = @__(@Lazy)
 * For JDK8+ is onConstructor_ ={@Lazy}
 * @author xiaowu
 */
@Service("brandService")
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    private final CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 获取key
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        // 如果传过来的数据不是空的，就进行多参数查询
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("brand_id",key).or().like("name",key);
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余字段的数据一致
        baseMapper.updateById(brand);
        if (!StringUtils.isEmpty(brand.getName())) {
            //同步更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
            //TODO 更新其他关联
        }
    }
}
