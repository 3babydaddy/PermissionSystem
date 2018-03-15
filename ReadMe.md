# AJAX开启 #
    
1. permissionClient.login.needAjaxLogin 配置成1
2. 配置permissionClient.login.ajaxLogin 为ajax登录后台请求
3. 前台JS保证window.top.doAjaxLogin();方法调用正常。（建议打开一个dialog，点击确定的时候调用permissionClient.login.ajaxLogin）