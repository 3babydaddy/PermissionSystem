package com.tf.demo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tf.permission.client.constants.PermissionConstants;
import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.entity.User;
import com.tf.permission.client.service.PermissionClientBaseService;
import com.tf.permission.client.service.PermissionClientService;


@Controller
public class LoginController {

	@Autowired
	private PermissionClientBaseService permissionClientBaseService;
	
	@Autowired
	private PermissionClientService permissionClientService;
	
	@RequestMapping(value = {"/", "index.html", "index.jsp"}, method = RequestMethod.GET) 
	public String index(HttpServletRequest request) throws Exception {
		return "login";
	}
    // 登录
	@RequestMapping(value="/userInfoContorller/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request, 
			HttpServletResponse response , ModelMap model) throws Exception{
		
		  String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
		  String error = null;
		  
		  if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        } else if(exceptionClassName != null) {
	            error = "其他错误：" + exceptionClassName;
	        }
		 
		  model.addAttribute("error", error);
		  
		  if (!StringUtils.isEmpty(error)) {
			  return "login";
		  }

		  return "redirect:/index";
	        
	}	
    @RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model) {
		
		User u = (User) request.getAttribute(PermissionConstants.CURRENT_USER);
		
		request.getSession().setAttribute(PermissionConstants.CURRENT_USER, u);
		
		  model.addAttribute("showname", u.getShowname());
		  List<ResourceInfo> meuns  =  permissionClientService.getMenusByUserName(u.getUsername());
		  model.addAttribute("menus", JSON.toJSONString(meuns));
		model.addAttribute("showname", u.getShowname());
		return "index";
	}

    @RequestMapping(value="/userInfoContorller/update_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update_password(String oldpassword,String newpassword) throws Exception{
	
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			User u = (User) permissionClientBaseService.getBaseInfo("user");
			
			if (!oldpassword.equals(u.getPassword())) {
				
				map.put("mes", "原密码不正确。");
				map.put("status", "-1");
				
				return map; 
			}
			
			int res = permissionClientService.modifyUserPassword(u.getUsername(), oldpassword, newpassword);
			
			if (res == 0) {
				
				map.put("mes", "更新成功。");
				map.put("status", "0");
			} else {
				
				map.put("mes", "更新失败。");
				map.put("status", "-1");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "更新失败");
			map.put("status", "-1");
//			throw e;
		}
		return map; 
	}
}
