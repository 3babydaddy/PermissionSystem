package com.tf.base.system.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.system.domain.QueryParams;
import com.tf.base.system.domain.SystemInfo;

public interface SystemQueryMapper {

	int getSystemCount(@Param(value="params")QueryParams params);
	
	List<SystemInfo> getSystemInfoLikeName(@Param(value="start")int start,@Param(value="params")QueryParams params);
	SystemInfo getSystemInfoByName(@Param(value="name")String name);
	SystemInfo getSystemInfoByNameAndNotEQID(@Param(value="name")String name,@Param(value="id")String id);
	
	int deleteSystemInfo(@Param(value="id")String id);
	
	List<SystemInfo> getAllSystemInfo();
	
	SystemInfo getSystemInfoById(@Param(value="id")String id);
	
	List<SystemInfo> getSystemInfoByUserId(@Param(value="userId")String userId);
}
