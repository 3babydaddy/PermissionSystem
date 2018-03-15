package com.tf.base.user.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tf.base.user.domain.UserInfo;



public interface UserInfoService{

	public List<UserInfo> getAllUserInfo(UserInfo userInfo) throws Exception;
	
	public List<UserInfo> getUserByName(String name) throws Exception;
	
	public boolean updateUserInfo(UserInfo userInfo) throws Exception;
	
	public UserInfo selectById(Integer id) throws Exception;
	
	int insert(UserInfo record);
	
	public boolean resetPassword(UserInfo userInfo);
	
	public int deleteByPrimaryKey(Integer id);
	
	public int saveUserInfo(UserInfo userInfo, String system,String selectedIds,UserInfo user);
	
	public int modifyUserInfo(UserInfo userInfo,String [] roleIds,String[] systemIds,UserInfo user);
	
	
}