package com.tf.base.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.service.LoadDeprInfoService;
import com.tf.base.common.service.LoadRoleInfoService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.common.utils.MD5Utils;
import com.tf.base.user.domain.RoleTreeInfo;
import com.tf.base.user.domain.SystemRoleInfo;
import com.tf.base.user.domain.UserInfo;
import com.tf.base.user.persistence.UserModifyMapper;
import com.tf.base.user.persistence.UserQueryMapper;
import com.tf.base.user.service.UserInfoService;

@Controller
public class UserModifyController {

	@Autowired
	private UserModifyMapper userModifyMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LoadRoleInfoService loadRoleInfoService;
	
	@Autowired
	private LoadDeprInfoService loadDeprInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired 
	private UserQueryMapper userQueryMapper;
	
	@Autowired
	private LogService  logService;
	@Autowired
	private CommonService  commonService;
	
	@RequestMapping(value = "/user/usermodify", method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest request) {
		
		List<Map<String, String>> systemList = loadSysInfoService.getDropDownList("");
		
		model.addAttribute("systemList", systemList);
		
		String id = request.getParameter("userId");
		
		UserInfo info = userModifyMapper.getUserInfoById(id);
		
		model.addAttribute("userInfo", info);
		
		List<Map<String, String>> departmentList = loadDeprInfoService.getDropDownList("");
		
		model.addAttribute("departmentList", departmentList);
		
		List<String> availSystems = userModifyMapper.getUserSystem(id);
		
		model.addAttribute("availSystems", availSystems.toString());
		
		return "user/userModify";
	}
	
	@RequestMapping(value = "/user/userRoleModifyInit", method = RequestMethod.POST)
	@ResponseBody
	public List<SystemRoleInfo> roleInit(String user) {
		
		List<SystemRoleInfo> result = new ArrayList<SystemRoleInfo>();
		
		List<String> sys = loadSysInfoService.getAllSystem();
		
		SystemRoleInfo sr;
		
		List<String> availSystems = userModifyMapper.getUserSystem(user);
		List<String> availRoles = userModifyMapper.getUserRole(user);
		
		for (String item : sys) {
			
			if (!StringUtils.isEmpty(item)) {
				
				List<RoleTreeInfo> roles = loadRoleInfoService.getRoleInfo(item);
				sr = new SystemRoleInfo();
				sr.setId(item);
				sr.setName(loadSysInfoService.getSysName(item));
				sr.setpId("0");
				sr.setChecked(availSystems.contains(item));
				result.add(sr);
				for  (RoleTreeInfo role : roles) {
					sr = new SystemRoleInfo();
					sr.setId(role.getId());
					sr.setName(role.getText());
					sr.setpId(item);
					sr.setChecked(availRoles.contains(role.getId()));
					result.add(sr);
				}
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/user/usermodify", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public int modify(String id,String name,String showName,String tel,String avail,String depr, String system, String selectedIds,HttpSession session) {
		
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(name) || StringUtils.isEmpty(showName) 
				|| StringUtils.isEmpty(avail) || StringUtils.isEmpty(depr)) {
			return -1;
		}
		UserInfo queryUser = userQueryMapper.findUserInfoByNameAndNotEQID(new UserInfo(id,name));
		if (queryUser == null) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(id);
			userInfo.setUsername(name);
			userInfo.setShowname(showName);
			userInfo.setAvail(avail);
			userInfo.setDepartment(depr);
			userInfo.setTel(tel);
			String [] roleIds = null;
			String[] systemIds = null;
			if (!StringUtils.isEmpty(selectedIds)) {
				roleIds = selectedIds.split(",");			
			}
			if (!StringUtils.isEmpty(system)) {
				systemIds = system.split(",");
			}	
			UserInfo user = (UserInfo) session.getAttribute("user");
			return userInfoService.modifyUserInfo(userInfo, roleIds, systemIds,user);
		}
		return 2;
	}
	
	
	@RequestMapping(value="/user/changeStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateStatus(UserInfo userinfo,HttpSession session) throws Exception{
	
		Map<String, String> map = new HashMap<String, String>();
		try {
			if ("0".equals(userinfo.getAvail())) {
				userinfo.setAvail("1");
			}else{
				userinfo.setAvail("0");
			}
			 int count = userModifyMapper.modifyavail(userinfo);
			 if (count >0) {
				 map.put("status", "0");				
			}else{
				map.put("status", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "1");
			throw e;
		}
		//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(userinfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
		UserInfo userInfo1 = userQueryMapper.findUserInfoByName(userinfo);
		osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.stop",userinfo.getAvail().equals("0")?"启用":"停用",userInfo1.getUsername()));
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
		return map; 
	}
	@RequestMapping(value="/user/reSetPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> reSetPassword(UserInfo userinfo,HttpSession session) throws Exception{
		
		Map<String, String> map = new HashMap<String, String>();
		UserInfo userInfo = userQueryMapper.findUserInfoById(userinfo.getId());
		try {
			String resetPassword = commonService.getInitUserPassword();
			userinfo.setPassword(resetPassword);
			//密码md5加密
			userinfo.setEncPassword(MD5Utils.md5(resetPassword,userInfo.getUsername()));
			int count = userModifyMapper.modifyPasswordByuserName(userinfo);
			if (count >0) {
				map.put("status", "0");
				map.put("password", resetPassword);
			}else{
				map.put("status", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "1");
			throw e;
		}
		//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(userinfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
		osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.resetpassword",userinfo.getPassword(),userInfo.getUsername()));
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
		return map; 
	}
}
