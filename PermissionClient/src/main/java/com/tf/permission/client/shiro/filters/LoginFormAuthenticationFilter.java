package com.tf.permission.client.shiro.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{

	 protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
             ServletRequest request, ServletResponse response) throws Exception {
		//issueSuccessRedirect(request, response);
		
		String successUrl = getSuccessUrl();
		
		if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }
		
		boolean contextRelative = true;
		
		WebUtils.issueRedirect(request, response, successUrl, null, contextRelative);
		//we handled the success redirect directly, prevent the chain from continuing:
		return false;
	}
}
