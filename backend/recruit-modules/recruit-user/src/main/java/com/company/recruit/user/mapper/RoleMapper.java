package com.company.recruit.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.recruit.user.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper for roles.
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {
}
