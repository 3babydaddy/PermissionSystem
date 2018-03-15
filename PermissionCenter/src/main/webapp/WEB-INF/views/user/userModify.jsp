<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/user/userModify.js?time=new Date()'/>"></script>
</head>
<body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="用户信息" style="width:95%">
		<div style="padding:10px 60px 20px 60px">
		<form id="sysFrm" modelAttribute="userInfo" method="post">
	    	<table cellpadding="10" style="width: 100%">
	    		<tr>
	    			<td align="left">用户登入名</td>
	    			<td align="left">
	    				<input class="easyui-textbox" type="text" id="nameTxt" value="${userInfo.username}" readonly="readonly"></input>
	    			</td>
	    			<td align="left">用户显示名</td>
	    			<td align="left">
	    				<input class="easyui-textbox" type="text" id="showNameTxt" value="${userInfo.showname}"></input>
	    			</td>
	    			<td align="left">是否有效</td>
	    			<td align="left">
	    				<select class="easyui-combobox" id="availSel" style="width:120px;" data-options="editable:false">
                            <option value="0" true>是</option>
                            <option value="1" >否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td align="left">所属部门</td>
	    			<td align="left">
	    				<input  type="text" id="higherDepart" value="" readonly value=""  onclick="showMenu(); return false;"></input>
							<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a></li>
							<div id="menuContent" class="menuContent" style="display:none; position: absolute;background-color: #fff;border:1px solid #95B8E7;">
								<ul id="parentDepartMent" class="ztree" style="margin-top:0; width:160px;"></ul>
							</div>
	    			</td>
	    			<td>有效系统</td>
	    			<td colspan="3">
	    				<select class="easyui-combobox" id="userSysSel" style="width:520px;" data-options="multiple:true,editable:false">
							<c:forEach var="it" items="${systemList}">
                                <option value="${it.value}" ${it.showSel}>${it.showMsg}</option>
                            </c:forEach>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td align="left">联系方式</td>
	    			<td align="left">
	    				<input class="easyui-textbox" type="text" id="telTxt" value="${userInfo.tel}" maxlength="11" data-options="validType:'length[11]'"/>
	    			</td>
	    		</tr>
	    	</table>
	    	</form>
	    	
	    </div>
	</div>
		<div class="easyui-panel" title="用户角色信息" style="width:95%">
			<div style="padding:10px 60px 20px 60px">
				<div style="margin:20px 0;"/>
		    	<ul id="sysRoleTree" class="ztree" ></ul>
	   		</div>
   		</div>
    	
    	 <div style="text-align:right;padding:20px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="gobackBtn">后退</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn">提交</a>
	    	&nbsp;&nbsp;&nbsp;&nbsp;
	    </div>
	
	<input type="hidden" id="departmentShowHid" value = "${userInfo.departmentShow}">
	<input type="hidden" id="availHid" value = "${userInfo.avail}">
    <input type="hidden" id="departmentHid" value = "${userInfo.department}">
    <input type="hidden" id="shownameHid" value = "${userInfo.showname}">
    <input type="hidden" id="usernameHid" value = "${userInfo.username}">
    <input type="hidden" id="idHid" value = "${userInfo.id}">
    <input type="hidden" id="availSystemsHid" value = "${availSystems}">
</body>
</html>