<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邮件服务器设置</title>
	<script type="text/javascript" src="<c:url value='/resources/js/notification/notification.js?version=${jsversion}'/>"></script>
</head>
<body>
	<div class="easyui-layout" style="width:100%;height:100%;" >
		<div data-options="region:'north'" class="easyui-panel" title="任务查询" style="width:100%;height: 190px;" align="center">
			<form id="queryForm" action="" name="queryForm" class="easyui-form" method="post" >
				<table cellpadding="10" border="0" cellspacing="0">
					<colgroup>
						<col width="200"/>
						<col width="200"/>
						<col width="200"/>
						<col width="200"/>
						<col width="200"/>
					</colgroup>
					<tr>
						<td align="right">客户端系统:</td>
						<td align="left">
							<select class="easyui-combobox" id ="systemid" name="systemid" style="width:150px;" data-options="editable:false,required:true">
							  <option value="">请选择 </option>
	               			 <c:forEach items="${systemList }" var="it">
	               			 	<c:choose >
	               			 		<c:when test="${it.id eq model.systemid}">
	               			 			<option value="${it.id}" selected="selected">${it.name} </option>
	               			 		</c:when>
	               			 		<c:otherwise>
	               			 			<option value="${it.id }">${it.name} </option>
	               			 		</c:otherwise>
	               			 	</c:choose>
	               			 </c:forEach>
	               			</select>
						</td>
						<td align="right">系统ip:</td>
						<td align="left"><input class="easyui-textbox" type="text" id="systemip" name="systemip"   ></td>
						<td></td>
					</tr>
					<tr>
						<td align="right">系统端口:</td>
						<td><input class="easyui-textbox" type="text" id="systemport" name="systemport" ></td>
						<td align="right">工程名称:</td>
						<td><input class="easyui-textbox" type="text" id="notificationUrl" name="notificationUrl" ></td>
						<td><a href="javascript:void(0)"
						class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  id="InfoTbl" style="height: auto;" ></div>
	</div>
</body>
</html>