package com.tf.base.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.constants.CommonConstants;
import com.tf.base.common.redis.dao.RedisDao;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.user.domain.RoleTreeInfo;

import net.sf.json.JSONObject;

@Service
public class LoadRoleInfoService {

	private Map<String,RoleInfo> roleInfoTable = new LinkedHashMap<String,RoleInfo>();
	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	private RedisInfoService redisInfoService;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	public LoadRoleInfoService(RoleQueryMapper roleQueryMapper) {
		
//		List<RoleInfo> list = roleQueryMapper.getAllRoleInfo(null);
//		
//		for (RoleInfo item : list) {
//				
//			roleInfoTable.put(item.getId(), item);
//		}
	}
	
	/**
	 * 重新加载数据
	 */
    public synchronized void reloadParamManager() {

    	redisInfoService.initRoleJsonArray();
    }
	
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<RoleTreeInfo> getRoleInfo(String system) {
    	List<RoleTreeInfo> result = new ArrayList<RoleTreeInfo>();
    	
    	JSONObject roleJson = JSONObject.fromObject(redisDao.get(CommonConstants.PERMISSION_ROLEINFO + system));
    	RoleTreeInfo item;
    	
    	if(roleJson.isNullObject()){
    		return result;
    	}
    	
    	Iterator<String> it = roleJson.keys();	
    	while(it.hasNext()){
    		String key = it.next();
    		item = new RoleTreeInfo();
    		item.setId(key);
    		item.setText( JsonToRole(roleJson, key).getName());
    		
    		result.add(item);
    		
    	}
    	
		return result;
	}

	public RoleInfo JsonToRole(JSONObject roleJson, String key) {
		return (RoleInfo)JSONObject.toBean(roleJson.getJSONObject(key),RoleInfo.class);
	}
    
	
}
