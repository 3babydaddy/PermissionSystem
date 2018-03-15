$(document).ready(function() {
	
	loadData();

	$("#searchBtn").click (function() {
		loadData();
	})
})

function loadData() {
	
	var logModule = $("#logModule").combobox('getValue');
	var operationUser = $("#operationUser").val();
	var operationDetail = $("#operationDetail").val();
	var operationTimeStart = $("#operationTimeStart").datetimebox('getValue');
	var operationTimeEnd = $("#operationTimeEnd").datetimebox('getValue');
	
	var params = {
			logModule:logModule,operationUser:operationUser,operationDetail:operationDetail,
			operationTimeStart:operationTimeStart,operationTimeEnd:operationTimeEnd,r:new Date().getTime()
	}
	
	$('#systemInfoTbl').datagrid({
		url : 'list'+'?fresh=true',
		queryParams : params,
		title: '日志列表',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		fitColumns: true,
		pagination:true,
		rownumbers:true, 
		loader: myLoader,
		nowrap:false,
		pageNumber:1,
		columns : [ [ 
		//  {field : 'id',title : '日志ID',width : 30}, 
		{field : 'operationTime',title : '操作时间',width : 50 } 
		, {field : 'operationDetail',title : '操作内容',width : 350 }
		, {field : 'operationUser',title : '操作人',width : 50 ,align:'center'}
          ] ],
      onLoadSuccess : function(data) {

      }
	});
}

function reloadData() {
	
	var logModule = $("#logModule").val();
	var operationUser = $("#operationUser").val();
	var operationDetail = $("#operationDetail").val();
	var operationTimeStart = $("#operationTimeStart").datetimebox('getValue');
	var operationTimeEnd = $("#operationTimeEnd").datetimebox('getValue');
	
	var params = {
			logModule:logModule,operationUser:operationUser,operationDetail:operationDetail,
			operationTimeStart:operationTimeStart,operationTimeEnd:operationTimeEnd
	}
	alert(1)
	$('#systemInfoTbl').datagrid('options').queryParams = params;
	
	 $("#systemInfoTbl").datagrid('load');
}

function editRow(id) {
	
	window.location.href= 'systemmodify?sysId=' + id;
}

function deleteRow(id) {
	
	$.ajax({
		url : "systemdelete",
		type : "POST",
		data : {
			id : id
		},
		success : function(data) {
			
			if (data != 0) {
				// TODO
				return;
			}
			
			alert("删除成功");
		}
	})
}

