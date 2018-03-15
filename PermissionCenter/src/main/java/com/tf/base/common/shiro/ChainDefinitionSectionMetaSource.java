package com.tf.base.common.shiro;

import java.util.List;

import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tf.base.resource.domain.ResourceInfo;
import com.tf.base.resource.persistence.ResourceQueryMapper;


/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

	//@Resource(name="permissionClientServiceImpl")
	@Autowired
	private ResourceQueryMapper resourceQueryMapper;
	



	// 静态资源访问权限
	private String filterChainDefinitions = null;

	public Ini.Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		List<ResourceInfo> lists = resourceQueryMapper.getResourceInfo(0, null);
		for (ResourceInfo resources : lists) {
			// 构成permission字符串
			if (!StringUtils.isEmpty(resources.getResourceurl()) && !StringUtils.isEmpty(resources.getPermission())) {
				String permission = "perms[" + resources.getPermission() + "]";
				// 不对角色进行权限验证
				// 如需要则 permission = "roles[" + resources.getResKey() + "]";
				section.put(resources.getResourceurl() + "", permission);
			}

		}
		// 所有资源的访问权限，必须放在最后
		section.put("/**", "authc");
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
