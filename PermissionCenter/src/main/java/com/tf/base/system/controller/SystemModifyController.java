package com.tf.base.system.controller;

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
import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.common.service.LogService;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemModifyMapper;
import com.tf.base.system.persistence.SystemQueryMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
public class SystemModifyController {

	@Autowired
	private SystemModifyMapper systemModifyMapper;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/system/systemmodify", method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest request) {
		
		String id = request.getParameter("sysId");
		
		SystemInfo info = systemQueryMapper.getSystemInfoById(id);
		
		model.addAttribute("systemInfo", info);
		
		return "system/sysModify";
	}
	
	@RequestMapping(value = "/system/systemmodify", method = RequestMethod.POST)
	@ResponseBody
	public int modify(SystemInfo info,HttpSession session) {
		if (info != null ) {
			if (!StringUtils.isEmpty(info.getId()) && !StringUtils.isEmpty(info.getName())) {
				SystemInfo queryResult = systemQueryMapper.getSystemInfoByNameAndNotEQID(info.getName(), info.getId());
				if (queryResult != null) {
					return 2;
				}
				SystemInfo siold = systemQueryMapper.getSystemInfoById(info.getId());
				int updateResult = systemModifyMapper.modifySystemInfo(info);
				loadSysInfoService.reloadParamManager();
				//增加日志
				UserInfo loginUser = (UserInfo) session.getAttribute("user");
				SystenLogInfo osInfo = new SystenLogInfo();
				osInfo.setId(info.getId());
				osInfo.setLogModule(ConstantsUtils.LOG_MODULE.SYSTEMINFO.toString());
				osInfo.setOperationDetail(logService.getDetailInfo("log.systeminfo.modify",
						siold.getName(),info.getName(),
						siold.getDescription(),info.getDescription()));
				osInfo.setOperationUser(loginUser.getShowname());
				logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
				return updateResult >0 ? 0: -1;
			}
		}
		return -1;
	}
}
