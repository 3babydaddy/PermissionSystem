package com.tf.base.resource.persistence;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.tf.base.resource.domain.QueryParams;
import com.tf.base.resource.domain.ResourceInfo;

public interface ResourceQueryMapper {

	int getResourceCount(@Param(value="params")QueryParams params);
	
	List<ResourceInfo> getResourceInfo(@Param(value="start")int start,@Param(value="params")QueryParams params);
	
	List<ResourceInfo> getResourceInfoBySys(@Param(value="system")String system);
	
	List<ResourceInfo> findPermissionsByUserName(@Param(value="username")String username);

	ResourceInfo getResourceInfoById(String id);
}
