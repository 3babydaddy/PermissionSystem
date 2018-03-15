package com.tf.base.role.service;

import java.util.List;

import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.domain.RoleResource;
import com.tf.base.user.domain.UserInfo;



public interface RoleInfoService{

	
	public int saveRoleInfo(RoleInfo roleInfo,List<RoleResource> rrInfo,UserInfo user);
	public int modifyRoleInfo(RoleInfo roleInfo,List<RoleResource> rrInfo,UserInfo user);

	
}