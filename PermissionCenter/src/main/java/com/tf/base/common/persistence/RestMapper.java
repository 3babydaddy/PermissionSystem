package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.LogInfo;
import com.tf.base.common.domain.SystemUserInfo;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.user.domain.UserInfo;

public interface RestMapper {

	UserInfo getUserInfo(@Param(value="system")String system, @Param(value="user")String user);
	
	List<String> getDaprRes(@Param(value="system")String system, @Param(value="user")String user);
	
	List<String> getRoleRes(@Param(value="system")String system, @Param(value="user")String user);
	
	List<ResourceInfo> getRes(@Param(value="ids")List<String> ids);
	
	int checkPassword(@Param(value="userName")String userName, @Param(value="password")String password, @Param("encPassword") String string);
	
	int modifyPassword(@Param(value="userName")String userName, @Param(value="password")String password,@Param("encPassword") String string);
	
	List<SystemUserInfo> getSystemUserInfo(@Param(value="system")String system);

	int modifyPasswordAndTel(@Param(value="userName")String userName, @Param(value="showName")String showName,@Param(value="password")String password, 
			@Param(value="encPassword")String encPassword, @Param(value="tel")String tel);

}
