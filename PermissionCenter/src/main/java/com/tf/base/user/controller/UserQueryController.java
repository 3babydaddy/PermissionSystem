package com.tf.base.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.service.LoadDeprInfoService;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;
import com.tf.base.user.domain.UserInfo;
import com.tf.base.user.persistence.UserQueryMapper;

@Controller
public class UserQueryController {

	@Autowired
	private UserQueryMapper uerQueryMapper;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	
	@Autowired
	private LoadDeprInfoService loadDeprInfoService;
	
	
	@RequestMapping(value = "/user/query", method = RequestMethod.GET)
	public String init(Model model) {
		List<SystemInfo> systemInfoList = systemQueryMapper.getAllSystemInfo();
		model.addAttribute("systemInfoList", systemInfoList);
		return "user/userQuery";
	}
	
	@RequestMapping(value = "/user/userquery", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> query(UserInfo params,HttpServletRequest request) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		int start = (params.getPage()-1)*params.getRows();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(ConstantsUtils.CURRENT_USER);
		if (userInfo != null) {
			params.setLoginDepartment(userInfo.getDepartment());
			params.setIsAdmin(userInfo.getIsAdmin());			
		}
		int total = uerQueryMapper.getUserCountWithLoginInfo(params);
		List<UserInfo> showInfos = uerQueryMapper.getUserInfoWithLoginInfo(start,params);
		if(showInfos != null){
			List<SystemInfo> systemInfoList = null;
			StringBuffer systemName = new StringBuffer();
			SystemInfo systemInfo  = null;
			for (UserInfo item : showInfos) {
				systemName.setLength(0);
				systemInfoList = systemQueryMapper.getSystemInfoByUserId(item.getId());
				if (systemInfoList != null) {
					for (int i = 0; i < systemInfoList.size(); i++) {
						systemInfo = systemInfoList.get(i);
						if (systemInfo != null) {
							systemName.append(systemInfo.getName());
							if (i < systemInfoList.size() -1) {
								systemName.append(",");
							}
						}
					}
					
					item.setSystemName(systemName.toString());
				}
				item.setDepartmentShow(loadDeprInfoService.getDeprName(item.getDepartment()));
				
			}
			
		}

		
		result.put("total", total);
		result.put("rows", showInfos);
		
		return result;
	}
	
}
