<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<html>
<head>
	<title>权限系统</title>
	<script type="text/javascript" src="<c:url value='/resources/js/logs/systemlogs.js?time=new Date()'/>"></script>
</head>
<body>
	<div class="easyui-panel" title="查询条件" style="width:100%">
		<div style="padding:0px 30px 20px 20px">
			<table cellspacing="15px">
				<tr>
					<td align="right">工作模块：
						<select class="easyui-combobox" id="logModule" style="width:175px;" data-options="editable:false">
								<option value="" >--请选择--</option>
								<option value="0" >用户管理</option>
								<option value="1" >角色管理</option>
								<option value="3" >部门管理</option>
								<option value="4" >系统管理</option>
								<option value="5" >资源管理</option>
						</select>
					</td>
					<td align="right">操作人：<input class="easyui-textbox" type="text" id="operationUser" ></input></td>
					<td align="right">操作内容：<input class="easyui-textbox" type="text" id="operationDetail" ></input></td>
					<td align="right">操作时间起：<input class="easyui-datetimebox" type="text" id="operationTimeStart" data-options="width:160"></input></td>
					<td align="right">操作时间止：<input class="easyui-datetimebox" type="text" id="operationTimeEnd" data-options="width:160"></input></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="javascript:void(0)" class="easyui-linkbutton" id="searchBtn">检索</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin:20px 0;"></div>
	<table id="systemInfoTbl" style="width: 100%"></table>
<script type="text/javascript">
function myLoader(param, success, error) {  
    var that = $(this);  
    var opts = that.datagrid("options");  
    if (!opts.url) {  
        return false;  
    }  
  
    var cache = that.data().datagrid.cache;  
    //if (!cache) {  
    //	if (true) {
    	if(opts.url.indexOf("fresh=true")>0){
    	opts.url = opts.url.replace("fresh=true","");
        $.ajax({  
            type: opts.method,  
            url: opts.url,  
            data: param,  
            dataType: "json",  
            success: function (data) {  
                that.data().datagrid['cache'] = data;  
                success(bulidData(data));  
            },  
            error: function () {  
                error.apply(this, arguments);  
            }  
        });  
    } else {  
        success(bulidData(cache));  
    }  
  
    function bulidData(data) {  
        var temp = $.extend({}, data);  
        var tempRows = [];  
        var start = (param.page - 1) * parseInt(param.rows);  
        var end = start + parseInt(param.rows);  
        var rows = data.rows;  
        for (var i = start; i < end; i++) {  
            if (rows[i]) {  
                tempRows.push(rows[i]);  
            } else {  
                break;  
            }  
        }  
  
        temp.rows = tempRows;  
        return temp;  
    }  
}  
</script>	
</body>
</html>