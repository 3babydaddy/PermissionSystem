<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/role/roleQuery.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="角色查询" style="width:100%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">角色名称：<input class="easyui-textbox" type="text" id="nameTxt" ></input></td>
					<td align="right">所属系统：
						<select class="easyui-combobox" id="systemTxt" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<c:forEach var="systemInfo" items="${systemInfoList}">
									<option value="${systemInfo.id}" >${systemInfo.name}</option>
								</c:forEach>
								
						</select>
					</td>
					<td align="right">状态：
							<select class="easyui-combobox" id="avail" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<option value="0" >启用</option>
								<option value="1" >禁用</option>
								
							</select>
					</td>
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
	<table id="roleInfoTbl" style="width: 100%"></table>
	
</body>
</html>