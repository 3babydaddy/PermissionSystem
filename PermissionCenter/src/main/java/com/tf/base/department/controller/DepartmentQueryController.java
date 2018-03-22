package com.tf.base.department.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentQueryMapper;
import com.tf.base.department.service.DepartmentService;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;

@Controller
public class DepartmentQueryController {

	@Autowired
	private DepartmentQueryMapper departmentQueryMapper;
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value = "/department/query", method = RequestMethod.GET)
	public String init(String systemId, ModelMap modelMap) {
		
		List<SystemInfo> systemInfoList = systemQueryMapper.getAllSystemInfo();
		modelMap.put("systemInfoList", systemInfoList);
		modelMap.put("systemId", systemId);
		return "department/depQuery";
	}
	
	@RequestMapping(value = "/department/departmentquery", method = RequestMethod.POST)
	@ResponseBody
	public List<DepartmentInfo> query(DepartmentInfo departmentInfo) {
		
		 List<DepartmentInfo> result = departmentService.getDepartmentInfosWithParentsByCondition(departmentInfo);
			
		if(result != null && !StringUtils.isEmpty(departmentInfo.getAvail())){
			
			List<DepartmentInfo> availResult = new ArrayList<>();
			for (DepartmentInfo dept : result) {
				if(departmentInfo.getAvail().equals(dept.getAvail())){
					availResult.add(dept);
				}
			}
			
			return availResult;
		}
	
		return result;
	}
}
