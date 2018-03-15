package com.tf.permission.client.shiro.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.service.PermissionClientService;
import com.tf.permission.client.shiro.filters.KickoutSessionControlFilter;
import com.tf.permission.client.shiro.filters.LoginFormAuthenticationFilter;
import com.tf.permission.client.shiro.filters.MenusFilter;
import com.tf.permission.client.shiro.filters.OwnUserFilter;
import com.tf.permission.client.shiro.filters.SysUserFilter;
import com.tf.permission.client.shiro.others.RetryLimitHashedCredentialsMatcher;
import com.tf.permission.client.shiro.realms.UserRealm;
import com.tf.permission.client.shiro.sessionJob.Quartz2SessionValidationScheduler;

/**
 * shiro shiroFilter配置文件，读取数据库权限信息，服务端资源更新后需调用loadShiroFilterChain更新filterChain权限信息
 * @author sunny
 *
 */
@Configuration
public class ShiroFilterBean {

	private static final Logger log = LoggerFactory.getLogger(ShiroFilterBean.class);
	
	public static final String SPLITSTRGROUP = ",";
	public static final String INDEXFLAG = "/";

	private String cacheConfigFile = "classpath:ehcache.xml";

	private boolean realmCachingEnabled = true;
	private boolean realmAuthenticationCachingEnabled = true;
	private String realmAuthenticationCacheName = "authenticationCache";

	private String rlhcmHashAlgorithmName = "md5";
	private boolean rlhcmStoredCredentialsHexEncoded = true;
	private int rlhcmHashIterations = 2;

	private String simpleCookieName = "sid";
	private boolean simpleCookieHttpOnly = true;
	@Value("${permissionClient.cookieMaxAge}")
	private int simpleCookieMaxAge = 180000;
	
	@Value("${permissionClient.sessionTimeout}")
	private int sessionTimeout = 180000;
	private boolean deleteInvalidSessions = true;
	private boolean sessionValidationSchedulerEnabled = true;
	private boolean sessionIdCookieEnabled = true;
	
	@Value("${permissionClient.login.usernameParam}")
	private String usernameParam;
	@Value("${permissionClient.login.passwordParam}")
	private String passwordParam;
	private static String unAuthAction;
	private static String loginUrl;
	private static String successUrl;
	
	private static String unauthorizedUrl;
	
	private static String anonConfigStr;

	private static String ajaxLogin;
	
//	@Value("${permissionClient.logout.logoutRedirectUrl}")
//	private String logoutRedirectUrl;
	
//	@Value("${permissionClient.loginPersion.kickoutAfter}")
	private boolean kickoutAfter = false;
	@Value("${permissionClient.loginPersion.maxSession}")
	private int kickoutMaxSession;
	@Value("${permissionClient.loginPersion.kickoutUrl}")
	private String kickoutUrl;
	@Value("${permissionClient.loginPersion.unCheckNames}")
	private String unCheckNames;
	
	@Value("${permissionClient.login.needAjaxLogin}")
	private String needAjaxLogin;
	
	@Value("${permissionClient.login.notInAjaxUrl}")
	private String notInAjaxUrl;
	
	@Autowired
	private EhCacheManager cacheManager;
	@Autowired
	private DefaultWebSessionManager sessionManager;
//	@Autowired
//	private LoginFormAuthenticationFilter formAuthenticationFilter;
//	@Autowired
//	private SysUserFilter sysUserFilter;
//	@Autowired
//	private KickoutSessionControlFilter kickoutSessionControlFilter;
//	@Autowired
//	private LogoutFilter logoutFilter;

	@Autowired
	private PermissionClientService permissionClientService;

	
	
	
	@Bean(name = "cacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile(cacheConfigFile);
		cacheManager = em;
		return em;
	}

