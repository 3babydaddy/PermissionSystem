var editingId;

$(document).ready(function() {
	
	$('#recTable').treegrid({
		
		rownumbers: true,
		animate: true,
		collapsible: true,
		fitColumns: true,
	    idField:'id',
	    treeField:'resourcename',
	    onContextMenu: onContextMenu,
	    columns:[[
	         {title:'资源信息',field:'resourcename',width:60,editor:'text'},
			{field:'type',title:'资源类型',width:20,align:'right',editor:'text'},
			{field:'permission',title:'权限',width:60,editor:'text'},
			{field:'resourceurl',title:'URL',width:80,editor:'text'},
			{field:'icon',title:'图片名称',width:40,editor:'text'},
			{field:'sortIndx',title:'排序',width:20,editor:'text'}
	    ]]
	});

	loadData();
	
	$("#systemSel").combobox({
		onSelect : function() {
			loadData();
		}
	})
})

function loadData() {
	
	var system = $("#systemSel").combobox('getValue');
	
	$.ajax({
		url : "rectreequery",
		type : "POST",
		data : {
			system : system
		},
		success : function(data) {
			
			//console.log(data);			
			
			$('#recTable').treegrid('loadData',data);
		}
	})
}

function onContextMenu(e,row){
	e.preventDefault();
	$(this).treegrid('select', row.id);
	$('#mm').menu('show',{
		left: e.pageX,
		top: e.pageY
	});
}

function append(){
	
	var node = $('#recTable').treegrid('getSelected');
	var system = $("#systemSel").combobox('getValue');
	var chlid =  $('#recTable').treegrid('getChildren',node.id);
	var levle = $('#recTable').treegrid('getLevel',node.id);
	
	$.ajax({
		url : "resourcecreate",
		type : "POST",
		data : {
			system:system,name:"新资源",order:chlid.length + 1,
			level:levle + 1,parent : node.id
		},
		success : function(data) {
			
			//console.log(data);			
			//console.log(node.id);			
			$('#recTable').treegrid('append',{
				parent: node.id,
				data: [{
					id: data.id,
					resourcename: data.resourcename,
					type: '',
					permission: '',
					resourceurl: ''
				}]
			})
			
			if (editingId != undefined){
				$('#recTable').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
			editingId = data.id;
			$('#recTable').treegrid('beginEdit', editingId);
		}
	})
}

function removeIt(){
	
	var node = $('#recTable').treegrid('getSelected');
	if (node) {
		$.messager.confirm('提示', '确认删除该项?', function (y) {
	        if (y) {
	        	$.ajax({
	    			url : "resourcedelete",
	    			type : "POST",
	    			data : {
	    				id:node.id
	    			},
	    			success : function(data) {
	    				$('#recTable').treegrid('remove', node.id);
	    				loadData();
	    			}
	    		})
	        }
		});
		
	}
}

function collapse(){
	var node = $('#recTable').treegrid('getSelected');
	if (node){
		$('#recTable').treegrid('collapse', node.id);
	}
}
function expand(){
	var node = $('#recTable').treegrid('getSelected');
	if (node){
		$('#recTable').treegrid('expand', node.id);
	}
}

function edit(){
	if (editingId != undefined){
		$('#recTable').treegrid('select', editingId);
		return;
	}
	var row = $('#recTable').treegrid('getSelected');
	if (row){
		$.messager.confirm('提示', '确认修改该项?', function (y) {
	        if (y) {
				editingId = row.id;
				$('#recTable').treegrid('beginEdit', editingId);
	        }
		});
	}
}

function save(){
	if (editingId != undefined){
		
		var t = $('#recTable');
		
		t.treegrid('endEdit', editingId);
		
		var data = t.treegrid('find', editingId);
		
		if(data.resourcename == '' || data.resourcename == null){
			alert("资源名称不能为空！");
			return;
		}
		if(data.type == '' || data.type == null){
			alert("资源类型不能为空！" );
			return;
		}
		if(data.permission == '' || data.permission == null){
			alert("资源权限不能为空！");
			return;
		}
		
		
		editingId = undefined;
		$.ajax({
			url : "resourcemodify",
			type : "POST",
			data : data,
			success : function(data) {
				if(data == 0){
					alert("保存成功");
					loadData();
				}else{
					alert("保存失败");
				}
			}
		})
	}
}

function cancel(){
	if (editingId != undefined){
		$('#recTable').treegrid('cancelEdit', editingId);
		editingId = undefined;
	}
}

