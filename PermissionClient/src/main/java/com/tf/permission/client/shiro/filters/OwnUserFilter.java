package com.tf.permission.client.shiro.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

public class OwnUserFilter extends AccessControlFilter{

	private Boolean DONEEDAJAX = false;
	
	private String NOT_IN_AJAX_URL = "";
	
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            return subject.getPrincipal() != null;
        }
    }
    
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	
    	if (DONEEDAJAX && !this.pathsMatch(NOT_IN_AJAX_URL, request)) {
    		
    		saveRequest(request);
        	
        	String html = "<script language='javascript'> window.top.doAjaxLogin(); </script>";
        	
        	response.getWriter().write(html);
    	} else {
    		
    		saveRequestAndRedirectToLogin(request, response);
    	}
    	
        return false;
    }
    
    public void setNeedAjax(Boolean w) {
    	DONEEDAJAX = w;
    }
    
    public void setNotInAjax(String x) {
    	NOT_IN_AJAX_URL = x;
    }
}
