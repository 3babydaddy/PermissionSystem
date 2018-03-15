package com.tf.base.resource.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceQueryMapper;

@Controller
public class ResourceQueryController {

	@Autowired
	private ResourceQueryMapper resourceQueryMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@RequestMapping(value = "/resource/query", method = RequestMethod.GET)
	public String init(Model model) {
		
		List<Map<String, String>> systemList = loadSysInfoService.getDropDownList("");
		
		model.addAttribute("systemList", systemList);
		
		return "resource/resourceInfo";
	}
	
	@RequestMapping(value = "/resource/rectreequery", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> treeInit(String system) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<ResourceInfo> showInfos = resourceQueryMapper.getResourceInfoBySys(system);
		
		result.put("total", showInfos.size());
		result.put("rows", showInfos);
		return result;
	}
}
