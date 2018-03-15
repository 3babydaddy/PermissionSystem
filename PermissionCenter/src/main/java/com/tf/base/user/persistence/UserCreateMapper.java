package com.tf.base.user.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.user.domain.UserInfo;

public interface UserCreateMapper {

	public int insertUserInfo(UserInfo info);
	
	public int insertUserSystem(@Param(value="infos")List<String> infos, @Param(value="user")String user);
	
	public int deleteUserSystem(@Param(value="user")String user);
	
	public int insertUserRole(@Param(value="infos")List<String> infos, @Param(value="user")String user);
	
	public int deleteUserRole(@Param(value="user")String user);
}
