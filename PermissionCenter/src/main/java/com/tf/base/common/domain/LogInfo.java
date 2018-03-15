package com.tf.base.common.domain;

import java.util.Date;

public class LogInfo {

	private String id; 
	private String systemid; 
	private String userid; 
	private String type; 
	private String ip; 
	private String description; 
	private Date createtime; 
	private String creator;
	private String operationTimeStart; 
	private String operationTimeEnd; 
	
	private String systemName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperationTimeStart() {
		return operationTimeStart;
	}
	public void setOperationTimeStart(String operationTimeStart) {
		this.operationTimeStart = operationTimeStart;
	}
	public String getOperationTimeEnd() {
		return operationTimeEnd;
	}
	public void setOperationTimeEnd(String operationTimeEnd) {
		this.operationTimeEnd = operationTimeEnd;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	} 
	
	
}
