package com.tf.base.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.system.domain.QueryParams;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;

@Controller
public class SystemQueryController {

	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	@RequestMapping(value = "/system/query", method = RequestMethod.GET)
	public String init() {
		
		return "system/sysQuery";
	}
	
	@RequestMapping(value = "/system/systemquery", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> query(QueryParams params) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		int start = (params.getPage()-1)*params.getRows();
		
		int total = systemQueryMapper.getSystemCount(params);
		
		List<SystemInfo> showInfos = systemQueryMapper.getSystemInfoLikeName(start,params);
		
		result.put("total", total);
		result.put("rows", showInfos);
		
		return result;
	}
	
	@RequestMapping(value = "/system/systemdelete", method = RequestMethod.POST)
	public @ResponseBody int delete(String id) {
		
		systemQueryMapper.deleteSystemInfo(id);
		
		return 0;
	}
}
