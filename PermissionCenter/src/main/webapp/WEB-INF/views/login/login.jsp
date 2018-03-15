<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head id="Head1">
    <base href="<%=basePath%>">
    <title>权限系统 </title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/default/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/icon.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/jquery.easyui.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/index/public.js'/>"></script>
    
	<script type="text/javascript">
	if (top != self) {
		parent.location.reload();
	}
	</script>
	<style type="text/css">  
<!--  
.con_div{  
	width:400px;  
	height:20px;  
	text-align:center;  
	display:table-cell;  
	vertical-align:middle;  
	line-height:20px;
}  
-->  
</style>  
 
<body  >

    
    <div id="w" class="easyui-window" title="请先登录" data-options="modal:true,iconCls:'Lockgo',closable:false,minimizable:false,maximizable:false" style="width:400px;padding:20px 70px 20px 70px;">
          <form id="loginForm" name="loginForm" method="post" action="user/login" >
		<div class="con_div"><h3>权限系统</h3><span style="color:red;">${error}</span></div> 
        <div style="margin-bottom:10px">
            <input class="easyui-textbox" type="text" id="name" name="name" style="width:100%;height:30px;padding:12px" data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" id="password" type="Password" name="password"   style="width:100%;height:30px;padding:12px" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
        </div>
       
        <div>
            <!-- a href="javascript:;" onclick="addOrUpdateInfo();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:2px 0px;width:100%;">
                <span style="font-size:14px;">登录</span>
            </a -->
            <span class="btn">
               　　　　　　　　　 <input type="submit" name="loginbth" value="登录"  />   
            </span>
        </div>
         <div class="con_div"><span style="font-size:19px;">©</span>permission</div> 
         </form>	   
    </div>

</body>
</html>