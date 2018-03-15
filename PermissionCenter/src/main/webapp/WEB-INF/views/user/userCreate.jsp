<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/ztree/zTreeStyle/zTreeStyle.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.core-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/ztree/jquery.ztree.excheck-3.5.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/user/userCreate.js?time=new Date()'/>"></script>
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
	    				<input class="easyui-textbox" type="text" id="nameTxt"></input>
	    			</td>
	    			<td align="left">用户显示名</td>
	    			<td align="left">
	    				<input class="easyui-textbox" type="text" id="showNameTxt"></input>
	    			</td>
	    			<td align="left">状态</td>
	    			<td align="left">
	    				<select class="easyui-combobox" id="availSel" style="width:120px;" data-options="editable:false">
                            <option value="0" true>启用</option>
                            <option value="1" >禁用</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td align="left">所属部门</td>
	    			<td align="left">
	    				<input type="hidden" id="departmentID" value = "">
	    				<input  type="text" id="higherDepart" value="" readonly value=""  onclick="showMenu(); return false;"></input>
							</li>
							<div id="menuContent" class="menuContent" style="display:none; position: absolute;background-color: #fff;border:1px solid #95B8E7;">
								<ul id="parentDepartMent" class="ztree" style="margin-top:0; width:160px;"></ul>
							</div>
	    			</td>
	    			<td align="left">有效系统</td>
	    			<td colspan="3" align="left">
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
	    				<input class="easyui-textbox" type="text" id="telTxt" maxlength="11" data-options="validType:'length[11]'"/>
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
</body>
</html>