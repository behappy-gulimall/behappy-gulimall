package org.xiaowu.behappy.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.product.vo.Catelog2Vo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:26:19
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long[] findCatelogPath(Long catelogId);

    List<CategoryEntity> listWithTree();

    void updateCascade(CategoryEntity category);

    void removeMenuByIds(List<Long> list);

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catelog2Vo>> getCatalogJson();
}

