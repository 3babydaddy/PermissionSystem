package com.tf.base.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

	@Value("${init_userpassword}")
	private String initUserPassword;

	public String getInitUserPassword() {
		return initUserPassword;
	}

	public void setInitUserPassword(String initUserPassword) {
		this.initUserPassword = initUserPassword;
	}
}
