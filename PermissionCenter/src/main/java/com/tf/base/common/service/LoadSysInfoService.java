package com.tf.base.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.constants.CommonConstants;
import com.tf.base.common.redis.dao.RedisDao;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;

import net.sf.json.JSONObject;

@Service
public class LoadSysInfoService {

	private Map<String,SystemInfo> sysInfoTable = new LinkedHashMap<String,SystemInfo>();
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	@Autowired
	private RedisInfoService redisInfoService;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	public LoadSysInfoService(SystemQueryMapper systemQueryMapper) {
		
//		List<SystemInfo> list = systemQueryMapper.getAllSystemInfo();
//		
//		for (SystemInfo item : list) {
//				
//			sysInfoTable.put(item.getId(), item);
//		}
	}
	
	/**
	 * 重新加载数据
	 */
    public synchronized void reloadParamManager() {

    	redisInfoService.initSystemJson();
    }
	
	public String getSysName(String id) {
		JSONObject system = getSystemInfo();
		if (id == null) {
			return "";
		}
		return system.getJSONObject(id).isNullObject() ? "" :JsonToSystem(system.getJSONObject(id)).getName();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,String>> getDropDownList(String appValue) {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		JSONObject system = getSystemInfo();
		
    	Set set = system.entrySet();         
    	Iterator i = set.iterator();   
    	
    	while(i.hasNext()){
    	     Map.Entry<String, JSONObject> entry1=(Map.Entry<String, JSONObject>)i.next();    
    	     
    	     Map<String, String> showMap = new HashMap<String, String>();
             showMap.put("value", entry1.getKey());
             showMap.put("showMsg",  JsonToSystem(entry1.getValue()).getName());
             if (entry1.getKey().equals(appValue)) {
                 showMap.put("showSel", "selected");
             } else {
                 showMap.put("showSel", "");
             }
             
             list.add(showMap);
    	}   

    	return list;
	}

	public SystemInfo JsonToSystem(JSONObject json) {
		return (SystemInfo)JSONObject.toBean(json, SystemInfo.class);
	}
	
	public List<String> getAllSystem() {
		
		List<String> result = new ArrayList<String> (); 
		JSONObject system = getSystemInfo();
		result.addAll(system.keySet());
		
		return result;
	}
	
	public List<SystemInfo> getAllSystemIdAndName(){
		List<SystemInfo> result = new ArrayList<SystemInfo>();
		JSONObject system = getSystemInfo();
		result.addAll(system.values());
		return result;
	}

	public JSONObject getSystemInfo() {
		JSONObject system = JSONObject.fromObject(redisDao.get(CommonConstants.PERMISSION_SYSINFO));
		return system;
	}
	
}
