<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/department/depCreate.js?time=new Date()'/>"></script>
</head>
<body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="部门信息" style="width:95%">
		<div style="padding:10px 60px 20px 60px">
		<form id="sysFrm" modelAttribute="departmentInfo" method="post">
	    	<table cellpadding="10" style="width: 80%">
	    		<tr>
	    			<td>名称</td>
	    			<td >
	    				<input class="easyui-textbox" type="text" id="nameTxt"></input>
	    			</td>
	    			<td>上级部门</td>
	    			<td >
	    				<td >
	    				<input  type="text" id="higherDepart" value="" readonly value=""  onclick="showMenu(); return false;"></input>
							</li>
							<div id="menuContent" class="menuContent" style="display:none; position: absolute;background-color: #fff;border:1px solid #95B8E7;">
								<ul id="parentDepartMent" class="ztree" style="margin-top:0; width:160px;"></ul>
							</div>
	    			</td>
	    			</td>
	    			</td>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="6"></td>
	    		</tr>
	    	</table>
	    		<input type="hidden" id="higherDepartHid" value = "${departmentInfo.higherDepart}">
	    	</form>
	    </div>
	</div>
	<%-- <%@ include file="/WEB-INF/views/resourceSetting/setting.jsp"%> --%>
	
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="gobackBtn">后退</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn">提交</a>
	</div>
</body>
</html>