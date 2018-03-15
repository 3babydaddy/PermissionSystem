package com.tf.base.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.permission.service.PermissionService;
import com.tf.base.user.domain.UserInfo;


/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class SysUserFilter extends PathMatchingFilter {

	//@Resource(name="permissionClientServiceImpl")
    private PermissionService permissionService;
    

    public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}


	@Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        request.setAttribute(ConstantsUtils.CURRENT_USER, permissionService.findByUsername(userInfo));
        return true;
    }
}
