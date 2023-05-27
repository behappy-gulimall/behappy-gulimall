package org.xiaowu.behappy.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xiaowu.behappy.member.entity.MemberEntity;

/**
 * 会员
 *
 * @author xiaowu
 * @email wangxiaowu950330@foxmail.com
 * @date 2023-05-27 15:49:42
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}
