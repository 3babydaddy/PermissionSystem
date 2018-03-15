package com.tf.base.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.permission.service.PermissionService;
import com.tf.base.user.domain.UserInfo;

@Controller
public class IndexContorller {
	
	@Autowired
	private PermissionService permissionService;
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcom(HttpServletRequest request,ModelMap model) {
		
		UserInfo u = (UserInfo) request.getAttribute(ConstantsUtils.CURRENT_USER);
		if (u != null) {
			request.getSession().setAttribute(ConstantsUtils.CURRENT_USER, u);
			model.addAttribute("showname", u.getShowname());			
		}
//		CommonConstant.userInfoList = permissionService.getSystemUserInfo();
		return "index/index";
	}
	
}
