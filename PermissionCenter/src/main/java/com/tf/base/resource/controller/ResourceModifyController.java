package com.tf.base.resource.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.LogService;
import com.tf.base.common.utils.NotificationCenter;
import com.tf.base.notification.domain.Notificationobject;
import com.tf.base.notification.service.NotificationService;
import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceModifyMapper;
import com.tf.base.resource.persistence.ResourceQueryMapper;

@Controller
public class ResourceModifyController {

	@Autowired
	private ResourceModifyMapper resourceModifyMapper;
	@Autowired
	private LogService logService;
	@Autowired
	private ResourceQueryMapper resourceQueryMapper;
	
	@Autowired
	private  NotificationService notificationServer;
	
	@RequestMapping(value = "/resource/resourcemodify", method = RequestMethod.POST)
	@ResponseBody
	public int modify(ResourceInfo info,HttpSession session) {
		
		ResourceInfo resourceInfo = resourceQueryMapper.getResourceInfoById(info.getId());
		resourceModifyMapper.modifyResourceInfo(info);
		
		//新资源更新通知
		Notificationobject params = new Notificationobject();
		params.setSystemid(Integer.valueOf(resourceInfo.getSystemid()));
		List<Notificationobject> notificationobjectList = notificationServer.selectByPrimaryKey(params);
		for(Notificationobject  notificationobject:notificationobjectList){
			NotificationCenter.sendNotification(notificationobject ,resourceInfo,session ,info,logService);
		}
		
		//增加日志
//		UserInfo loginUser = (UserInfo) session.getAttribute("user");
//		SystenLogInfo osInfo = new SystenLogInfo();
//		String insertOrModify = "";
//		String insertOrModifyOper="";
//		if(resourceInfo.getResourcename().equals("新资源")&&(resourceInfo.getType()==null||resourceInfo.getType().equals(""))){
//			insertOrModify="log.resourcesinfo.create";
//			insertOrModifyOper =ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString();
//			osInfo.setOperationDetail(logService.getDetailInfo(insertOrModify,
//					info.getResourcename(),info.getType(),
//					info.getPermission(),info.getResourceurl(),info.getIcon(),info.getSortIndx(),result));
//		}else{
//			insertOrModify="log.resourcesinfo.modify";
//			insertOrModifyOper=ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString();
//			osInfo.setOperationDetail(logService.getDetailInfo(insertOrModify,
//					resourceInfo.getResourcename(),info.getResourcename(),
//					resourceInfo.getType(),info.getType(),
//					resourceInfo.getPermission(),info.getPermission(),
//					resourceInfo.getResourceurl(),info.getResourceurl(),
//					resourceInfo.getIcon(),info.getIcon(),
//					resourceInfo.getSortIndx(),info.getSortIndx(),result));
//		}
//		
//		osInfo.setId(resourceInfo.getId());
//		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.RESOURCESINFO.toString());
//		
//		osInfo.setOperationUser(loginUser.getShowname());
//		logService.save(osInfo, insertOrModifyOper);
		return 0;
	}
	
	@RequestMapping(value = "/resource/resourcedelete", method = RequestMethod.POST)
	public @ResponseBody int delete(String id) {
		
		resourceModifyMapper.deleteResourceInfo(id);
		
		return 0;
	}
}
