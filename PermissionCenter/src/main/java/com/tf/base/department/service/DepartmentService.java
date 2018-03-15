package com.tf.base.department.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tf.base.department.domain.DepartmentInfo;
import com.tf.base.department.persistence.DepartmentQueryMapper;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentQueryMapper departmentQueryMapper;
	
	/**
	 * 根据部门名称查询部门的所有上级机构（包含本部门）
	 * @param name 部门名称
	 * @return
	 */
	public List<DepartmentInfo> getDepartmentInfosWithParentsByName(String name){
		
		if(!StringUtils.isEmpty(name)){
			List<DepartmentInfo> departmentInfoList = new ArrayList<DepartmentInfo>();
			List<DepartmentInfo> queryDepartmentInfo = departmentQueryMapper.getDepartmentInfosByNameLike(name);
			departmentInfoList.addAll(queryDepartmentInfo);
			if (queryDepartmentInfo != null && !queryDepartmentInfo.isEmpty()) {
				for (DepartmentInfo departmentInfo : queryDepartmentInfo) {
					queryParemntDepartmentWithParentsInfo(departmentInfo, departmentInfoList);
				}
			}
			return departmentInfoList;
		}
		return null;
	}
	/**
	 * 根据部门名称查询部门的所有下级机构（包含本部门）
	 * @param name 部门名称
	 * @return
	 */
	public List<DepartmentInfo> getDepartmentInfosWithChildByName(String name){
		
		if(!StringUtils.isEmpty(name)){
			List<DepartmentInfo> departmentInfoList = new ArrayList<DepartmentInfo>();
			List<DepartmentInfo> queryDepartmentInfo = departmentQueryMapper.getDepartmentInfosByNameLike(name);
			departmentInfoList.addAll(queryDepartmentInfo);
			if (queryDepartmentInfo != null && !queryDepartmentInfo.isEmpty()) {
				for (DepartmentInfo departmentInfo : queryDepartmentInfo) {
					queryParemntDepartmentWithChildInfo(departmentInfo, departmentInfoList);
				}
			}
			return departmentInfoList;
		}
		return null;
	}
	
	
	private void queryParemntDepartmentWithParentsInfo(DepartmentInfo departmentInfo,List<DepartmentInfo> departmentInfoList){
		if (departmentInfo != null && !StringUtils.isEmpty(departmentInfo.getHigherDepart())) {
			List<DepartmentInfo> parentList = departmentQueryMapper.getDepartmentInfoById(departmentInfo.getHigherDepart());
			if (parentList != null && !parentList.isEmpty()) {
				
				for (DepartmentInfo departmentInfo2 : parentList) {
					if (!departmentInfoList.contains(departmentInfo2)) {
						departmentInfoList.add(departmentInfo2);
					}
					queryParemntDepartmentWithParentsInfo(departmentInfo2, departmentInfoList);
				}
			}
		}
	}
	private void queryParemntDepartmentWithChildInfo(DepartmentInfo departmentInfo,List<DepartmentInfo> departmentInfoList){
		if (departmentInfo != null && !StringUtils.isEmpty(departmentInfo.getHigherDepart())) {
			List<DepartmentInfo> parentList = departmentQueryMapper.getDepartmentInfoByParentId(departmentInfo.getId());
			if (parentList != null && !parentList.isEmpty()) {
				
				for (DepartmentInfo departmentInfo2 : parentList) {
					if (!departmentInfoList.contains(departmentInfo2)) {
						departmentInfoList.add(departmentInfo2);
					}
					queryParemntDepartmentWithParentsInfo(departmentInfo2, departmentInfoList);
				}
			}
		}
	}
}
