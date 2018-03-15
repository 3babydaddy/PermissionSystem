<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Shiro</title>
    <script type="text/javascript">	
	 var _menus = ${menus};
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/index/default.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/default/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/icon.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/jquery.easyui.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/easyui-lang-zh_CN.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/index/index.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/index/public.js?'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/layer/layer.js'/>"></script>
</head>

<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="<c:url value='/resources/images/index/noscript.gif'/>" alt='抱歉，请开启脚本支持！' /> </div>
    </noscript>
	<div region="north" split="true" border="false" style="overflow: hidden; height: 30px; background: url(./resources/images/index/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> 
        <span style="float:right; padding-right:20px;" class="head">欢迎 ${showname} <a href="#" id="editpass">修改密码</a> <a href="logout" id="loginOut">安全退出</a></span> <span style="padding-left:10px; font-size: 16px; ">
        <img src="<c:url value='/resources/images/index/blocks.gif'/>" width="20" height="20" align="absmiddle" />demo系统</span> </div>

	<div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
      <div id="nav" class="easyui-accordion" fit="true" border="false"> 
    	<!--  导航内容 --> 
	  </div>
    </div>
    
	<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
      <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
	      <div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	          <h1 style="font-size:24px;">欢迎使用demo系统</h1>
	          <!-- <marquee direction="right" scrollamount="10">
	          <img alt="" src="./resources/images/index/fly.png" /></marquee> -->
	      </div>
	  </div>
   </div>

</body>
</html>