package com.tf.permission.client.service;

import java.util.List;
import java.util.Set;

import com.tf.permission.client.entity.DepartmentInfo;
import com.tf.permission.client.entity.LogInfo;
import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.entity.RoleInfo;
import com.tf.permission.client.entity.SystemUserInfo;
import com.tf.permission.client.entity.User;

/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */

public interface PermissionClientService {

	/**
	 * 根据系统Id查找全部资源
	 * 
	 * @param
	 * @return
	 */
	public List<ResourceInfo> findAllPermissionBySystemId();

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public List<RoleInfo> findRolesByUserName(String username);

	/**
	 * 根据用户名查找其权限（例如 ：index:resourceconfig:warnmgr）
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissionsByUserName(String username);

	/**
	 * 根据用户名查找菜单信息 (non-Javadoc)
	 * 
	 * @see com.tf.permission.client.service.PermissionClientService#getMenusByUserName(java.lang.String)
	 * @param username
	 * @return
	 */
	@Deprecated
	public String findMenusByUserName(String username);

	/**
	 * 根据用户名查找其菜单信息
	 * 
	 * @param username
	 * @return
	 */
	public List<ResourceInfo> getMenusByUserName(String username);

	/**
	 * 根据权限字符串查找查单信息
	 * 
	 * @param permissions
	 * @return
	 */
	public List<ResourceInfo> findMenusByPermissions(Set<String> permissions);

	/**
	 * 修改用户密码
	 * 
	 * @param userName
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public int modifyUserPassword(String userName, String oldPassword, String newPassword);

	/**
	 * 查找系统下的所有用户
	 * 
	 * @return
	 */
	public List<SystemUserInfo> getSystemUserInfo();

	/**
	 * 根据部门ID获取部门人员信息
	 * 
	 * @param departId
	 * @return
	 */
	public List<User> queryUserInfosByDepartId(String departId);

	/**
	 * 获取所有部门信息
	 * 
	 * @param departId
	 * @return
	 */
	public List<DepartmentInfo> findAllDepartments();

	/**
	 * 根据roleID获取角色人员信息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<User> queryUserInfoByRoleId(String roleId);

	public int modifyPassWordAndTel(String userName, String newShowName, String newPassword, String newTel);

	public int saveLog(LogInfo logInfo);

	/**
	 * 根据用户id 和系统id获取拥有的权限集合
	 * 
	 * @param userId
	 * @param systemId
	 * @return 权限集合
	 */
	public Set<String> findRolesByUserIdSystemId(String userId, String systemId);

	/**
	 * 向权限中心的redis数据库加入用户缓存
	 * 
	 * @param key
	 * @param flag
	 * @return
	 */
	boolean addUserCache(String key, String flag);

	/**
	 * 从权限中心的redis数据库取出用户缓存
	 * 
	 * @param key
	 * @return
	 */
	String getUserCache(String key);

	/**
	 * 从权限中心的redis数据库清除用户缓存
	 * 
	 * @param key
	 * @return
	 */
	boolean clearUserCache(String key);
}
