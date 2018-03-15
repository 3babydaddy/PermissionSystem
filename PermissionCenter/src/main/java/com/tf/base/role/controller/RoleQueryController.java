package com.tf.base.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
public class RoleQueryController {

	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private BaseService baseService;
	
	@RequestMapping(value = "/role/query", method = RequestMethod.GET)
	public String init(ModelMap modelMap) {
		List<SystemInfo> systemInfoList = systemQueryMapper.getAllSystemInfo();
		
		modelMap.put("systemInfoList", systemInfoList);
		return "role/roleQuery";
	}
	
	@RequestMapping(value = "/role/rolequery", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> query(RoleInfo params,HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		int start = (params.getPage()-1)*params.getRows();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(ConstantsUtils.CURRENT_USER);
		if (userInfo != null) {
			params.setLoginName(userInfo.getUsername());
			params.setIsAdmin(userInfo.getIsAdmin());			
		}
		int total = roleQueryMapper.getRoleCount(params);
		List<RoleInfo> showInfos = roleQueryMapper.getRoleInfo(start,params);
		
		for (RoleInfo item : showInfos) {
			
			item.setSystemName(loadSysInfoService.getSysName(item.getSystemid()));
			
		}
		
		result.put("total", total);
		result.put("rows", showInfos);
		
		return result;
	}
}
