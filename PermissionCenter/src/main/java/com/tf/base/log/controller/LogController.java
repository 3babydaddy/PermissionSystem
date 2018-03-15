package com.tf.base.log.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.LogInfo;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.log.persistence.LogInfoQueryMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;

@Controller
public class LogController {
	
	@Autowired
	private LogInfoQueryMapper logInfoQueryMapper;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;

	@RequestMapping(value="/log/logQuery")
	public String init(Model model){
		
		List<SystemInfo> systemInfoList = systemQueryMapper.getAllSystemInfo();
		model.addAttribute("systemInfoList", systemInfoList);
		
		return "log/logQuery";
	}
	
	@RequestMapping(value="/log/logQuery",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> logquery(LogInfo info,
			int page,int rows){
		Map<String,Object> result = new HashMap<String,Object>();
		
		int start = (page-1)*rows;
		int total = logInfoQueryMapper.logQueryCount(info);
		
		if(total == 0){
			result.put("total", total);
			result.put("rows", new ArrayList<>());
			return result;
		}
		List<LogInfo> showInfos = logInfoQueryMapper.logQuery(start,rows,info);
		
		List<SystemInfo> systemInfoList = systemQueryMapper.getAllSystemInfo();
		for (LogInfo logInfo : showInfos) {
			String systemName = this.findSystemName(systemInfoList,logInfo.getSystemid());
			logInfo.setSystemName(systemName);
		}
		
		result.put("total", total);
		result.put("rows", showInfos);
		return result;
	}

	private String findSystemName(List<SystemInfo> systemInfoList, String systemid) {

		if(systemid == null){
			return null;
		}
		for (SystemInfo systemInfo : systemInfoList) {
			if(systemid.equals(systemInfo.getId())){
				return systemInfo.getName();
			}
		}
		return null;
	}
}
