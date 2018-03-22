package com.tf.base.department.controller;

import java.util.List;
import java.util.Map;

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
import com.tf.base.common.service.LoadDeprInfoService;
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentModifyMapper;
import com.tf.base.department.persistence.DepartmentQueryMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
public class DepartmentModifyController {

	@Autowired
	private DepartmentModifyMapper departmentModifyMapper;

	@Autowired
	private DepartmentQueryMapper departmentQueryMapper;
	
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LoadDeprInfoService loadDeprInfoService;
	
	@Autowired
	private LogService  logService;
	
	@RequestMapping(value = "/department/departmentmodify", method = RequestMethod.GET)
	public String init(String systemId, Model model, String depId) {
		
		DepartmentInfo info = null;
		if(!StringUtils.isEmpty(depId)){
			info = departmentModifyMapper.getDepartmentInfoById(depId);
		}
		List<Map<String, String>> systemList = loadSysInfoService.getDropDownList(systemId);
		
		model.addAttribute("systemList", systemList);
		
		model.addAttribute("departmentInfo", info);
		model.addAttribute("systemId", systemId);
		
		return "department/depModify";
	}
	
	@RequestMapping(value = "/department/departmentmodify", method = RequestMethod.POST)
	@ResponseBody
	public int modify(DepartmentInfo info,HttpSession session) {
		String deptId = "";
		String deptName="";
		String parentDeptId="";
		if (info != null) {
			if (!StringUtils.isEmpty(info.getName()) && !StringUtils.isEmpty(info.getId())) {
				if(info.getAvail().equals("1")){
					List<DepartmentInfo> childs = departmentQueryMapper.getDepartmentInfoByParentId(info.getId());
					if(childs != null && childs.size() > 0){
						for (DepartmentInfo departmentInfo : childs) {
							if(departmentInfo.getAvail().equals("0")){
								return 3;
							}
						}
					}
				}
				
				
				DepartmentInfo departmentInfo = departmentQueryMapper.getDepartmentInfosByNameAndNotEQID(info.getName(), info.getId());
				if (departmentInfo != null) {
					return 2;
				}
				List<DepartmentInfo> olddilist = departmentQueryMapper.getDepartmentInfoById(info.getId());
				if(olddilist!=null){
					DepartmentInfo diold = olddilist.get(0);
					deptId = diold.getId();
					deptName = diold.getName();
					parentDeptId = diold.getHigherDepart();
				}
				info.setHigherDepart(StringUtils.isEmpty(info.getHigherDepart())?null:info.getHigherDepart());
				int result = departmentModifyMapper.modifyDepartmentInfo(info);
				loadDeprInfoService.reloadParamManager();
				
				//增加日志
				UserInfo loginUser = (UserInfo) session.getAttribute("user");
				SystenLogInfo osInfo = new SystenLogInfo();
				osInfo.setId(deptId);
				osInfo.setLogModule(ConstantsUtils.LOG_MODULE.DEPARTMENT.toString());
				List<DepartmentInfo> parentDeptList = departmentQueryMapper.getDepartmentInfoById(parentDeptId);
				DepartmentInfo parentDept = null;
				if(parentDeptList!=null){
					parentDept= parentDeptList.get(0);
				}
				List<DepartmentInfo> newparentDeptList = departmentQueryMapper.getDepartmentInfoById(info.getHigherDepart());
				DepartmentInfo newparentDept = null;
				if(newparentDeptList!=null){
					newparentDept= newparentDeptList.get(0);
				}
				osInfo.setOperationDetail(logService.getDetailInfo("log.department.modify",deptName,info.getName(),parentDept.getName(),newparentDept.getName()));
				osInfo.setOperationUser(loginUser.getShowname());
				logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
				return result >0 ? 0 :-1;
			}
		}
		
		
		return -1;
	}
}
