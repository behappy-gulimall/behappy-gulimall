package org.xiaowu.behappy.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.api.ware.vo.MergeVo;
import org.xiaowu.behappy.api.ware.vo.PurchaseDoneVo;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.ware.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:51:24
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void done(PurchaseDoneVo doneVo);

    void received(List<Long> ids);

    void mergePurchase(MergeVo mergeVo);

    PageUtils queryPageUnreceive(Map<String, Object> params);
}

