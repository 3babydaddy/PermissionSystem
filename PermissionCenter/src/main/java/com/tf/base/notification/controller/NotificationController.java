package com.tf.base.notification.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.LoadSysInfoService;
import com.tf.base.notification.domain.Notificationobject;
import com.tf.base.notification.service.NotificationService;
import com.tf.base.system.domain.SystemInfo;

import net.sf.json.JSONObject;

@Controller
public class NotificationController {

	@Autowired
	private LoadSysInfoService loadSysInfoService;
	
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * 初始化页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/notification/init" ,method = RequestMethod.GET)
	public String init(Model model,HttpServletRequest request){
		initPage(model);
		return "notification/query";
	}

	private void initPage(Model model) {
		 List<SystemInfo> systemList =  loadSysInfoService.getAllSystemIdAndName();
		 model.addAttribute("systemList", systemList);
	}
	
	/**
	 * 查询列表
	 * @param notificationobject
	 * @param page
	 * @param rows
	 * @param response
	 */
	@RequestMapping(value="/notification/query" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  querynotification(Notificationobject params ,int page ,int rows ,HttpServletResponse response ){
		int start = (page - 1) * rows;
		Map<String, Object> result = new HashMap<String, Object>();
		int total = notificationService.queryBaseListCount(params);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (total == 0) {
			result.put("rows", list);
			result.put("total", total);
			return result;
		}
		
		
		 list  = notificationService.queryBaseList(params,start,rows);
		 result.put("total", total);
		 result.put("rows", list);
		return result;
	}
	
	@RequestMapping(value="/notification/edit" ,method = RequestMethod.GET)
	public String addOrEditNotification(Integer id,Model model ,HttpServletResponse response){
		initPage(model);
		if(id != null){
			Notificationobject params = new Notificationobject();
			params.setId(id);
			List<Notificationobject> notificationobject = notificationService.selectByPrimaryKey(params);
			model.addAttribute("model", notificationobject.get(0));
		}
		return "notification/edit";
	}
	
	/**
	 * 保存操作
	 * @param Notificationobject
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/notification/save" , method = RequestMethod.POST)
	public void save(Notificationobject Notificationobject ,HttpServletResponse response) throws IOException{
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = notificationService.update(Notificationobject);
		} catch (Exception e) {
			map.put("status", "2");
			map.put("message","操作失败！");
		}
		response.getWriter().println(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 删除操作
	 * @param notifyEmailFrom
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/notification/deleteNotification" , method = RequestMethod.POST)
	public void deleteFrom(Notificationobject Notificationobject ,HttpServletResponse response) throws IOException{
		Map<String,String> map = new HashMap<String, String>();
		int i = 0;
		try {
			i = notificationService.delete(Notificationobject);
			if(i > 0){
				map.put("status", "0");
				map.put("message","删除成功！");
			}else{
				map.put("status", "1");
				map.put("message","删除失败！");
			}
		} catch (Exception e) {
			map.put("status", "1");
			map.put("message","删除失败！");
		}
		response.getWriter().println(JSONObject.fromObject(map).toString());
	}
}
