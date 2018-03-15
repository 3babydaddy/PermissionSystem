package com.tf.base.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceCreateMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemCreateMapper;
import com.tf.base.system.persistence.SystemQueryMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
public class SystemCreateController {

	@Autowired
	private SystemCreateMapper systemCreateMapper;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private ResourceCreateMapper resourceCreateMapper;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/system/systemcreate", method = RequestMethod.GET)
	public String init() {
		
		return "system/sysCreate";
	}
	
	@RequestMapping(value = "/system/systemcreate", method = RequestMethod.POST)
	@ResponseBody
	public int create(String name,String description,HttpSession session) {
		
		SystemInfo systemInfo = new SystemInfo();
		
		systemInfo.setName(name);
		systemInfo.setDescription(description);
		SystemInfo queryResult = systemQueryMapper.getSystemInfoByName(name);
		if (queryResult != null) {
			return 2;
		}
		int inserResult = systemCreateMapper.insertSystemInfo(systemInfo);
		
		loadSysInfoService.reloadParamManager();
		
		ResourceInfo info = new ResourceInfo();
		
		info.setResourcename("根目录");
		info.setSystemid(systemInfo.getId());
		info.setResourceorder("0");
		
		resourceCreateMapper.insertResourceInfo(info);
		//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(systemInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.SYSTEMINFO.toString());
		osInfo.setOperationDetail(logService.getDetailInfo("log.systeminfo.create",name,description));
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());
		return inserResult > 0 ? 0 : -1;
	}
}
