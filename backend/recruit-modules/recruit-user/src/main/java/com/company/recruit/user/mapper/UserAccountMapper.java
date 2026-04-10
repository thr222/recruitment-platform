package com.company.recruit.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.recruit.user.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper for user accounts.
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity> {
}