	@Bean(name = "credentialsMatcher")
	@DependsOn("cacheManager")
	public RetryLimitHashedCredentialsMatcher getCredentialsMatcher() {
		RetryLimitHashedCredentialsMatcher rlhcm = new RetryLimitHashedCredentialsMatcher(cacheManager);
		rlhcm.setHashIterations(rlhcmHashIterations);
		rlhcm.setStoredCredentialsHexEncoded(rlhcmStoredCredentialsHexEncoded);
		rlhcm.setHashAlgorithmName(rlhcmHashAlgorithmName);
		return rlhcm;
	}

	@Bean(name = "userRealm")
	public UserRealm getUserRealm(RetryLimitHashedCredentialsMatcher credentialsMatcher) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager);
		userRealm.setCachingEnabled(realmCachingEnabled);
		userRealm.setAuthenticationCachingEnabled(realmAuthenticationCachingEnabled);
		userRealm.setAuthenticationCacheName(realmAuthenticationCacheName);
		userRealm.setCredentialsMatcher(credentialsMatcher);
		userRealm.setPermissionService(permissionClientService);
		return userRealm;
	}

	

	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}

	@Bean(name = "sessionIdGenerator")
	public JavaUuidSessionIdGenerator getJavaUuidSessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}

	@Bean(name = "sessionIdCookie")
	public SimpleCookie getSimpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie(simpleCookieName);
		simpleCookie.setHttpOnly(simpleCookieHttpOnly);
		simpleCookie.setMaxAge(simpleCookieMaxAge);
		return simpleCookie;
	}

	@Bean(name = "sessionDAO")
	public EnterpriseCacheSessionDAO getEnterpriseCacheSessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
		EnterpriseCacheSessionDAO eCacheSessionDao = new EnterpriseCacheSessionDAO();
		eCacheSessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
		eCacheSessionDao.setSessionIdGenerator(sessionIdGenerator);
		return eCacheSessionDao;
	}

	@Bean(name = "sessionValidationScheduler")
	public Quartz2SessionValidationScheduler getQuartz2SessionValidationScheduler() {
		Quartz2SessionValidationScheduler qSessionValidationScheduler = new Quartz2SessionValidationScheduler();
		qSessionValidationScheduler.setSessionValidationInterval(sessionTimeout);
		return qSessionValidationScheduler;
	}

	// 会话管理
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager getDefaultWebSessionManager(
			Quartz2SessionValidationScheduler sessionValidationScheduler, EnterpriseCacheSessionDAO sessionDAO,
			SimpleCookie sessionIdCookie) {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setGlobalSessionTimeout(sessionTimeout);
		defaultWebSessionManager.setDeleteInvalidSessions(deleteInvalidSessions);
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(sessionValidationSchedulerEnabled);
		defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		defaultWebSessionManager.setSessionDAO(sessionDAO);
		defaultWebSessionManager.setSessionIdCookie(sessionIdCookie);
		defaultWebSessionManager.setSessionIdCookieEnabled(sessionIdCookieEnabled);
		sessionValidationScheduler.setSessionManager(defaultWebSessionManager);
		return defaultWebSessionManager;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm,
			DefaultWebSessionManager sessionManager) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(userRealm);
		// <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
		dwsm.setCacheManager(cacheManager);
		dwsm.setSessionManager(sessionManager);
		return dwsm;
	}

	@Bean(name = "methodInvokingFactoryBean")
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(new Object[] { securityManager });
		return methodInvokingFactoryBean;
	}
	
	
	
	
	/**
	 *  * ShiroFilter<br/>
	 * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
	 * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean(name = "shiroFilter")
//	@Scope("singleton")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		
		log.info("ShiroFilterFactoryBean(shiroFilter) Bean Creating>>>>>>>> ");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl(successUrl);
//		无权限跳转的页面
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
//		shiro的自定义filter设置
		shiroFilterFactoryBean.setFilters(getFilters());
//		设置权限信息
		List<ResourceInfo> resourceList = permissionClientService.findAllPermissionBySystemId();
		loadShiroFilterChain(shiroFilterFactoryBean, resourceList);
		
		log.info("ShiroFilterFactoryBean(shiroFilter) Bean Created<<<<<<<<<<< ");
		return shiroFilterFactoryBean;
		
		
		
	}

//	/**
//	 * 自定义Filter的设置
//	 * @return
//	 */
//	public Map<String, Filter> getFilters() {
//		Map<String, Filter> filters = new HashMap<String, Filter>();
//		filters.put("authc", formAuthenticationFilter);
//		filters.put("sysUser", sysUserFilter);
//		filters.put("kickout", kickoutSessionControlFilter);
//		filters.put("logout", logoutFilter);
//		// SslFilter sslFilter = new SslFilter();
//		// sslFilter.setPort(8443);
//		// filters.put("ssl", sslFilter);
//		return filters;
//	}
	public Map<String, Filter> getFilters() {
		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("authc", getLoginFormAuthenticationFilter());
		filters.put("sysUser", getSysUserFilter());
		filters.put("kickout", getKickoutSessionControlFilter());
		filters.put("logout", getLogoutFilter());
		filters.put("ownUser", getOwnUserAuthenticationFilter());
		//filters.put("menus", getMenusFilter());
		// SslFilter sslFilter = new SslFilter();
		// sslFilter.setPort(8443);
		// filters.put("ssl", sslFilter);
		return filters;
	}
	private Filter getMenusFilter() {
		
		MenusFilter m = new MenusFilter();
		m.setPermissionService(permissionClientService);
		return m;
	}

	/**
	 * 
	 * 加载shiroFilter权限控制规则（从数据库读取然后配置），配置顺序为unauthorizedUrl、unAuthAction、logout、anon、successUrl、resourcePermission、/**
	 * @param shiroFilterFactoryBean
	 * @param resourceList
	 */
	public static void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,
			List<ResourceInfo> resourceList) {
		/////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
		log.debug("加载权限设置规则>>>>>>>>>>>>>>>>>>>>>");
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//
		if (!StringUtils.isEmpty(unauthorizedUrl)) {
			filterChainDefinitionMap.put(unauthorizedUrl, "anon");
		}
		
		if (!StringUtils.isEmpty(ajaxLogin)) {
			filterChainDefinitionMap.put(ajaxLogin, "anon");
		}

		if (!StringUtils.isEmpty(unAuthAction)) {
			int lastIndex = unAuthAction.lastIndexOf(INDEXFLAG);
			if (lastIndex != -1) {
				if (unAuthAction.length() == 1) {
					filterChainDefinitionMap.put(unAuthAction + "**", "authc");
				}
				if (lastIndex > 0) {
					filterChainDefinitionMap.put(unAuthAction.substring(0, lastIndex) + "/**", "authc");
				} else {
					filterChainDefinitionMap.put(unAuthAction + "/**", "authc");
				}
			}
		}

		filterChainDefinitionMap.put("/logout", "logout");
		setAnonCfgToFilterChainDefinitionMap(filterChainDefinitionMap, anonConfigStr);
		if (!StringUtils.isEmpty(successUrl)) {
			filterChainDefinitionMap.put(successUrl, "ownUser,sysUser");
		}
		setPermissionToFilterChain(filterChainDefinitionMap, resourceList);
		filterChainDefinitionMap.put("/**", "kickout,ownUser");
		//filterChainDefinitionMap.put("/**", "menus");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		log.debug("权限设置规则:"+filterChainDefinitionMap);
		log.debug("权限设置规则加载完毕<<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 设置数据库中的权限规则 
	 * @param filterChainDefinitionMap
	 * @param resourceList
	 */
	private static void setPermissionToFilterChain(Map<String, String> filterChainDefinitionMap,
			List<ResourceInfo> resourceList) {
		if (resourceList != null && !resourceList.isEmpty()) {
			for (ResourceInfo resourceInfo : resourceList) {
				if (resourceInfo != null && !StringUtils.isEmpty(resourceInfo.getPermission())
						&& !StringUtils.isEmpty(resourceInfo.getResourceurl())) {
					filterChainDefinitionMap.put(resourceInfo.getResourceurl(),
							"authc,perms[" + resourceInfo.getPermission() + "]");
				}
			}
		}
	}

	/**
	 * 设置无需shiro进行权限控制的url，即 url=anon
	 * @param filterChainDefinitionMap
	 * @param anonConfigStr
	 */
	private static void setAnonCfgToFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap,
			String anonConfigStr) {
		String[] anonConfigArray = null;
		if (!StringUtils.isEmpty(anonConfigStr)) {
			anonConfigArray = anonConfigStr.split(SPLITSTRGROUP);
			if (anonConfigArray != null && anonConfigArray.length > 0) {
				for (int i = 0; i < anonConfigArray.length; i++) {
					if (!StringUtils.isEmpty(anonConfigArray[i])) {
						filterChainDefinitionMap.put(anonConfigArray[i], "anon");
					}

				}
			}
		}
	}

	
	
	
	
	
	public SysUserFilter getSysUserFilter() {
		SysUserFilter sysUserFilter = new SysUserFilter();
		sysUserFilter.setPermissionService(permissionClientService);
		return sysUserFilter;
	}

	public OwnUserFilter getOwnUserAuthenticationFilter() {
		OwnUserFilter filter = new OwnUserFilter();
		
		if ("1".equals(needAjaxLogin)) {
			
			filter.setNeedAjax(true);
			filter.setNotInAjax(notInAjaxUrl);
		}
		
		return filter;
	}
	
	// @Bean(name = "formAuthenticationFilter")
	public LoginFormAuthenticationFilter getLoginFormAuthenticationFilter() {
		LoginFormAuthenticationFilter loginFormAuthenticationFilter = new LoginFormAuthenticationFilter();
		loginFormAuthenticationFilter.setUsernameParam(usernameParam);
		loginFormAuthenticationFilter.setPasswordParam(passwordParam);
		loginFormAuthenticationFilter.setLoginUrl(unAuthAction);
		return loginFormAuthenticationFilter;
	}

	// @Bean(name = "kickoutSessionControlFilter")
	public KickoutSessionControlFilter getKickoutSessionControlFilter() {
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		kickoutSessionControlFilter.setCacheManager(cacheManager);
		kickoutSessionControlFilter.setSessionManager(sessionManager);
		kickoutSessionControlFilter.setKickoutAfter(kickoutAfter);
		kickoutSessionControlFilter.setMaxSession(kickoutMaxSession);
		kickoutSessionControlFilter.setKickoutUrl(kickoutUrl);
		kickoutSessionControlFilter.setUnCheckNames(unCheckNames);
		return kickoutSessionControlFilter;
	}

	public LogoutFilter getLogoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter();
//		logoutFilter.setRedirectUrl(logoutRedirectUrl);
		return logoutFilter;
	}
	
	
	@Value("${permissionClient.login.loginUrl}")
	public void setLoginUrl(String loginUrl) {
		ShiroFilterBean.loginUrl = loginUrl;
	}

	@Value("${permissionClient.login.successUrl}")
	public void setSuccessUrl(String successUrl) {
		ShiroFilterBean.successUrl = successUrl;
	}

	@Value("${permissionClient.unauthorizedUrl}")
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		ShiroFilterBean.unauthorizedUrl = unauthorizedUrl;
	}

	@Value("${permissionClient.anonConfigStr}")
	public void setAnonConfigStr(String anonConfigStr) {
		ShiroFilterBean.anonConfigStr = anonConfigStr;
	}

	@Value("${permissionClient.login.unAuthAction}")
	public void setUnAuthAction(String unAuthAction) {
		ShiroFilterBean.unAuthAction = unAuthAction;
	}
	
	@Value("${permissionClient.login.ajaxLogin}")
	public void setajaxLogin(String ajaxLogin) {
		ShiroFilterBean.ajaxLogin = ajaxLogin;
	}
}
