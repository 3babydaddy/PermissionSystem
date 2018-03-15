package com.tf.base.role.persistence;

import org.apache.ibatis.annotations.Param;

import com.tf.base.role.domain.RoleInfo;

public interface RoleModifyMapper {

	RoleInfo getRoleInfoById(@Param(value="id")String id);
	
	int modifyRoleInfo(@Param(value="info")RoleInfo info);
	
	int modifyavail(@Param(value="info")RoleInfo info);
}
