package com.tf.permission.client.shiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tf.permission.client.service.PermissionClientService;
import com.tf.permission.client.service.PermissionClientServiceImpl;

@Configuration
public class ShiroClientConfig {

	@Value("${permissionClient.host}")
	private String host;
	@Value("${permissionClient.port}")
	private String port;
	@Value("${permissionClient.systemid}")
	private String systemid;

	@Bean(name = "permissionClientService")
	public PermissionClientService permissionClientService() {
		PermissionClientService permissionClientService = new PermissionClientServiceImpl(host, port, systemid);
		return permissionClientService;
	}
	
	
}
