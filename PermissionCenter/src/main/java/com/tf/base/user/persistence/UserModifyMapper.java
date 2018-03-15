package com.tf.base.user.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.user.domain.UserInfo;

public interface UserModifyMapper {

	UserInfo getUserInfoById(@Param(value="id")String id);
	
	int modifyUserInfo(@Param(value="info")UserInfo info);
	
	List<String> getUserSystem(@Param(value="id")String id);
	
	List<String> getUserRole(@Param(value="id")String id);
	
	int modifyavail(@Param(value="info")UserInfo info);
	int modifyPasswordByuserName(@Param(value="info")UserInfo info);
}
