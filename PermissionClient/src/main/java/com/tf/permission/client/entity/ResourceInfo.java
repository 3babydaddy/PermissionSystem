package com.tf.permission.client.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;
/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class ResourceInfo implements Serializable {

	/**
	 * 
	 */
	public static final String MAINMENU_LEVEL = "2"; // 一级菜单在资源中的等级
	private static final long serialVersionUID = 5918955234546468384L;

	private String id;
	private String resourceurl;
	private String resourcename;
	private String resourcelevel;
	private String resourceorder;
	private String permission;
	private String _parentId;
	private String systemid;
	private String icon;
	private Integer sortIndx;
	private List<ResourceInfo> subResources ;
	
	
	
	private ResourceType type = ResourceType.menu; //资源类型
	
	public static enum ResourceType {
        menu("菜单"), button("按钮");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getResourcelevel() {
		return resourcelevel;
	}

	public void setResourcelevel(String resourcelevel) {
		this.resourcelevel = resourcelevel;
	}

	public String getResourceorder() {
		return resourceorder;
	}

	public void setResourceorder(String resourceorder) {
		this.resourceorder = resourceorder;
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

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
//	修复type为空无法转换json的bug
	/*public void setType(String type) {
		if (!StringUtils.isEmpty(type)) {
			setType(type);			
		}
	}*/
	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ResourceInfo> getSubResources() {
		return subResources;
	}

	public void setSubResources(List<ResourceInfo> subResources) {
		this.subResources = subResources;
	}

	public Integer getSortIndx() {
		return sortIndx;
	}

	public void setSortIndx(Integer sortIndx) {
		this.sortIndx = sortIndx;
	}

	

}
