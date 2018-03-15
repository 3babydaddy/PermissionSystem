$(document).ready(function(){
	initData();
	$("#saveBtn").click (function() {
		saveRecord();
	});
	
	$("#searchBtn").click(function(){
		reloadData();
	});
	
	
	
	$("#cancleBtn").click(function(){
		closeWin();
	});
	
});



function initData(){
	var params = getQueryParams();
	$('#InfoTbl').datagrid({
		url : 'query',
		queryParams : params,
		title: '发件人配置列表',
		rownumbers:true,
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		pagination:true,
		toolbar:[{
					iconCls: 'icon-edit',
					text:'新增配置',
					handler: function(){
						addRecord();
					}
		        }
		],
		columns : [[  
		            {field : 'name'  ,title : '客户端系统',width : "20%", align:'center'} ,
		            {field : 'systemip'  ,title : '系统ip',width : "20%", align:'center'} ,
		            {field : 'systemport'  ,title : '系统端口',width : "20%", align:'center'} ,
		            {field : 'notificationUrl'  ,title : '工程名称',width : "20%", align:'center'},
		             {field : 'statusAndDo',title : '操作',width : "19%" ,align:'center',
		            	formatter:function(vlaue,row,index){
		    				var edit='<a href="javascript:void(0)" class="easyui-linkbutton" name="editBtn" onclick="editRow(\''+ row.id+'\')">编辑</a>';
		            		var del = '<a href="javascript:void(0)" class="easyui-linkbutton" name="editBtn" onclick="delFrom(\''+ row.id+'\',\''+row.name+'\')">删除</a>';
		            		return edit +"&nbsp;&nbsp;&nbsp;"+ del; 
		            	}	
		           }]],
		     onLoadSuccess : function(data) {
	      }
	});
}


/**
 * 新增信息
 */
function addRecord() {
	var url=ctx + '/notification/edit';
	openWin("添加", url,"50%","40%",function(){reloadData();});
}

function editRow(id){
	var url=ctx + '/notification/edit?id=' + id ;
	openWin("编辑", url,"50%","40%",function(){reloadData();});
}



function delFrom(id,name){
	$.messager.confirm("操作提示","您确定要删除 “"+name+"” 的配置吗？",function(data){
		if(data){
			$.ajax({
				url:"deleteNotification",
				type:"POST",
				data:{id:id},
				success:function(data){
					reloadData();
				}
			});	
		}
	});
}

/**
 * 封装查询参数
 * @returns {___anonymous1724_1725}
 */
function getQueryParams(){
	var params = {};
	var fields =$('#queryForm').serializeArray(); //自动序列化表单元素为JSON对象
	$.each( fields, function(i, field){
		params[field.name] = field.value; //设置查询参数
	}); 
	
	
	return params;
}


/**
 * 添加修改操作
 */
function saveRecord(){

	if($("#editForm").form('validate')){
		//开启遮罩
		var index = layer.load(1, {
		    shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		try{
			var url = ctx + '/notification/save';
			$.post(url,$("#editForm").serializeArray(),function(result){
				var json = JSON.parse(result);
				//关闭遮罩
				layer.close(index);
				if(json.status ==1){
					alert(json.message);
					closeWin();
				}
				else
					alert(json.message);
			});
		}catch(e){
			//关闭遮罩
			layer.close(index);
			alert("发生错误:"+e.message);
		}
	}
}

function reloadData() {
	var params = getQueryParams();
	$('#InfoTbl').datagrid('options').queryParams = params;
	$("#InfoTbl").datagrid('reload');
}