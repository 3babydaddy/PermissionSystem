<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/system/sysModify.js?time=new Date()'/>"></script>
</head>
<body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="系统信息" style="width:95%">
		<div style="padding:10px 60px 20px 60px">
		<form id="sysFrm" modelAttribute="systemInfo" method="post">
	    	<table cellpadding="10" style="width: 80%">
	    		<tr>
	    			<td>名称</td>
	    			<td >
	    				<input class="easyui-textbox" type="text" id="nameTxt" value="${systemInfo.name}"></input>
	    			</td>
	    			</td>
	    			</td>
	    			</td>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="6"></td>
	    		</tr>
	    		<tr>
	    			<td>系统描述:</td>
	    			<td  colspan="5"><input id="detailTxt" class="easyui-textbox"
						data-options="multiline:true" style="width: 100%; height: 250px"></td>
	    		</tr>
	    	</table>
	    	</form>
		    <div style="text-align:right;padding:20px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" id="gobackBtn">后退</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn">提交</a>
		    	&nbsp;&nbsp;&nbsp;&nbsp;
		    </div>
	    </div>
	</div>
	
	<input type="hidden" id="descriptionHid" value = "${systemInfo.description}">
    <input type="hidden" id="nameHid" value = "${systemInfo.name}">
    <input type="hidden" id="idHid" value = "${systemInfo.id}">
</body>
</html>