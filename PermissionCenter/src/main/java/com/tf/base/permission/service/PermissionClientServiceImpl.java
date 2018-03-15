package com.tf.base.permission.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.utils.MD5Utils;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceQueryMapper;
import com.tf.base.user.domain.UserInfo;
import com.tf.base.user.persistence.UserModifyMapper;
import com.tf.base.user.persistence.UserQueryMapper;




/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
@Service
public class PermissionClientServiceImpl implements PermissionService {

	@Autowired
	private ResourceQueryMapper resourceQueryMapper;
	@Autowired
	private UserModifyMapper userModifyMapper;
	@Autowired
	private UserQueryMapper userQueryMapper;
	
	
	@Override
	public List<ResourceInfo> findAll() {
		return resourceQueryMapper.getResourceInfo(0, null);
	}


	@Override
	public UserInfo findByUsername(UserInfo userinfo) {
		return userQueryMapper.findUserInfoByName(userinfo);
	}
	
	@Override
	public UserInfo findByUsernameLike(UserInfo userinfo) {
		return userQueryMapper.findUserInfoByNameLike(userinfo);
	}

	@Override
	public Set<String> findRoles(String username) {
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		List<ResourceInfo> resourceList = resourceQueryMapper.findPermissionsByUserName(username);
		if (resourceList != null && !resourceList.isEmpty()) {
			Set<String> permissionSet = new HashSet<String>();
			for (ResourceInfo resourceInfo : resourceList) {
				if (resourceInfo != null) {
					permissionSet.add(resourceInfo.getPermission());
				}
			}
			return permissionSet;
		}
		return null;
	}

	@Override
	public List<ResourceInfo> findMenus(Set<String> permissions) {
		List<ResourceInfo> allResources = findAll();
		List<ResourceInfo> menus = new ArrayList<ResourceInfo>();
		for (ResourceInfo resource : allResources) {
			if (null == resource.get_parentId() || "0".equals(resource.get_parentId())) {
				continue;
			}
			if (resource.getType() != ConstantsUtils.MENU) {
				continue;
			}
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			menus.add(resource);
		}
		return menus;
	}

	public int modifyUserPassword(String userName,String newPwd){
		if (!StringUtils.isEmpty(userName)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUsername(userName);
			userInfo.setPassword(newPwd);
			/**
			 *  fix bug:密码修改 未传入md5
			 *  modify by wanghw
			 */
			String md5 = MD5Utils.md5(newPwd,userName);
			userInfo.setEncPassword(md5);
			return userModifyMapper.modifyPasswordByuserName(userInfo);
		}
		return 0;
	}

	
	private boolean hasPermission(Set<String> permissions, ResourceInfo resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	
}
