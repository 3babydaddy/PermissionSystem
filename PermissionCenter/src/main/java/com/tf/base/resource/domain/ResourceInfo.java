package com.tf.base.resource.domain;

public class ResourceInfo {

	private String id; 
	private String systemid;
	private String resourceurl;
	private String resourcename;
	private String resourceorder;
	private String resourcelevel;
	private String type;
	private String permission; 
	private String _parentId;
	private String icon;
	private Integer sortIndx;
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
	public String getResourceurl() {
		return resourceurl;
	}
	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}
	public String getResourcename() {
		return resourcename;
	}
	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}
	public String getResourceorder() {
		return resourceorder;
	}
	public void setResourceorder(String resourceorder) {
		this.resourceorder = resourceorder;
	}
	public String getResourcelevel() {
		return resourcelevel;
	}
	public void setResourcelevel(String resourcelevel) {
		this.resourcelevel = resourcelevel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSortIndx() {
		return sortIndx;
	}
	public void setSortIndx(Integer sortIndx) {
		this.sortIndx = sortIndx;
	}
	
}
