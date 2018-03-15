package com.tf.permission.client.shiro.filters;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.util.CollectionUtils;

import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.service.PermissionClientService;

public class MenusFilter extends PathMatchingFilter  {
	
	 private PermissionClientService permissionService;
	    
		public void setPermissionService(PermissionClientService permissionService) {
			this.permissionService = permissionService;
		}
		

	@Override  
	public  boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception  {
	
		if(!SecurityUtils.getSubject().isAuthenticated()) {  
			return true;
		}
	   HttpServletRequest req=(HttpServletRequest) request;
	   String url = req.getRequestURI();
	   
	   String referer = req.getHeader("referer");
	   //http://127.0.0.1:8080/base_easyui/index
	   int s = referer.lastIndexOf("/");
	   referer = referer.substring(s);
	   //System.out.println("来源url："+referer);
	   
	   if(!referer.equals("/index")){
		   return true;
	   }
	   if(url.startsWith("/")){
		   
		   url = url.replaceFirst("/", "");
	   }
	   int start = url.indexOf("/");
	   url = url.substring(start);
	   url = url.replaceFirst("/", "");
	   
	   //System.out.println(url);
	   
	   String username = (String)SecurityUtils.getSubject().getPrincipal();
	   List<ResourceInfo> memus = permissionService.getMenusByUserName(username);
	   List<String> urls = new ArrayList<>();
	   for (ResourceInfo resourceInfo : memus) {
		   if(!CollectionUtils.isEmpty(resourceInfo.getSubResources())){
			   List<ResourceInfo> subResources = resourceInfo.getSubResources();
			   for (ResourceInfo resourceInfo2 : subResources) {
				   String resurl = resourceInfo2.getResourceurl();
				   if(resurl.startsWith("/")){
					   resurl = resurl.replaceFirst("/", "");
				   }
				   urls.add(resurl);
			   }
		   }else{
			   String resurl = resourceInfo.getResourceurl();
			   if(resurl.startsWith("/")){
				   resurl = resurl.replaceFirst("/", "");
			   }
			   urls.add(resourceInfo.getResourceurl());
		   }
	   }
	   
	   //System.out.println(JSON.toJSONString(urls));
	   for (String tmpUrl : urls) {
		
		   if(tmpUrl.equals(url)){
			   return true;
		   }
	   }
      // System.out.println("url matches,config is " + url);  
       return false;  
    }  
	
}
