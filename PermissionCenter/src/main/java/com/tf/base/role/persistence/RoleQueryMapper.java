package com.tf.base.role.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tf.base.role.domain.RoleInfo;

public interface RoleQueryMapper {

	int getRoleCount(@Param(value="params")RoleInfo params);
	
	List<RoleInfo> getRoleInfo(@Param(value="start")int start,@Param(value="params")RoleInfo params);
	List<RoleInfo> getRoleInfos(@Param(value="params")RoleInfo params);
	List<RoleInfo> getRoleInfosByUserIdAndSystemId(@Param(value="params")RoleInfo params);
	
	List<RoleInfo> getAllRoleInfo(@Param(value="params")RoleInfo params);
	
	RoleInfo findRoleInfoByName(@Param(value="params") RoleInfo params);
	
	RoleInfo findRoleInfoByNameAndNotEQID(@Param(value="name") String name,@Param(value="id") String id);

	List<Map> getResourceByRoleid(String roleId);

	RoleInfo getRoleInfoById(String id);
}
