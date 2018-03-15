package com.tf.base.role.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.domain.RoleResource;

public interface RoleCreateMapper {

	public int insertRoleInfo(RoleInfo info);
	
	public int insertRoleResouces(@Param(value="infos")List<RoleResource> infos, @Param(value="role")String role,@Param(value="system")String system);
	
	public int deleteRoleResouces(@Param(value="role")String role,@Param(value="system")String system);
}
