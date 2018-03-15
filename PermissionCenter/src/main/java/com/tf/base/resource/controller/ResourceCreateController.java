package com.tf.base.resource.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.LogService;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceCreateMapper;
import com.tf.base.user.domain.UserInfo;

@Controller
public class ResourceCreateController {

	@Autowired
	private ResourceCreateMapper resourceCreateMapper;
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "/resource/resourcecreate", method = RequestMethod.POST)
	@ResponseBody
	public ResourceInfo create(String system,String name,String order,String level,String parent,HttpSession session) {
		
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setSystemid(system);
		resourceInfo.setResourcename(name);
		resourceInfo.setResourceorder(order);
		resourceInfo.setResourcelevel(level);
		resourceInfo.set_parentId(parent);
		
		resourceCreateMapper.insertResourceInfo(resourceInfo);
		/*//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.RESOURCESINFO.toString());
		osInfo.setOperationDetail(logService.getDetailInfo("log.resourcesinfo.create",
				name,resourceInfo.getType(),
				resourceInfo.getPermission(),resourceInfo.getResourceurl(),resourceInfo.getIcon(),resourceInfo.getSortIndx()));
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());*/
		return resourceInfo;
	}
}
