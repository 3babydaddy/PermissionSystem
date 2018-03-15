<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/index/welcome.js'/>"></script>
</head>
<body>
	<h2>Basic Project</h2>
	<div style="margin:20px 0;"></div>
	<div id="p" class="easyui-panel" title="使用框架" style="width:700px;height:200px;padding:10px;">
		<p style="font-size:14px">基础项目架构说明</p>
		<ul>
			<li>MVC框架使用 SPRING MVC</li>
			<li>前台显示使用 JQuery和 easyui</li>
			<li>持久化层使用 MYBATIS</li>
			<li>数据库使用 MYSQL</li>
			<li>项目构建管理使用 MAVEN</li>
		</ul>
	</div>
	
	<table id="projectInfoTbl" style="width: 700px"></table>

</body>
</html>