package com.tf.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.tf.demo.web.shiro.ShiroFilerChainManager;

/**
 * 权限系统客户端
 * 
 * @author fzq
 *
 */
@Controller
public class IndexController {

//	@Resource(name="permissionClientServiceImpl")
//    private PermissionClientService permissionService;
//    

//    public void setPermissionService(PermissionClientService permissionService) {
//		this.permissionService = permissionService;
//	}

//	@RequestMapping("/")
//    public String index(Model model) {
//		Subject user = SecurityUtils.getSubject();
//		String username = (String)user.getPrincipal();
//        Set<String> permissions = permissionService.findPermissions(username);
//        List<ResourceInfo> menus = permissionService.findMenus(permissions);
//        model.addAttribute("menus", menus);
//        return "index";
//    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
    @RequestMapping("/reInit")
    public String reInit() {
    	return "welcome";
    }
    @RequestMapping("/ttt")
    public String ttt() {
    	return "welcome";
    }


}
