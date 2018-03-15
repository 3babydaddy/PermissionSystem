package com.tf.base.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class UserInfo extends QueryParams{

	private String id;
	private String username;
	private String password;
	private String showname;
	private String department;
	private String loginDepartment;
	private String avail;
	private String departmentShow;
	
	private String systemName;
	private String tel;
	
	private Map<String,List<String>> hasSystemAndRole;
	
	private String encPassword;
	
	public String getLoginDepartment() {
		return loginDepartment;
	}
	public void setLoginDepartment(String loginDepartment) {
		this.loginDepartment = loginDepartment;
	}
	public UserInfo(){
	}
	public UserInfo(String username){
		this.username = username;
	}
	public UserInfo(String id,String username){
		this.id = id;
		this.username = username;
	}
	
	private String salt; // 加密密码的盐
	private List<Long> roleIds; // 拥有的角色列表
	private Boolean locked = Boolean.FALSE;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getDepartmentShow() {
		return departmentShow;
	}
	public void setDepartmentShow(String departmentShow) {
		this.departmentShow = departmentShow;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
