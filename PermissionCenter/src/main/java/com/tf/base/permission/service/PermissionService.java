package com.tf.base.permission.service;

import java.util.List;
import java.util.Set;

import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.user.domain.UserInfo;


/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public interface PermissionService {

	/**
	 * 查找全部资源
	 * 
	 * @param
	 * @return
	 */
	public List<ResourceInfo> findAll();

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username 
	 * @return
	 */
	public UserInfo findByUsername(UserInfo userinfo);
	/**
	 * 根据用户名查找用户
	 * 
	 * @param username 
	 * @return
	 */
	public UserInfo findByUsernameLike(UserInfo userinfo);

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);
	
	/**
	 * 生产菜单
	 * 
	 * @param permissions
	 * @return
	 */
	public List<ResourceInfo> findMenus(Set<String> permissions);
	
	/**修改密码
	 * @param userName
	 * @param newPwd
	 * @return
	 */
	public int modifyUserPassword(String userName,String newPwd);
	
}
