
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tf.permission.client.constants.PermissionEnum;
import com.tf.permission.client.entity.DepartmentInfo;
import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.entity.RoleInfo;
import com.tf.permission.client.entity.SystemUserInfo;
import com.tf.permission.client.entity.User;
import com.tf.permission.client.service.PermissionClientService;
import com.tf.permission.client.service.PermissionClientServiceImpl;

public class ClientTest {

	@Test
	public void test() {
//		
//		String host = "115.182.234.156";
//		String port = "8280";
//		String host = "127.0.0.1";
//		String port = "8080";
//		String systemId = "500034";
//		PermissionClientService perClient = new PermissionClientServiceImpl(host, port, systemId);
//		Set<String> deptList = null;
//		List<ResourceInfo> perList  = null;
//		
//
//		User user = perClient.findUserByUsername("wangguangda");
//		System.out.println(user.getLoginName());
//		deptList = perClient.findPermissionsByUserName("wangguangda");
//		for (String permission : deptList) {
//			System.out.println(permission);
//		}
//		List<ResourceInfo> perList = perClient.findAllPermissionBySystemId();
//		List<DepartmentInfo> depList = perClient.findAllDepartments();
//		Set<String> permissions = new HashSet<String>();
//		permissions.add("index:resourceconfig:channel");
//		perList = null;
//		perList = perClient.findMenusByPermissions(permissions);
//		String menusStr = perClient.findMenusByUserName("wangguangda");
//		perList = null;
//		perList = perClient.getMenusByUserName("guolijuan");
//		for (ResourceInfo resourceInfo : perList) {
//			System.out.println(resourceInfo.getPermission());
//		}
//		List<RoleInfo> roleList = perClient.findRolesByUserName("tg");
//		List<SystemUserInfo> sysList = perClient.getSystemUserInfo();
//		List<User> userList = perClient.queryUserInfoByRoleId("400058");
//		userList = null;
//		userList = perClient.queryUserInfosByDepartId("300013");
//		perClient.modifyUserPassword("11111", "123456", "123456");
		
		
		
	}
	@Test
	public void testJarFile() { 
//	    // 项目中jar包所在物理路径  
//	    String jarName = "D:/CommonsData/git/PermissionSystem/PermissionClient/target/permission-client-1.4.6.jar";  
//	    JarFile jarFile;
//		try {
//			jarFile = new JarFile(jarName);
//			Enumeration<JarEntry> entrys = jarFile.entries();  
//		    while (entrys.hasMoreElements()) {  
//		        JarEntry jarEntry = entrys.nextElement();  
//		        System.out.println(jarEntry.getName());  
//		    }    
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	    
	}
}
