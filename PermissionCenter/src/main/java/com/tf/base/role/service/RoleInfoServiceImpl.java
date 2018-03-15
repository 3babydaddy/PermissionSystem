package com.tf.base.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.domain.SystenLogInfo;
import com.tf.base.common.service.LogService;
import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.resource.persistence.ResourceQueryMapper;
import com.tf.base.role.domain.RoleInfo;
import com.tf.base.role.domain.RoleResource;
import com.tf.base.role.persistence.RoleCreateMapper;
import com.tf.base.role.persistence.RoleModifyMapper;
import com.tf.base.role.persistence.RoleQueryMapper;
import com.tf.base.system.domain.SystemInfo;
import com.tf.base.user.domain.UserInfo;

@Service("roleInfoServiceImpl")
@Transactional(rollbackFor=Exception.class)

public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private LogService logService;
	
	@Autowired
	private RoleCreateMapper roleCreateMapper;
	
	@Autowired
	private RoleModifyMapper roleModifyMapper;
	
	@Autowired
	private RoleQueryMapper roleQueryMapper;
	@Autowired
	private ResourceQueryMapper resourceQueryMapper;
	
	public int saveRoleInfo(RoleInfo roleInfo, List<RoleResource> rrInfo,UserInfo user){
		int insertResult = 0;
		insertResult = roleCreateMapper.insertRoleInfo(roleInfo);
		if (insertResult > 0) {
			roleCreateMapper.deleteRoleResouces(roleInfo.getId(), roleInfo.getSystemid());
			if (!rrInfo.isEmpty()) {
				insertResult = roleCreateMapper.insertRoleResouces(rrInfo, roleInfo.getId(), roleInfo.getSystemid());									
			}
		}
		//增加日志
		String systemAndRight = "";
		List<Map> resourceList = roleQueryMapper.getResourceByRoleid(roleInfo.getId());
		String systemname="";
		if(resourceList!=null&&resourceList.size()>0){
			systemname = (String) resourceList.get(0).get("name");
	        Iterator iterator=resourceList.iterator();  
	        while(iterator.hasNext()){  
	            Map tmpMap = (Map) iterator.next(); 
	            systemAndRight+=tmpMap.get("resourcename")+",";
	        }
		}
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(roleInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.ROLEINFO.toString());
		osInfo.setOperationUser(user.getShowname());
		osInfo.setOperationDetail(logService.getDetailInfo("log.roleinfo.create",
				roleInfo.getName(),systemname,roleInfo.getAvail().equals("0")?"有效":"无效",
						systemAndRight));
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.CREATE.toString());
		return insertResult > 0 ? 0 : -1;
	}
	
	public int modifyRoleInfo(RoleInfo roleInfo,List<RoleResource> rrInfo,UserInfo user){
		//增加日志需要start
		RoleInfo oldRoleInfo= roleQueryMapper.getRoleInfoById(roleInfo.getId());
		String oldsystemAndRight = "";
		String oldsystemname ="";
		List<Map> oldresourceList = roleQueryMapper.getResourceByRoleid(roleInfo.getId());
		if(oldresourceList!=null&&oldresourceList.size()>0){
			oldsystemname = (String) oldresourceList.get(0).get("name");
	        Iterator iterator2=oldresourceList.iterator();  
	        while(iterator2.hasNext()){  
	            Map tmpMap2 = (Map) iterator2.next(); 
	            oldsystemAndRight+=tmpMap2.get("resourcename")+",";
	        }
		}
		//end
		int updateResult = roleModifyMapper.modifyRoleInfo(roleInfo);
		if (updateResult > 0) {
			roleCreateMapper.deleteRoleResouces(roleInfo.getId(), roleInfo.getSystemid());
			if(rrInfo!=null&&rrInfo.size()>0){
				updateResult = roleCreateMapper.insertRoleResouces(rrInfo, roleInfo.getId(), roleInfo.getSystemid());
			}
		}
		//增加日志
		String systemAndRight = "";
		List<Map> resourceList = roleQueryMapper.getResourceByRoleid(roleInfo.getId());
		String systemname="";
		if(resourceList!=null&&resourceList.size()>0){
			systemname = (String) resourceList.get(0).get("name");
	        Iterator iterator=resourceList.iterator();  
	        while(iterator.hasNext()){  
	            Map tmpMap = (Map) iterator.next(); 
	            systemAndRight+=tmpMap.get("resourcename")+",";
	        }
		}
		SystenLogInfo osInfo = new SystenLogInfo();
		osInfo.setId(roleInfo.getId());
		osInfo.setLogModule(ConstantsUtils.LOG_MODULE.ROLEINFO.toString());
		osInfo.setOperationUser(user.getShowname());
		osInfo.setOperationDetail(logService.getDetailInfo("log.roleinfo.modify",
				oldRoleInfo.getName(),roleInfo.getName(),
				oldsystemname,systemname,
				oldRoleInfo.getAvail().equals("0")?"有效":"无效",roleInfo.getAvail().equals("0")?"有效":"无效",
						oldsystemAndRight,systemAndRight));
		logService.save(osInfo, ConstantsUtils.LOG_OPERATION_TYPE.MODIFY.toString());
		return updateResult > 0 ? 0:-1;
	}
	
	
	
}