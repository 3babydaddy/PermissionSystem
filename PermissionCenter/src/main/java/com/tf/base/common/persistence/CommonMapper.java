package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper {

	List<String> getDepartResource(@Param(value="system")String system, @Param(value="department")String department);
	
	List<String> getRoleResource(@Param(value="system")String system, @Param(value="role")String role);
	
	List<String> getUserResource(@Param(value="system")String system, @Param(value="user")String user);
	
}
