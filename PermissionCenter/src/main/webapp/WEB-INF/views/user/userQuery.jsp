<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/user/userQuery.js?time=new Date()'/>"></script>
<style type="text/css">
.mytextbox{
        position: relative;
    border: 1px solid #95B8E7;
    background-color: #fff;
    vertical-align: middle;
    display: inline-block;
    overflow: hidden;
    white-space: nowrap;
    margin: 0;
    padding: 0;
    -moz-border-radius: 5px 5px 5px 5px;
    -webkit-border-radius: 5px 5px 5px 5px;
    border-radius: 5px 5px 5px 5px;
    width: 171px; height: 22px;
    margin-left: 0px; margin-right: 0px; padding-top: 3px; padding-bottom: 3px; width: 163px;
}
</style>
</head>
<body>
	
	<div class="easyui-panel" title="用户查询" style="width:100%;">
		<div style="padding:0px 30px 20px 20px;">
			<table cellspacing="15px">
				<tr>
					<td align="right">登录名称：</td>
					<td align="right"><input class="easyui-textbox" type="text" id="usernameTxt" ></input></td>
					<td align="right">用户姓名：</td>
					<td align="right"><input class="easyui-textbox" type="text" id="shownameTxt" ></input></td>
					<td align="right">所属部门：</td>
					<td align="right">
							<input type="hidden" id="departmentID" value = "">
							<input class="mytextbox" type="text" id="higherDepart" value="" readonly value=""  onclick="showMenu(); return false;"></input>
							</li>
							<div id="menuContent" class="menuContent" style="display:none;position: absolute;background-color: #fff;border:1px solid #95B8E7;">
								<ul id="parentDepartMent" class="ztree" style="margin-top:0; width:160px;"></ul>
							</div>
					</td>
					
				</tr>
				<tr>
					<td align="right">所属系统：</td>
					<td align="right">
						<select class="easyui-combobox" id="systemTxt" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<c:forEach var="systemInfo" items="${systemInfoList}">
									<option value="${systemInfo.id}" >${systemInfo.name}</option>
								</c:forEach>
								
						</select>
					</td>
					<td align="right">状态：</td>
					<td align="right">
							<select class="easyui-combobox" id="avail" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<option value="0" >启用</option>
								<option value="1" >禁用</option>
								
							</select>
					</td>
					<td colspan="2" align="right">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="addBtn">新建</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtn">检索</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:20px 0;"></div>
	<table id="userInfoTbl" style="width: 100%"></table>
	
</body>
</html>