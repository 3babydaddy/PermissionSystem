package com.tf.base.user.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tf.base.user.domain.UserInfo;

public interface UserQueryMapper {

	int getUserCount(@Param(value="params")UserInfo params);
	
	int getUserCountWithLoginInfo(@Param(value="params")UserInfo params);
	
	List<UserInfo> getUserInfo(@Param(value="start")int start,@Param(value="params")UserInfo params);
	
	List<UserInfo> getUserInfoWithLoginInfo(@Param(value="start")int start,@Param(value="params")UserInfo params);
	
	UserInfo findUserInfoByName(@Param(value="params") UserInfo params);
	
	UserInfo findUserInfoByNameAndNotEQID(@Param(value="params") UserInfo params);
	
	UserInfo findUserInfoByNameLike(@Param(value="params") UserInfo params);
	
	List<UserInfo> findUserInfoByParams(@Param(value="params") UserInfo params);

	UserInfo findUserInfoById(String id);

	List<UserInfo> findUserInfoByRoleId(Map<?, ?> map);
	
	List<UserInfo> selectAll();
}
