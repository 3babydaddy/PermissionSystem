$(document).ready(function() {
	
	loadData();

	$("#searchBtn").click (function() {
		reloadData();
	})
	
	$("#addBtn").click(function (){
		
		window.location.href= 'systemcreate';
	})
})

function loadData() {
	
	var id = $("#idTxt").val();
	var name = $("#nameTxt").val();
	
	var params = {
			id:id,	name:name
	}
	
	$('#systemInfoTbl').datagrid({
		url : 'systemquery',
		queryParams : params,
		title: '系统列表',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		fitColumns: true,
		pagination:true,
		rownumbers:true, 
		columns : [ [ {field : 'id',title : '系统ID',width : 200
		}, {field : 'name',title : '系统名称',width : 150 } 
		, {field : 'description',title : '描述',width : 250 }
		, {field : 'do',title : '操作',width : 180 ,align:'center',
			formatter:function(value,row,index){  
                var cb = '<a href="javascript:void(0)" class="easyui-linkbutton" name="editBtn" onclick="editRow(\''+ row.id + '\')">编辑</a>' ;
//                		+ '<a href="javascript:void(0)" class="easyui-linkbutton" name="deleteBtn" onclick="deleteRow(\''+ row.id + '\')">删除</a>';
                return cb;  
            }}
          ] ],
      onLoadSuccess : function(data) {

      }
	});
}

function reloadData() {
	
	var id = $("#idTxt").val();
	var name = $("#nameTxt").val();
	
	var params = {
			id:id,	name:name
	}
	
	$('#systemInfoTbl').datagrid('options').queryParams = params;
	
	 $("#systemInfoTbl").datagrid('reload');
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

