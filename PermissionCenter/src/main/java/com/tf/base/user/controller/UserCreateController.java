package com.tf.base.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.CommonService;
import com.tf.base.common.service.LoadDeprInfoService;
import com.tf.base.common.service.LoadRoleInfoService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.utils.MD5Utils;
import com.tf.base.user.domain.RoleTreeInfo;
import com.tf.base.user.domain.SystemRoleInfo;
import com.tf.base.user.domain.UserInfo;
import com.tf.base.user.persistence.UserQueryMapper;
import com.tf.base.user.service.UserInfoService;

@Controller
public class UserCreateController {
	
	@Autowired
	private LoadDeprInfoService loadDeprInfoService;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LoadRoleInfoService loadRoleInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired 
	private UserQueryMapper userQueryMapper;
	@Autowired 
	private CommonService commonService;
	
	
	@RequestMapping(value = "/user/usercreate", method = RequestMethod.GET)
	public String init(Model model) {
		
		List<Map<String, String>> systemList = loadSysInfoService.getDropDownList("");
		
		model.addAttribute("systemList", systemList);
		
		List<Map<String, String>> departmentList = loadDeprInfoService.getDropDownList("");
		
		model.addAttribute("departmentList", departmentList);
		
		return "user/userCreate";
	}
	
	@RequestMapping(value = "/user/usercreate", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public int create(String name,String showName,String tel,String avail,String depr	, String system , String selectedIds,HttpSession session) {
		if (StringUtils.isEmpty(name)) {
			return -1;
		}
		UserInfo queryUser = userQueryMapper.findUserInfoByName(new UserInfo(name));
		if (queryUser == null) {
			UserInfo userInfo = new UserInfo();
			
			userInfo.setUsername(name);
			userInfo.setShowname(showName);
			userInfo.setAvail(avail);
			userInfo.setDepartment(depr);
			// 默认密码
			userInfo.setPassword(commonService.getInitUserPassword());
			//默认密码123456
			userInfo.setEncPassword(MD5Utils.md5(commonService.getInitUserPassword(),name));
			userInfo.setTel(tel);
			
			UserInfo user = (UserInfo) session.getAttribute("user");
			return userInfoService.saveUserInfo(userInfo, system, selectedIds,user);
			
		}
		return 2;
		
	}
	
	@RequestMapping(value = "/user/userRoleInit", method = RequestMethod.POST)
	@ResponseBody
	public List<SystemRoleInfo> roleInit() {
		
		List<SystemRoleInfo> result = new ArrayList<SystemRoleInfo>();
		
		List<String> sys = loadSysInfoService.getAllSystem();
		
		SystemRoleInfo sr;
		
		for (String item : sys) {
			
			if (!StringUtils.isEmpty(item)) {
				
				List<RoleTreeInfo> roles = loadRoleInfoService.getRoleInfo(item);
				
				sr = new SystemRoleInfo();
				
				sr.setId(item);
				sr.setName(loadSysInfoService.getSysName(item));
				sr.setpId("0");
				
				result.add(sr);
				for  (RoleTreeInfo role : roles) {
					
					sr = new SystemRoleInfo();
					
					sr.setId(role.getId());
					sr.setName(role.getText());
					sr.setpId(item);
					
					result.add(sr);
				}
				
			}
		}
		
		return result;
	}
}
