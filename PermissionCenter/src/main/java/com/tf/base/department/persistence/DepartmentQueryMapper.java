package com.tf.base.department.persistence;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.domain.QueryParams;

public interface DepartmentQueryMapper {

	int getDepartmentCount(@Param(value="params")QueryParams params);
	
	List<DepartmentInfo> getDepartmentInfo(@Param(value="start")int start,@Param(value="params")QueryParams params);
	
	List<DepartmentInfo> getAllDepartmentInfo();
	
	List<DepartmentInfo> getDepartmentInfosByNameLike(@Param(value="name") String name);
	
	DepartmentInfo getDepartmentInfosByName(@Param(value="name") String name);
	
	DepartmentInfo getDepartmentInfosByNameAndNotEQID(@Param(value="name") String name,@Param(value="id") String id);
	
	List<DepartmentInfo> getDepartmentInfoById(@Param(value="id") String id);
	List<DepartmentInfo> getDepartmentInfoByParentId(@Param(value="id") String id);

}
