package org.xiaowu.behappy.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:49:42
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

