package com.tf.base.department.persistence;

import org.apache.ibatis.annotations.Param;

import com.tf.base.department.domain.DepartmentInfo;

public interface DepartmentModifyMapper {

	DepartmentInfo getDepartmentInfoById(@Param(value="id")String id);
	
	int modifyDepartmentInfo(@Param(value="info")DepartmentInfo info);
}
