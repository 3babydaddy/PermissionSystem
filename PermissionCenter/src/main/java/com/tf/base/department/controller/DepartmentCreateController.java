package com.tf.base.department.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.LoadDeprInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentCreateMapper;
import com.tf.base.department.persistence.DepartmentQueryMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
@Transactional
public class DepartmentCreateController {

	@Autowired
	private DepartmentCreateMapper departmentCreateMapper;
	
	@Autowired DepartmentQueryMapper departmentQueryMapper;
	@Autowired
	private LoadDeprInfoService loadDeprInfoService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/department/departmentcreate", method = RequestMethod.GET)
	public String init() {
		
		return "department/depCreate";
	}
	
	@RequestMapping(value = "/department/departmentcreate", method = RequestMethod.POST)
	@ResponseBody
	public int create(String name,String higherDepart/*,String selectedIds,String system*/,HttpSession session) {
		
		DepartmentInfo info = new DepartmentInfo();
		
		info.setName(name);
		info.setHigherDepart(higherDepart);
		
		DepartmentInfo queryResult = departmentQueryMapper.getDepartmentInfosByName(name);
		
		if (queryResult == null) {
			int inserResult = departmentCreateMapper.insertDepartmentInfo(info);
			
			loadDeprInfoService.reloadParamManager();
			
			
			//增加日志
			UserInfo loginUser = (UserInfo) session.getAttribute("user");
			SystenLogInfo osInfo = new SystenLogInfo();
			DepartmentInfo deptInfo = departmentQueryMapper.getDepartmentInfosByName(info.getName());
			
			osInfo.setId(deptInfo.getId());
			osInfo.setLogModule(ConstantsUtils.LOG_MODULE.DEPARTMENT.toString());
			List<DepartmentInfo> parentDeptList = departmentQueryMapper.getDepartmentInfoById(deptInfo.getHigherDepart());
			DepartmentInfo parentDept = null;
			if(parentDeptList!=null){
				parentDept= parentDeptList.get(0);
			}
			osInfo.setOperationDetail(logService.getDetailInfo("log.department.create",info.getName(),parentDept.getName()));
			osInfo.setOperationUser(loginUser.getShowname());
			logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());
			return inserResult > 0 ? 0 :-1;
		}else{
			return 2;
		}
		
		
		/*String[] selected = selectedIds.split(",");
		
		List<DepartmentResource> drInfo = new ArrayList<DepartmentResource>();
		
		DepartmentResource dr;
		
		for (String item : selected) {
			
			dr = new DepartmentResource();
			
			dr.setResourceid(item);
			
			drInfo.add(dr);
		}
		
		departmentCreateMapper.insertDepartmentResouces(drInfo,info.getId(),system);*/
	}
}
