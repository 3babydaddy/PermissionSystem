<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/system/sysQuery.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="系统查询" style="width:100%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">系统ID：<input class="easyui-textbox" type="text" id="idTxt" ></input></td>
					<td align="right">系统名称：<input class="easyui-textbox" type="text" id="nameTxt" ></input></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="addBtn">新建</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtn">检索</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:20px 0;"></div>
	<table id="systemInfoTbl" style="width: 100%"></table>
	
</body>
</html>