<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.exedit-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/department/depQuery.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="部门查询" style="width:100%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">部门名称：<input class="easyui-textbox" type="text" id="nameTxt" ></input></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="addBtn">新建</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" id="editBtn">修改</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtn">检索</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="部门信息" style="width:95%">
			<div style="padding:10px 60px 20px 60px">
				<div style="margin:20px 0;"/>
		    	<ul id="departmentTree" class="ztree" ></ul>
	   		</div>
   	</div>
	
</body>
</html>