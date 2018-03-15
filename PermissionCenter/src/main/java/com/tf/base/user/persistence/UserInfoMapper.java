package com.tf.base.user.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.user.domain.UserInfo;


public interface UserInfoMapper {

	
   public List<UserInfo> getAllUserInfo(UserInfo userInfo);
   
   public List<UserInfo> getUserByName(@Param(value="name")String name);
   
   public void updateUserInfo(UserInfo userInfo);
   
   public UserInfo selectById(Integer id);
   
   int insert(UserInfo record);
   
   public boolean resetPassword(UserInfo userInfo);
   
   public int deleteByPrimaryKey(Integer id);
	
}