package org.xiaowu.behappy.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.mybatis.utils.PageUtils;
import org.xiaowu.behappy.common.mybatis.utils.Query;
import org.xiaowu.behappy.member.dao.MemberCollectSubjectDao;
import org.xiaowu.behappy.member.entity.MemberCollectSubjectEntity;
import org.xiaowu.behappy.member.service.MemberCollectSubjectService;

import java.util.Map;


@Service("memberCollectSubjectService")
public class MemberCollectSubjectServiceImpl extends ServiceImpl<MemberCollectSubjectDao, MemberCollectSubjectEntity> implements MemberCollectSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberCollectSubjectEntity> page = this.page(
                new Query<MemberCollectSubjectEntity>().getPage(params),
                new QueryWrapper<MemberCollectSubjectEntity>()
        );

        return new PageUtils(page);
    }

}
