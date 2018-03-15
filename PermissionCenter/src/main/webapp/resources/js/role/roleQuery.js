$(document).ready(function() {
	
	loadData();

	$("#searchBtn").click (function() {
		reloadData();
	})
	
	$("#addBtn").click(function (){
		
		window.location.href= 'rolecreate';
	})
})

function loadData() {
	
	var systemid = $("#systemTxt").combobox('getValue');
	var name = $("#nameTxt").val();
	var avail = $("#avail").combobox('getValue');
	
	var params = {
			systemid:systemid,	name:name,avail:avail
	}
	
	$('#roleInfoTbl').datagrid({
		url : 'rolequery',
		queryParams : params,
		title: '系统列表',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		fitColumns: true,
		pagination:true,
		rownumbers:true, 
		columns : [ [ {field : 'id',title : '角色ID',width : 200
		}, {field : 'name',title : '角色名称',width : 150 } 
		, {field : 'systemName',title : '所属系统',width : 250 }
		, {field : 'avail',title : '是否有效',width : 250,
			formatter:function(value,row,index){  
				return value == '0' ? '启用':'停用';  
			}
		}
		, {field : 'do',title : '操作',width : 180 ,align:'center',
			formatter:function(value,row,index){  
                var cb = '<a href="javascript:void(0)" class="easyui-linkbutton" name="editBtn" onclick="editRow(\''+ row.id + '\')">编辑</a>'
                +'&nbsp;|&nbsp;' 
        		+ '<a href="javascript:void(0)" class="easyui-linkbutton" name="changeBtn" onclick="changeStatus(\''+ row.id + '\',\''+ row.avail + '\')">'
        		+ (row.avail == '0' ? '停用' : '启用')
        		+'</a>';
                return cb;  
            }}
          ] ],
      onLoadSuccess : function(data) {

      }
	});
}

function reloadData() {
	
	var systemid = $("#systemTxt").combobox('getValue');
	var name = $("#nameTxt").val();
	var avail = $("#avail").combobox('getValue');
	var params = {
			systemid:systemid,	name:name,avail:avail
	}
	
	$('#roleInfoTbl').datagrid('options').queryParams = params;
	
	 $("#roleInfoTbl").datagrid('reload');
}

function editRow(id) {
	
	window.location.href= 'rolemodify?roleId=' + id;
}

function changeStatus(id,avail) {
	
	$.ajax({
		url : "../role/changeStatus",
		type : "POST",
		data : {
			id : id,avail:avail
		},
		success : function(data) {
			if(data.status == '0'){
				alert("处理成功!");
				$('#roleInfoTbl').datagrid('reload'); //设置好查询参数 reload 一下就可以了
			}else{
				alert("处理失败，请联系管理员!");
			}
			
			
		}
	});

}

