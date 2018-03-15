package com.tf.permission.client.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.service.PermissionClientService;
import com.tf.permission.client.shiro.config.ShiroFilterBean;

@Controller
@RequestMapping("/notificationService")
public class NotificationController {

	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private PermissionClientService permissionService;

	@Autowired
	private ShiroFilterFactoryBean shiroFilter;

	@RequestMapping("/resources")
	@ResponseBody
	public String resourcesNotify() {
		log.info("有新资源需更新，服务通知开始>>>>>>>>>>>>>");
//		从数据库获取新的资源信息
		List<ResourceInfo> resourceList = permissionService.findAllPermissionBySystemId();
		AbstractShiroFilter absShiroFilter = null;
		try {
			absShiroFilter = (AbstractShiroFilter) shiroFilter.getObject();
			// 获取过滤管理器
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) absShiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
			// 清空初始权限配置
			manager.getFilterChains().clear();
			shiroFilter.getFilterChainDefinitionMap().clear();
			
//			设置新的权限配置
			ShiroFilterBean.loadShiroFilterChain(shiroFilter, resourceList);
//			更新Manager的权限控制（必须设置，要不然不起作用）
			Map<String, String> chains = shiroFilter.getFilterChainDefinitionMap();
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
		} catch (Exception e) {
			log.error("新资源更新，通知服务出现异常，详情："+e.getMessage());
		}finally{
			
			log.info("有新资源需更新，服务通知结束<<<<<<<<<<<<<<<<<<");
		}
		return "welcome";
	}
}
