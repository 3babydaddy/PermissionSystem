package com.tf.base.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.constants.CommonConstants;
import com.tf.base.common.redis.dao.RedisDao;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentQueryMapper;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class RedisInfoService {
   
	private static Logger log = LoggerFactory.getLogger(RedisInfoService.class);
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	@Autowired
	private DepartmentQueryMapper departmentQueryMapper;
	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	public void init(){
         this.initSystemJson();
         this.initRoleJsonArray();
         this.initDeptJson();
	}
	
	
	

	/**
	 * 初始化role
	 * @return
	 */
	public Map<String,JSONObject> initRoleJsonArray() {
		this.delPermission(CommonConstants.PERMISSION_ROLEINFO+ "*");
		JSONObject roleJson = null;
		RoleInfo param = null;
		Map<String,JSONObject>  map = new HashMap<String,JSONObject>();
		List<SystemInfo> list = systemQueryMapper.getAllSystemInfo();
	    for(SystemInfo  item:list){
	    	roleJson = new JSONObject();
	    	
	    	param = new RoleInfo();
	    	param.setAvail("0");
	    	param.setSystemid(item.getId());
	    	
	    	List<RoleInfo> roles = roleQueryMapper.getRoleInfos(param);
	    	for(RoleInfo role :roles){
	    		roleJson.put(role.getId(), role);
	    	}
	    	map.put(CommonConstants.PERMISSION_ROLEINFO + item.getId(), roleJson);
	    }
	    for(String key : map.keySet()){
        	redisDao.set(key, map.get(key));
        	log.info("更新 redis role 缓存 >>>>>>>>>>>>>" + map.get(key).toString());
        }
		return map;
	}

	/**
	 * 批量删除角色
	 * @param pattern
	 */
	private void delPermission(String pattern) {
		Set<String> keys = redisDao.getKeys(pattern);
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			redisDao.del(it.next());
		}
	}




	/**
	 * 初始化系统json对象
	 * @return
	 */
	public JSONObject initSystemJson() {
		redisDao.del(CommonConstants.PERMISSION_SYSINFO);
	    JSONObject system = new JSONObject();
	    List<SystemInfo> list = systemQueryMapper.getAllSystemInfo();
	    for(SystemInfo  item:list){
	    	system.put(item.getId(), item);
	    }
	    redisDao.set(CommonConstants.PERMISSION_SYSINFO, system);
		log.info("更新 redis system 缓存 >>>>>>>>>>>>>" + system.toString());
		return system;
	}
	

	public JSONObject initDeptJson(){
		redisDao.del(CommonConstants.PERMISSION_DEPTINFO);
		JSONObject dept = new JSONObject();
		List<DepartmentInfo> list = departmentQueryMapper.getAllDepartmentInfo("");
		for (DepartmentInfo item : list) {
			
			dept.put(item.getId(), item);
		}
		redisDao.set(CommonConstants.PERMISSION_DEPTINFO, dept);
		log.info("更新 redis 部门 缓存 >>>>>>>>>>>>>" + dept.toString());
		return dept;
	}
	
}
