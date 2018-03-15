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
import org.springframework.util.StringUtils;

import com.tf.base.common.constants.CommonConstants;
import com.tf.base.common.redis.dao.RedisDao;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentQueryMapper;

import net.sf.json.JSONObject;

@Service
public class LoadDeprInfoService {

	private Map<String,DepartmentInfo> deprInfoTable = new LinkedHashMap<String,DepartmentInfo>();
	
	@Autowired
	private DepartmentQueryMapper departmentQueryMapper;
	
	@Autowired
	private RedisInfoService redisInfoService;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	public LoadDeprInfoService(DepartmentQueryMapper departmentQueryMapper) {
		
//		List<DepartmentInfo> list = departmentQueryMapper.getAllDepartmentInfo();
//		
//		for (DepartmentInfo item : list) {
//				
//			deprInfoTable.put(item.getId(), item);
//		}
	}
	
	/**
	 * 重新加载数据
	 */
    public synchronized void reloadParamManager() {
    	redisInfoService.initDeptJson();
    }
	
	public String getDeprName(String id) {
		JSONObject department = getDeptInfo() ;
		if (StringUtils.isEmpty(id)) {
			return "";
		}
		return department.getJSONObject(id).isNullObject()  ? "" :JsonToDept(department.getJSONObject(id)).getName();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String,String>> getDropDownList(String appValue) {
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    	
		JSONObject department = getDeptInfo() ;
		
    	Set set = department.entrySet();         
    	Iterator i = set.iterator();   
    	
    	while(i.hasNext()){
    	     Map.Entry<String, JSONObject> entry1=(Map.Entry<String, JSONObject>)i.next();    
    	     
    	     Map<String, String> showMap = new HashMap<String, String>();
             showMap.put("value", entry1.getKey());
             showMap.put("showMsg",  JsonToDept(entry1.getValue()).getName());
             if (entry1.getKey().equals(appValue)) {
                 showMap.put("showSel", "selected");
             } else {
                 showMap.put("showSel", "");
             }
             
             list.add(showMap);
    	}   

    	return list;
	}

	public JSONObject getDeptInfo() {
		return JSONObject.fromObject(redisDao.get(CommonConstants.PERMISSION_DEPTINFO));
	}

	public DepartmentInfo JsonToDept(JSONObject json) {
		return (DepartmentInfo)JSONObject.toBean(json,DepartmentInfo.class);
	}
}
