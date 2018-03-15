package com.tf.base.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.LogService;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentQueryMapper;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.system.persistence.SystemQueryMapper;
import com.tf.base.user.domain.UserInfo;
import com.tf.base.user.persistence.UserCreateMapper;
import com.tf.base.user.persistence.UserInfoMapper;
import com.tf.base.user.persistence.UserModifyMapper;
import com.tf.base.user.persistence.UserQueryMapper;

import net.sf.json.JSONArray;

@Service("userInfoServiceImpl")
@Transactional(rollbackFor=Exception.class)

public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private LogService logService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserCreateMapper userCreateMapper;

	@Autowired
	private SystemQueryMapper systemQueryMapper;
	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	
	@Autowired
	private UserModifyMapper userModifyMapper;
	@Autowired
	private BaseService baseService;
	@Autowired
	private DepartmentQueryMapper dqMapper;
	@Autowired
	private UserQueryMapper userQueryMapper;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<UserInfo> getAllUserInfo(UserInfo userInfo) throws Exception {
		return userInfoMapper.getAllUserInfo( userInfo);
	}
	
   public boolean updateUserInfo(UserInfo userInfo) throws Exception{
		
	     userInfoMapper.updateUserInfo(userInfo);
		 return true;
   }
   public UserInfo selectById(Integer id) throws Exception{
	   return userInfoMapper.selectById(id);
   }

   public int insert(UserInfo record) {
		return userInfoMapper.insert(record);
   }
   /**
    * 重置用户密码
    */
   public boolean resetPassword(UserInfo userInfo){
	   
	   return userInfoMapper.resetPassword(userInfo);
   }

	public int deleteByPrimaryKey(Integer id) {
		return userInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<UserInfo> getUserByName(String name) throws Exception {
		return userInfoMapper.getUserByName(name);
	}
	
	
	public int saveUserInfo(UserInfo userInfo, String system,String selectedIds,UserInfo user){
	
		if (userInfo != null) {
			userCreateMapper.insertUserInfo(userInfo);
			
			List<String> systems = new ArrayList<String>();
			List<String> roles = new ArrayList<String>();
			
			if (!StringUtils.isEmpty(selectedIds)) {
				String [] sid = selectedIds.split(",");
				for (String item : sid) {
					
					if (!StringUtils.isEmpty(item)) {
						
						if (item.startsWith("5")) {
							
						} else {
							roles.add(item);
						}
					}
				}
			}
			
			if(!StringUtils.isEmpty(system)){
				String[] sys = system.split(",");
				
				for (String item : sys) {
					
					if (!StringUtils.isEmpty(item)) {
						systems.add(item);
					}
				}
				
			}
			
			int inserCount = 0;
			if (systems.size() > 0) {
				
				inserCount = userCreateMapper.insertUserSystem(systems, userInfo.getId());
			}
			if (roles.size() > 0 && inserCount > 0) {
				inserCount = userCreateMapper.insertUserRole(roles, userInfo.getId());
			}
			
			// 记录操作日志
			
			/*Map<String,List<String>> hasSystemAndRole = new HashMap<String,List<String>>();
			List<SystemInfo> systemInfoList = systemQueryMapper.getSystemInfoByUserId(userInfo.getId());
			String systemAndRight = "";
			if (systemInfoList != null && !systemInfoList.isEmpty()) {
				RoleInfo roleInfo = null;
				List<RoleInfo> roleList = null;
				List<String> hasRoleList = new ArrayList<String>();
				for (SystemInfo systemInfo : systemInfoList) {
					if (systemInfo != null) {
						roleInfo = new RoleInfo();
						roleInfo.setSystemid(systemInfo.getId());
						roleInfo.setUserId(userInfo.getId());
						roleList = roleQueryMapper.getRoleInfosByUserIdAndSystemId(roleInfo);
						if (roleList != null && !roleList.isEmpty()) {
							for (RoleInfo role : roleList) {
								if (role != null && !StringUtils.isEmpty(role.getName())) {
									hasRoleList.add(role.getName());
								}
							}
						}
					}
					hasSystemAndRole.put(systemInfo.getName(), hasRoleList);
					
				}
			}
			Set keySet = hasSystemAndRole.entrySet();
	        Iterator iterator=keySet.iterator();  
	        while(iterator.hasNext()){  
	                Map.Entry<String, Object> enter=(Entry<String, Object>) iterator.next();  
	                System.out.println(enter.getKey()+"\t"+enter.getValue());  
	                systemAndRight+=enter.getKey()+":"+enter.getValue();
	        } 
			List<DepartmentInfo> dptlist = dqMapper.getDepartmentInfoById(userInfo.getDepartment());
			DepartmentInfo dptinfo= dptlist.get(0);
			userInfo.setHasSystemAndRole(hasSystemAndRole);
			SystenLogInfo osInfo = new SystenLogInfo();
			osInfo.setId(userInfo.getId());
			osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
			//osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.create",JSONObject.toJSON(userInfo).toString()));
			osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.create",userInfo.getUsername(),userInfo.getShowname(),userInfo.getAvail(),dptinfo.getName(),systemAndRight));
			osInfo.setOperationUser(user.getShowname());
			logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());*/
			// 记录操作日志
			List<DepartmentInfo> dptlist = dqMapper.getDepartmentInfoById(userInfo.getDepartment());
			DepartmentInfo dptinfo= dptlist.get(0);
			String systemAndRight = getRoleAndRight(userInfo);
			SystenLogInfo osInfo = new SystenLogInfo();
			osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.create",
					userInfo.getUsername(),userInfo.getShowname(),
					userInfo.getAvail().equals("0")?"启用":"停用",
							dptinfo.getName(),systemAndRight));
			
			osInfo.setId(userInfo.getId());
			osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
			osInfo.setOperationUser(user.getShowname());
			logService.save(osInfo,ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());
			
			return inserCount > 0 ? 0 :-1;
		}
		return -1;
		
	}
	
	public int modifyUserInfo(UserInfo userInfo,String [] roleIds,String[] systemIds,UserInfo user){
		if (userInfo == null || StringUtils.isEmpty(userInfo.getId())) {
			return -1;
		}
		List<String> systems = new ArrayList<String>();
		List<String> roles = new ArrayList<String>();
		if (roleIds != null && roleIds.length >0) {
			for (String item : roleIds) {
				
				if (!StringUtils.isEmpty(item)) {
					
					if (item.startsWith("5")) {
						
					} else {
						roles.add(item);
					}
				}
			}
			
		}
		if (systemIds != null && systemIds.length >0) {
			for (String item : systemIds) {				
				if (!StringUtils.isEmpty(item)) {
					systems.add(item);
				}
			}
		}
		//增加日志 需要 start
		UserInfo oldUserInfo = userQueryMapper.findUserInfoById(userInfo.getId());
		List<DepartmentInfo> olddptlist = dqMapper.getDepartmentInfoById(oldUserInfo.getDepartment());
		DepartmentInfo olddptinfo= olddptlist.get(0);
		String oldSystemAndRight = getRoleAndRight(oldUserInfo);
		//end
		
		int modifyResult = userModifyMapper.modifyUserInfo(userInfo);
		if (modifyResult > 0) {
			userCreateMapper.deleteUserSystem(userInfo.getId());
			userCreateMapper.deleteUserRole(userInfo.getId());			
			if (systems.size() > 0) {
				userCreateMapper.insertUserSystem(systems, userInfo.getId());
			}
			if (roles.size() > 0) {
				userCreateMapper.insertUserRole(roles, userInfo.getId());
			}
		}
		// 记录操作日志
		List<DepartmentInfo> dptlist = dqMapper.getDepartmentInfoById(userInfo.getDepartment());
		DepartmentInfo dptinfo= dptlist.get(0);
		String systemAndRight = getRoleAndRight(userInfo);
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setOperationDetail(logService.getDetailInfo("log.userinfo.modify",
				oldUserInfo.getUsername(),userInfo.getUsername(),
				oldUserInfo.getShowname(),userInfo.getShowname(),
				oldUserInfo.getAvail().equals("0")?"启用":"停用",userInfo.getAvail().equals("0")?"启用":"停用",
						olddptinfo.getName(),dptinfo.getName(),
						oldSystemAndRight,systemAndRight));
		osInfo.setId(userInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.USERINFO.toString());
		osInfo.setOperationUser(user.getShowname());
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
		
		return modifyResult > 0 ? 0 : -1 ;
	}
	
	
	
	private String getRoleAndRight(UserInfo userInfo) {
		Map<String,List<String>> hasSystemAndRole = new HashMap<String,List<String>>();
		List<SystemInfo> systemInfoList = systemQueryMapper.getSystemInfoByUserId(userInfo.getId());
		String systemAndRight = "";
		if (systemInfoList != null && !systemInfoList.isEmpty()) {
			RoleInfo roleInfo = null;
			List<RoleInfo> roleList = null;
			List<String> hasRoleList = new ArrayList<String>();
			for (SystemInfo systemInfo : systemInfoList) {
				if (systemInfo != null) {
					roleInfo = new RoleInfo();
					roleInfo.setSystemid(systemInfo.getId());
					roleInfo.setUserId(userInfo.getId());
					roleList = roleQueryMapper.getRoleInfosByUserIdAndSystemId(roleInfo);
					if (roleList != null && !roleList.isEmpty()) {
						for (RoleInfo role : roleList) {
							if (role != null && !StringUtils.isEmpty(role.getName())) {
								hasRoleList.add(role.getName());
							}
						}
					}
				}
				hasSystemAndRole.put(systemInfo.getName(), hasRoleList);
				
			}
		}
		Set keySet = hasSystemAndRole.entrySet();
        Iterator iterator=keySet.iterator();  
        while(iterator.hasNext()){  
            Map.Entry<String, Object> enter=(Entry<String, Object>) iterator.next();  
            systemAndRight+=enter.getKey()+":"+enter.getValue();
        } 
        userInfo.setHasSystemAndRole(hasSystemAndRole);
        return systemAndRight;
	}
}