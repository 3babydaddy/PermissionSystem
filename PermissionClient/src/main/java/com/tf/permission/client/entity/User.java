package com.tf.permission.client.entity;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2767634587784587495L;
	private Long id; // 编号
	private String username; // 用户名
	private String password; // 密码
	private String showname; // 用户显示名
	private String department; // 组织ID
	private String avail; // 是否有效
	private String salt; // 加密密码的盐
	private List<Long> roleIds; // 拥有的角色列表
	private Set<String> roles;//角色名称集合 user,admin
	private Boolean locked = Boolean.FALSE;

	
	
	private String loginDepartment;
	private String departmentShow;
	private String systemName;
	private Map<String,List<String>> hasSystemAndRole;
	
	private int page;
	private int rows;
	private String loginName;
	private String name;
	private String isAdmin;
	private String systemid;
	private String tel;
	
	//密文密码
	private String encPassword;
	
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public List<Long> getRoleIds() {
		if (roleIds == null) {
			roleIds = new ArrayList<Long>();
		}
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleIdsStr() {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for (Long roleId : roleIds) {
			s.append(roleId);
			s.append(",");
		}
		return s.toString();
	}

	public void setRoleIdsStr(String roleIdsStr) {
		if (StringUtils.isEmpty(roleIdsStr)) {
			return;
		}
		String[] roleIdStrs = roleIdsStr.split(",");
		for (String roleIdStr : roleIdStrs) {
			if (StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			getRoleIds().add(Long.valueOf(roleIdStr));
		}
	}

	public Boolean getLocked() {
		return locked;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public String getLoginDepartment() {
		return loginDepartment;
	}

	public void setLoginDepartment(String loginDepartment) {
		this.loginDepartment = loginDepartment;
	}

	public String getDepartmentShow() {
		return departmentShow;
	}

	public void setDepartmentShow(String departmentShow) {
		this.departmentShow = departmentShow;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Map<String, List<String>> getHasSystemAndRole() {
		return hasSystemAndRole;
	}

	public void setHasSystemAndRole(Map<String, List<String>> hasSystemAndRole) {
		this.hasSystemAndRole = hasSystemAndRole;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getEncPassword() {
		return encPassword;
	}

	public void setEncPassword(String encPassword) {
		this.encPassword = encPassword;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	
}
