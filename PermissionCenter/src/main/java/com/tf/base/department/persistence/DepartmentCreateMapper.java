package com.tf.base.department.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.domain.DepartmentResource;

public interface DepartmentCreateMapper {

	public int insertDepartmentInfo(DepartmentInfo info);
	
	public int insertDepartmentResouces(@Param(value="infos")List<DepartmentResource> infos, @Param(value="depart")String depart,@Param(value="system")String system);
	
	public int deleteDepartmentResouces(@Param(value="depart")String depart,@Param(value="system")String system);
	
	public int insertDepartmentResouce(@Param(value="departId")String depart,@Param(value="systemId")String system);
}
