<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/resourceSetting/setting.js'/>"></script>
</head>
<body>
	<div style="margin:20px 0;"/>
	<div class="easyui-panel" title="资源管理" style="width:95%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">系统名称：
						<select class="easyui-combobox" id="systemSel" style="width:120px;" data-options="editable:false" disabled="disabled">
							<c:forEach var="it" items="${systemList}">
                                <option value="${it.value}" ${it.showSel}>${it.showMsg}</option>
                            </c:forEach>
						</select>
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" id="treeSaveBtn">保存</a>
					</td>
				</tr>
			</table>
		</div>
		
		<ul id="recTree" class="ztree" ></ul>
	</div>
</body>
</html>