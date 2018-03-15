package com.tf.base.common.utils;


import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.user.domain.UserInfo;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.tf.base.common.service.LogService;
import com.tf.base.common.utils.HttpUtil;



public class NotificationTask implements Runnable {

	private Integer systemid = null;
	
	private String systemip = null;
	
	private String systemport = null;
	
	private String notificationUrl = null;
	
	private ResourceInfo resourceInfo = null;
	
	private ResourceInfo info = null;
	private HttpSession session = null;
	
	
	private LogService logService = null;
	
	public NotificationTask(Integer systemid, String systemip, String systemport, String notificationUrl, ResourceInfo resourceInfo ,HttpSession session,ResourceInfo info ,LogService logService) {
		super();
		this.systemid = systemid;
		this.systemip = systemip;
		this.systemport = systemport;
		this.notificationUrl = notificationUrl;
		this.resourceInfo  = resourceInfo;
		this.session = session;
		this.info = info;
		this.logService = logService;
	}




	@Override
	public void run() {
		String url = null;
		//域名不拼端口
		if(StringUtils.isEmpty(systemport)){
			url = "http://"+ systemip  +"/"+ notificationUrl +"/notificationService/resources";
		}else{
		   url = "http://"+ systemip +":"+ systemport +"/"+ notificationUrl +"/notificationService/resources";
		}
		String result = "";
		try {
			result = HttpUtil.postToUrl(url, "");
			 if("welcome".equals(result)){
				  result = "资源更新通知 " + systemip + " 成功";
			  }else{
				  result = "资源更新通知 " + systemip + " 失败";
			  }
		} catch (Exception e) {
			result = "资源更新通知 "+systemip + "失败";
			e.printStackTrace();
		}
		
		//增加日志
		UserInfo loginUser = (UserInfo) session.getAttribute("user");
		SystenLogInfo osInfo = new SystenLogInfo();
		String insertOrModify = "";
		String insertOrModifyOper="";
		if(resourceInfo.getResourcename().equals("新资源")&&(resourceInfo.getType()==null||resourceInfo.getType().equals(""))){
			insertOrModify="log.resourcesinfo.create";
			insertOrModifyOper =ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString();
			osInfo.setOperationDetail(logService.getDetailInfo(insertOrModify,
					info.getResourcename(),info.getType(),
					info.getPermission(),info.getResourceurl(),info.getIcon(),info.getSortIndx(),result));
		}else{
			insertOrModify="log.resourcesinfo.modify";
			insertOrModifyOper=ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString();
			osInfo.setOperationDetail(logService.getDetailInfo(insertOrModify,
					resourceInfo.getResourcename(),info.getResourcename(),
					resourceInfo.getType(),info.getType(),
					resourceInfo.getPermission(),info.getPermission(),
					resourceInfo.getResourceurl(),info.getResourceurl(),
					resourceInfo.getIcon(),info.getIcon(),
					resourceInfo.getSortIndx(),info.getSortIndx(),result));
		}
		
		osInfo.setId(resourceInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.RESOURCESINFO.toString());
		
		osInfo.setOperationUser(loginUser.getShowname());
		logService.save(osInfo, insertOrModifyOper);
	}

}
