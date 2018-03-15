package com.tf.permission.client.shiro.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.tf.permission.client.constants.PermissionConstants;
import com.tf.permission.client.service.PermissionClientService;

/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class SysUserFilter extends PathMatchingFilter {

    private PermissionClientService permissionService;
    
	public void setPermissionService(PermissionClientService permissionService) {
		this.permissionService = permissionService;
	}
	
	
	@Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(PermissionConstants.CURRENT_USER, permissionService.findUserByUsername(username));
        return true;
    }
}
