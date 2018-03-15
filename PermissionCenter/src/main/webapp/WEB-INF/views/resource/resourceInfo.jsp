<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/resource/resourceInfo.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="资源管理" style="width:100%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">系统名称：
						<select class="easyui-combobox" id="systemSel" style="width:120px;" data-options="editable:false">
							<c:forEach var="it" items="${systemList}">
                                <option value="${it.value}" ${it.showSel}>${it.showMsg}</option>
                            </c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">取消</a>
	</div>
	
	<table id="recTable" style="width: 100%"></table>
	
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">新增</div>
		<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>
		<div class="menu-sep"></div>
		<div onclick="collapse()">收起</div>
		<div onclick="expand()">打开</div>
	</div>
	
</body>
</html>