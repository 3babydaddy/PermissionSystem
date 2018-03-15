package com.tf.base.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.LoadRoleInfoService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.domain.RoleResource;
import com.tf.base.role.persistence.RoleModifyMapper;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.role.service.RoleInfoService;
import com.tf.base.user.domain.UserInfo;

@Controller
public class RoleModifyController {

	@Autowired
	private RoleModifyMapper roleModifyMapper;
	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LoadRoleInfoService loadRoleInfoService;
	
	@Autowired
	private RoleInfoService roleInfoService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/role/rolemodify", method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest request) {
		
		String id = request.getParameter("roleId");
		
		RoleInfo info = roleModifyMapper.getRoleInfoById(id);
		
		info.setSystemName(loadSysInfoService.getSysName(info.getSystemid()));
		
		model.addAttribute("roleInfo", info);
		
		return "role/roleModify";
	}
	
	@RequestMapping(value = "/role/rolemodify", method = RequestMethod.POST)
	@ResponseBody
	public int modify(String id,String name,String system,String avail, String selectedIds,HttpSession session) {
		
		RoleInfo info = new RoleInfo();
		
		info.setId(id);
		info.setName(name);
		info.setSystemid(system);
		info.setAvail(avail);
		
		RoleInfo queryRoleinfo = roleQueryMapper.findRoleInfoByNameAndNotEQID(name, id);
		if (queryRoleinfo != null) {
			return 2;
		}
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
		loadRoleInfoService.reloadParamManager();
		UserInfo user = (UserInfo) session.getAttribute("user");
		return roleInfoService.modifyRoleInfo(info, rrInfo,user);
	}
	
	@RequestMapping(value="/role/changeStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateStatus(RoleInfo roleInfo,HttpSession session) throws Exception{
	
		Map<String, String> map = new HashMap<String, String>();
		try {
			if ("0".equals(roleInfo.getAvail())) {
				roleInfo.setAvail("1");
			}else{
				roleInfo.setAvail("0");
			}
			 int count = roleModifyMapper.modifyavail(roleInfo);
			 if (count >0) {
				 map.put("status", "0");				
			}else{
				map.put("status", "1");
			}
		} catch (Exception e) {
			map.put("status", "1");
			e.printStackTrace();
			throw e;
		}
		//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(roleInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
		List<RoleInfo> roleInfos = roleQueryMapper.getRoleInfos(roleInfo);
		RoleInfo ri = null;
		if(roleInfos!=null){
			ri  = roleInfos.get(0);
		}
		osInfo.setOperationDetail(logService.getDetailInfo("log.roleinfo.top",ri.getName(),ri.getAvail().equals("0")?"启用":"停用"));
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
		return map; 
	}
}
