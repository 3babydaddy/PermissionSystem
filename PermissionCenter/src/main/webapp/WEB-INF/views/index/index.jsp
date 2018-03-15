<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<html>
<head>
	<title>权限中心-权限系统</title>
	<script type="text/javascript">
		var hasPermissionModule   = new Array();
		var hasPermissionFunction = new Array();
		
		<shiro:hasPermission name="user:list">
		   hasPermissionFunction.push("1");
		</shiro:hasPermission>
		<shiro:hasPermission name="role:list">
	    	hasPermissionFunction.push("2");
		</shiro:hasPermission>
		<shiro:hasPermission name="department:list">
		    hasPermissionFunction.push("3");
		</shiro:hasPermission>
		<shiro:hasPermission name="system:list">
		    hasPermissionFunction.push("4");
		</shiro:hasPermission>
		<shiro:hasPermission name="resource:list">
		   hasPermissionFunction.push("5");
		</shiro:hasPermission>
		<shiro:hasPermission name="notification:list">
		   hasPermissionFunction.push("7");
		</shiro:hasPermission>
		<shiro:hasPermission name="logs:list">
		   hasPermissionFunction.push("6");
		</shiro:hasPermission>
		<shiro:hasPermission name="log:list">
		   hasPermissionFunction.push("8");
		</shiro:hasPermission>
	
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/index/default.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/default/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/icon.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/jquery.easyui.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/easyui-lang-zh_CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/index/index.js?r=<%=new Random().nextInt() %>'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/layer/layer.js'/>"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">

<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="<c:url value='/resources/images/index/noscript.gif'/>" alt='抱歉，请开启脚本支持！' /> </div>
    </noscript>
	<div region="north" split="true" border="false" style="overflow: hidden; height: 30px; background: url(./resources/images/index/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> 
        <span style="float:right; padding-right:20px;" class="head">欢迎:&nbsp; ${showname } &nbsp;<a href="#" id="editpass">修改密码</a> &nbsp; <a href="/PermissionCenter/logout" id="loginOut">安全退出</a></span> <span style="padding-left:10px; font-size: 16px; ">
        <img src="<c:url value='/resources/images/index/blocks.gif'/>" width="20" height="20" align="absmiddle" />权限中心</span> </div>

	<div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
      <div id="nav" class="easyui-accordion" fit="true" border="false"> 
    	<!--  导航内容 --> 
	  </div>
    </div>
    
	<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
      <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
	      <div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	          <h1 style="font-size:24px;">欢迎使用权限中心</h1>
	      </div>
	  </div>
   </div>
   <!--修改密码窗口-->
<div id="w"  style=" display:none" >
		 <form id="fm_password" method="post">
	    	    <input type="hidden" name="id" id="uuid">
		        <div align="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	          		<table cellpadding=3>
	          		    <tr>
	              			<td>登录用户名：</td>
	              			<td><input  id="login_name" name="login_name" value="${showname}"  disabled="disabled" /></td>
	            		</tr>
	            		<tr>
	              			<td>原密码：</td>
	              			<td><input type="password" id="oldpwd" name="oldpwd"  /></td>
	            		</tr>
	        			<tr>
	              			<td>新密码：</td>
	              			<td><input type="password" id="password1" name="password1"  /></td>
	            		</tr>
	            		<tr>
	              			<td>确认密码：</td>
	              			<td><input type="password" id="password2" name="password2" /></td>
	            		</tr>
	        			
	           			
	           		 </table>
	        	</div>
	    		<div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;"> 
		    		<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"  > 确定</a> 
		    		<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" >取消</a> 
	    		</div>
	     </form>
	 </div>
</body>
</html>