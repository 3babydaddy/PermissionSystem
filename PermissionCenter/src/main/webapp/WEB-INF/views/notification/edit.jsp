<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
	<title></title>
	<script type="text/javascript" src="<c:url value='/resources/js/notification/notification.js?time=new Date()'/>"></script>
	<link href="<c:url value='/resources/plugins/bootstrap-3.3.5/css/bootstrap.min.css'/>" rel="stylesheet">
	<link href="<c:url value='/resources/plugins/bootstrap-3.3.5/css/bootstrap-theme.min.css'/>" rel="stylesheet">
</head>
<body>
	<div class="panel panel-info">
	   <div class="panel-body" align="center" style="border-width:0px;">
	   		<form id="editForm" action="save" method="post">
	        	<input type="hidden" name="id" id="id"  value="${model.id}">
	            <table class="table table-bordered" cellpadding="2" border="0" cellspacing="0" style="width:100%;height:60%;" >
	               <tr>
	               		<td align="right">客户端系统:</td>
						<td align="left">
							<select class="easyui-combobox" id ="systemid" name="systemid" style="width:150px;" data-options="editable:false,required:true">
							  <option value="">请选择 </option>
	               			 <c:forEach items="${systemList}" var="it">
	               			 	<c:choose >
	               			 		<c:when test="${it.id eq model.systemid}">
	               			 			<option value="${it.id}" selected="selected">${it.name} </option>
	               			 		</c:when>
	               			 		<c:otherwise>
	               			 			<option value="${it.id}">${it.name} </option>
	               			 		</c:otherwise>
	               			 	</c:choose>
	               			 </c:forEach>
	               			</select>
						</td>
						<td align="right">系统ip:</td>
						<td align="left"><input class="easyui-textbox" type="text" id="systemip" name="systemip"  value="${model.systemip}" data-options="required:true"></td>
	               </tr>
	               <tr>
	               		<td align="right">系统端口:</td>
						<td><input class="easyui-textbox" type="text" id="systemport" name="systemport" value="${model.systemport}" ></td>
						<td align="right">工程名称:</td>
						<td><input class="easyui-textbox" type="text" id="notificationUrl" name="notificationUrl" value="${model.notificationUrl}" data-options="required:true" ></td>
	               </tr>
	               <tr>
	               		<td colspan="2" align="center"><div style="text-align:right;padding:5px" >
					      		<a href="javascript:void(0)" class="easyui-linkbutton" id="cancleBtn" style="width:100px;height:28px;">关闭</a></td>
					     <td colspan="2" align="center"><div style="text-align:left;padding:5px">
						    	<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn" style="width:100px;height:28px;">保存</a></td>
	               </tr>
	            </table>
	        </form>
	   </div>
    </div>		   
</body>
</html>