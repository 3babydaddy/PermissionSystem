<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/role/roleCreate.js?time=new Date()'/>"></script>
</head>
<body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="角色信息" style="width:95%">
		<div style="padding:10px 60px 20px 60px">
		<form id="sysFrm" modelAttribute="departmentInfo" method="post">
	    	<table cellpadding="10" style="width: 80%">
	    		<tr>
	    			<td>名称</td>
	    			<td >
	    				<input class="easyui-textbox" type="text" id="nameTxt"></input>
	    			</td>
	    			<td>所属系统</td>
	    			<td >
	    				<select class="easyui-combobox" id="roleSystemSel" style="width:120px;" data-options="editable:false">
							<c:forEach var="it" items="${systemList}">
                                <option value="${it.value}" ${it.showSel}>${it.showMsg}</option>
                            </c:forEach>
						</select>
	    			</td>
	    			<td>是否有效</td>
	    			<td >
	    				<select class="easyui-combobox" id="availSel" style="width:120px;" data-options="editable:false">
                            <option value="0" true>是</option>
                            <option value="1" >否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="6"></td>
	    		</tr>
	    	</table>
	    	</form>
	    </div>
	</div>
	<%@ include file="/WEB-INF/views/resourceSetting/settingOneSystem.jsp"%>
	
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="gobackBtn">后退</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn">提交</a>
	</div>
</body>
</html>