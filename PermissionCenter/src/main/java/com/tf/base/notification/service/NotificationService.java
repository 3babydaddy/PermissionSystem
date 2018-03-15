package com.tf.base.notification.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tf.base.notification.domain.Notificationobject;
import com.tf.base.notification.persistence.NotificationobjectMapper;

@Service
public class NotificationService {

	@Autowired
	private NotificationobjectMapper notificationobjectMapper;
	
	public List<Map<String, String>> queryBaseList(Notificationobject params, int start, int rows) {
		return notificationobjectMapper.queryBaseList(params,start,rows) ;
	}

	public int queryBaseListCount(Notificationobject params) {
		return notificationobjectMapper.queryBaseListCount(params);
	}

	public List<Notificationobject> selectByPrimaryKey(Notificationobject params) {
		return notificationobjectMapper.selectByPrimaryKey(params);
	}

	public Map<String, String> update(Notificationobject notificationobject) {
		Map<String,String> map = new HashMap<String,String>();
		int i = 0;
		if(StringUtils.isEmpty(notificationobject.getId())){
			i = notificationobjectMapper.insert(notificationobject);
			map.put("status", "1");
			map.put("message", "新增成功!");
		}else{
			i = notificationobjectMapper.updateByPrimaryKeySelective(notificationobject);
			map.put("status", "1");
			map.put("message", "修改成功!");
		}
		if(i < 1){
			map.put("status", "2");
			map.put("message", "操作失败!");
		}
		return map;
	}

	public int delete(Notificationobject notificationobject) {
		return notificationobjectMapper.delete(notificationobject) ;
	}

}
