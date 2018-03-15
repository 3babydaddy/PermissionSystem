package com.tf.permission.client.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tf.permission.client.constants.PermissionEnum;
import com.tf.permission.client.entity.DepartmentInfo;
import com.tf.permission.client.entity.LogInfo;
import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.entity.RoleInfo;
import com.tf.permission.client.entity.SystemUserInfo;
import com.tf.permission.client.entity.User;
import com.tf.permission.client.utils.HttpClientUtils;
import com.tf.permission.client.utils.HttpUtil;

/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class PermissionClientServiceImpl implements PermissionClientService {

	/**
	 * 系统服务器IP
	 */
	private String host;

	/**
	 * 系统服务器端口
	 */
	private String port;

	/**
	 * 系统ID
	 */
	private String systemId;

	public PermissionClientServiceImpl(String host, String port, String systemId) {
		this.host = host;
		this.port = port;
		this.systemId = systemId;
	}

	@Override
	public List<ResourceInfo> findAllPermissionBySystemId() {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/queryAll/").append(this.getSystemId());
		String target = getTarget(sb.toString());
		return HttpClientUtils.getDataListByGetMethod(target, ResourceInfo.class);
	}

	@Override
	public User findUserByUsername(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/queryByUser/").append(this.getSystemId()).append("/").append(username);
		String target = getTarget(sb.toString());
		JSONObject responseObj = HttpClientUtils.getDataToJsonObject(target);
		User user = new User();
		if (responseObj != null) {
			String password = (String) responseObj.get(PermissionEnum.Password.toString());
			if (!StringUtils.isEmpty(password)) {
				user = responseObj.getObject(PermissionEnum.UserInfo.toString(), User.class);
			}
			List<String> roles = JSON.parseArray(responseObj.getString(PermissionEnum.Roles.toString()), String.class);
			user.setRoles(new HashSet<String>(roles));
		}
		return user == null ? new User() : user;
	}

	@Override
	public List<RoleInfo> findRolesByUserName(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/user/queryRoleInfos/").append(userId).append("/").append(this.getSystemId());
		String target = getTarget(sb.toString());
		List<RoleInfo> rolesList = HttpClientUtils.getDataListByGetMethod(target, RoleInfo.class);
		return rolesList;
	}

	@Override
	public Set<String> findPermissionsByUserName(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/queryByUser/").append(this.getSystemId()).append("/").append(username);
		String target = getTarget(sb.toString());
		JSONObject responseObj = HttpClientUtils.getDataToJsonObject(target);
		Set<String> result = new HashSet<String>();
		if (responseObj != null) {
			List<ResourceInfo> resourceList = JSON
					.parseArray(responseObj.getString(PermissionEnum.Permission.toString()), ResourceInfo.class);
			if (resourceList != null && !resourceList.isEmpty()) {
				for (ResourceInfo ri : resourceList) {
					if (ri != null && !StringUtils.isEmpty(ri.getPermission())) {
						result.add(ri.getPermission());
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<DepartmentInfo> findAllDepartments() {
		String target = getTarget("/PermissionCenter/dept/queryAllDepartments");
		return HttpClientUtils.getDataListByGetMethod(target, DepartmentInfo.class);
	}

	@Override
	public List<ResourceInfo> findMenusByPermissions(Set<String> permissions) {
		List<ResourceInfo> allResources = findAllPermissionBySystemId();
		List<ResourceInfo> menus = new ArrayList<ResourceInfo>();
		for (ResourceInfo resource : allResources) {
			if (null == resource.get_parentId() || "0".equals(resource.get_parentId())) {
				continue;
			}
			if (resource.getType() != ResourceInfo.ResourceType.menu) {
				continue;
			}
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			menus.add(resource);
		}
		return menus;
	}

	@Override
	public int modifyUserPassword(String userName, String oldPassword, String newPassword) {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/user/passwordmodify/").append(userName).append("/").append(oldPassword).append("/")
				.append(newPassword);
		String target = getTarget(sb.toString());
		String response = HttpClientUtils.getDataStringByGetMethod(target);
		if (!StringUtils.isEmpty(response)) {
			Integer.parseInt(response);
			return Integer.parseInt(response);
		}
		return 0;
	}

	@Override
	public List<SystemUserInfo> getSystemUserInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/user/getsystemUser/").append(systemId);
		String target = getTarget(sb.toString());
		return HttpClientUtils.getDataListByGetMethod(target, SystemUserInfo.class);
	}

	@Override
	public List<User> queryUserInfosByDepartId(String departId) {
		if (!StringUtils.isEmpty(departId)) {
			StringBuilder sb = new StringBuilder();
			sb.append("/PermissionCenter/user/queryUserInfosByDepartId/").append(departId);
			String target = getTarget(sb.toString());
			return HttpClientUtils.getDataListByGetMethod(target, User.class);
		}
		return null;
	}

	@Override
	public List<User> queryUserInfoByRoleId(String roleId) {
		if (!StringUtils.isEmpty(roleId)) {
			StringBuilder sb = new StringBuilder();
			sb.append("/PermissionCenter/user/queryUserInfosByRoleId/").append(roleId);
			String target = getTarget(sb.toString());
			return HttpClientUtils.getDataListByGetMethod(target, User.class);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tf.permission.client.service.PermissionClientService#
	 * getMenusByUserName(java.lang.String)
	 */
	@Deprecated
	public String findMenusByUserName(String username) {
		try {
			Set<String> set = this.findPermissionsByUserName(username);
			if (set == null) {
				return "";
			}
			// 获取用户全部的菜单资源
			List<ResourceInfo> menus = this.findMenusByPermissions(set);
			if (menus == null) {
				return "";
			}

			List<ResourceInfo> mainList = new ArrayList<ResourceInfo>();
			// 组织菜单
			for (ResourceInfo resource : menus) {
				if (menus != null && ResourceInfo.MAINMENU_LEVEL.equals(resource.getResourcelevel())) {
					AddSubMenusByPid(resource, menus);
					mainList.add(resource);
				}
			}
			return "menus:" + JSON.toJSONString(mainList);
		} catch (Exception e) {
			return "";
		}
	}

	public List<ResourceInfo> getMenusByUserName(String username) {
		try {
			Set<String> set = this.findPermissionsByUserName(username);
			if (set == null) {
				return null;
			}
			// 获取用户全部的菜单资源
			List<ResourceInfo> menus = this.findMenusByPermissions(set);
			if (menus == null) {
				return null;
			}
			List<ResourceInfo> mainList = new ArrayList<ResourceInfo>();
			// 组织菜单
			for (ResourceInfo resource : menus) {
				if (menus != null && ResourceInfo.MAINMENU_LEVEL.equals(resource.getResourcelevel())) {
					AddSubMenusByPid(resource, menus);
					mainList.add(resource);
				}
			}
			return mainList;
		} catch (Exception e) {
			return null;
		}
	}

	private ResourceInfo AddSubMenusByPid(ResourceInfo mainres, List<ResourceInfo> menus) {
		// 将一级菜单和二级菜单整合成目录树结构
		if (mainres == null || menus == null || menus.isEmpty()) {
			return null;
		}

		List<ResourceInfo> menusList = new ArrayList<ResourceInfo>();
		for (ResourceInfo resource : menus) {
			if (mainres.getId().equals(resource.get_parentId())) {
				menusList.add(resource);
			}
		}
		mainres.setSubResources(menusList);
		if (StringUtils.isEmpty(mainres.getIcon())) {
			mainres.setIcon("icon-large-smartart"); // 默认图标
		}
		return mainres;
	}

	private String getTarget(String path) {
		return new StringBuilder().append("http://").append(this.getHost()).append(":").append(this.getPort())
				.append(path).toString();
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public int modifyPassWordAndTel(String userName, String newShowName, String newPassword, String newTel) {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/user/passwordandtelmodify");

		JSONObject json = new JSONObject();
		json.put("userName", userName);
		json.put("newShowName", newShowName);
		json.put("newPassword", newPassword);
		json.put("newTel", newTel);

		String target = getTarget(sb.toString());
		String response = HttpUtil.transferPostJson(json.toJSONString(), target);
		if (!StringUtils.isEmpty(response)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int saveLog(LogInfo logInfo) {

		/*
		 * StringBuilder sb = new StringBuilder();
		 * sb.append("/PermissionCenter/log/savelog/").append(logInfo.
		 * getSystemid()).append("/").append(logInfo.getUserid()).append("/")
		 * .append(logInfo.getUsername()).append("/")
		 * .append(logInfo.getType()).append("/").append(logInfo.getDesc());
		 * String target = getTarget(sb.toString()); String response =
		 * HttpClientUtils.getDataStringByGetMethod(target); if
		 * (!StringUtils.isEmpty(response)) { Integer.parseInt(response); return
		 * Integer.parseInt(response); } return 0;
		 */

		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/log/savelog");
		String target = getTarget(sb.toString());
		String response = "";
		try {
			response = HttpUtil.transferPostJson(JSON.toJSONString(logInfo), target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(response)) {
			return 1;
		}
		return 0;
	}

	@Override
	public Set<String> findRolesByUserIdSystemId(String userId, String systemId) {
		StringBuilder sb = new StringBuilder();
		sb.append("/PermissionCenter/user/queryRoles/").append(userId).append("/").append(this.getSystemId());
		String target = getTarget(sb.toString());
		List<String> rolesList = HttpClientUtils.getDataListByGetMethod(target, String.class);
		Set<String> result = new HashSet<String>(rolesList);
		return result;
	}
}
