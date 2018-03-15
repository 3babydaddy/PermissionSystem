<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/log/logQuery.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="查询条件" style="width:100%">
		<div style="padding:0px 30px 10px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">系统：</td>
					<td align="right">
						<select class="easyui-combobox" id="logSystem" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<c:forEach var="systemInfo" items="${systemInfoList}">
									<option value="${systemInfo.id}" >${systemInfo.name}</option>
								</c:forEach>
						</select>
					</td>
					<td align="right">操作人：</td>
					<td align="right"><input class="easyui-textbox" type="text" id="operationUser" ></input></td>
					<td align="right">操作内容：</td>
					<td align="right"><input class="easyui-textbox" type="text" id="operationDetail" ></input></td>
				</tr>
				<tr>
					<td align="right">操作时间起：</td>
					<td align="right"><input class="easyui-datetimebox" type="text" id="operationTimeStart" data-options="width:160"></input></td>
					<td align="right">操作时间止：</td>
					<td align="right"><input class="easyui-datetimebox" type="text" id="operationTimeEnd" data-options="width:160"></input></td>
					<td colspan="1">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtn">检索</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table id="systemInfoTbl" style="width: 100%"></table>
</body>
</html>