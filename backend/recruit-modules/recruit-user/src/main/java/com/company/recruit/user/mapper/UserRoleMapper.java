package com.company.recruit.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.recruit.user.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper for user-role relation.
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
}
