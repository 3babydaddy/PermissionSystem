package com.tf.base.role.controller;

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

import com.tf.base.common.service.LoadRoleInfoService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.domain.RoleResource;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.role.service.RoleInfoService;
import com.tf.base.user.domain.UserInfo;

@Controller
@Transactional
public class RoleCreateController {

	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LoadRoleInfoService loadRoleInfoService;
	
	@Autowired
	private RoleInfoService roleInfoService;
	
	@RequestMapping(value = "/role/rolecreate", method = RequestMethod.GET)
	public String init(Model model) {
		
		List<Map<String, String>> systemList = loadSysInfoService.getDropDownList("");
		
		model.addAttribute("systemList", systemList);
		
		return "role/roleCreate";
	}
	
	@RequestMapping(value = "/role/rolecreate", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public int create(String name,String system,String avail, String selectedIds,HttpSession session) {
		
		RoleInfo info = new RoleInfo();
		
		info.setName(name);
		info.setSystemid(system);
		info.setAvail(avail);
		
		RoleInfo queryRoleInfo = roleQueryMapper.findRoleInfoByName(info);
		
		if(queryRoleInfo == null){
			String[] selected = selectedIds.split(",");
			
			List<RoleResource> rrInfo = new ArrayList<RoleResource>();
			
			RoleResource rr;
			
			for (String item : selected) {
				
				if (StringUtils.isEmpty(item)) {
					continue;
				}
				
				rr = new RoleResource();
				
				rr.setResourceid(item);
				
				rrInfo.add(rr);
			}
			UserInfo user = (UserInfo) session.getAttribute("user");
			int result = roleInfoService.saveRoleInfo(info, rrInfo,user);
			loadRoleInfoService.reloadParamManager();
			
			return result;
		}else{
			return 2;
		}
	}
}
