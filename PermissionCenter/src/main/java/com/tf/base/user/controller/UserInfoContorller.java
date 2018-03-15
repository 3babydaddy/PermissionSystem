package com.tf.base.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.permission.service.PermissionService;
//import com.github.pagehelper.PageInfo;
//import com.tf.base.common.constant.CommonConstant;
//import com.tf.base.common.util.JSONUtils;
//import com.tf.base.common.util.PageUtil;
//import com.tf.base.invoice.domain.InvoiceMain;
//import com.tf.base.invoice.domain.bean.InvoiceHandleForm;
//import com.tf.base.user.domain.TreeBean;
import com.tf.base.user.domain.UserInfo;


@Controller
@SessionAttributes("user")
public class UserInfoContorller {
	
	public static String INIT_PASSEWORD = "123456";

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/user/intpage")
	public String init(ModelMap model) throws Exception {
		return "user/userManage";
	}
	

	
	// 登录
	@RequestMapping(value="/user/login" ,method=RequestMethod.POST)
//	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response ,ModelMap model) throws Exception{
		
		try {
			String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
			String error = null;
			if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
				error = "没有该系统权限";
			}else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			}else if(DisabledAccountException.class.getName().equals(exceptionClassName)) {
				error = "账号被禁用";
			}else if(exceptionClassName != null) {
				error = "其他错误：" + exceptionClassName;
			}  
			model.addAttribute("error", error);
			if (!StringUtils.isEmpty(error)) {
				return "login/login";
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/index";    
    }
	/**
	 * 密码修改
	 * @param newpass
	 * @param id
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userInfoContorller/update_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update_password(String newpass,String oldpwd,Integer id,HttpServletRequest request) throws Exception{
	
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserInfo u = (UserInfo) request.getSession().getAttribute(ConstantsUtils.CURRENT_USER);
			if(!oldpwd.equals(u.getPassword())){
				map.put("mes", "原始密码错误");
			}else{
				int res = permissionService.modifyUserPassword(u.getUsername(),newpass);
				if (res == 1) {
					map.put("mes", "更新成功");
				} else {
					map.put("mes", "更新失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "更新密码失败");
			throw e;
		}
		return map; 
	}

	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login() throws Exception{
		return "login/login";
	}
	
	
//	@RequestMapping(value="/user/treedata",method=RequestMethod.POST)
//	public @ResponseBody void getTreeData(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		List<TreeBean>  treelist = new ArrayList<TreeBean>();
//		List<UserInfo> list =  userInfoService.getAllUserInfo(new UserInfo()); //获取全部用户
//		for(UserInfo info : list){
//			TreeBean   bean = new TreeBean();
//			bean.setId(info.getId());
//			bean.setText(info.getShowname());
//			treelist.add(bean);
//		}
//		response.setContentType("text/html;charset=UTF-8");
//		String treedata = "[{\"text\": \"root\",\"state\": \"open\",\"children\": "+JSONUtils.toJson(treelist) + "}]";
//		response.getWriter().write(treedata);
//	}
}