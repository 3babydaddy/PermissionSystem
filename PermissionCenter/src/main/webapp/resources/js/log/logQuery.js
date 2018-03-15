$(document).ready(function() {
	
	loadData();

	$("#searchBtn").click (function() {
		loadData();
	})
})

function loadData() {
	
	var params = getParams();
	$('#systemInfoTbl').datagrid({
		url : 'logQuery',
		queryParams : params,
		title: '日志列表',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		fitColumns: true,
		pagination:true,
		rownumbers:true, 
		nowrap:false,
		pageNumber:1,
		columns : [ [ 
		//  {field : 'id',title : '日志ID',width : 30}, 
		  {field : 'systemName',title : '所属系统',width : 150 } 
		, {field : 'type',title : '类型',width : 80,formatter:function(v,r,index){
			if("1" == v){
				return "新增";
			}else if("2" == v){
				return "查看";
			}else if("3" == v){
				return "修改";
			}else if("4" == v){
				return "删除";
			}else if("0" == v){
				return "登录";
			}else if("-1" == v){
				return "退出";
			}
		} } 
		, {field : 'description',title : '操作内容',width : 350 }
		, {field : 'createtime',title : '操作时间',width : 100 ,formatter:Common.TimeFormatter}
		, {field : 'ip',title : 'IP',width : 100 ,align:'center'}
		, {field : 'creator',title : '操作用户',width : 50 ,align:'center'}
          ] ],
      onLoadSuccess : function(data) {

      }
	});
}

function reloadData() {
	
	var params = getParams();
	$('#systemInfoTbl').datagrid('options').queryParams = params;
	
	 $("#systemInfoTbl").datagrid('load');
}

function getParams(){
	var logSystem = $("#logSystem").combobox('getValue');
	var operationUser = $("#operationUser").val();
	var operationDetail = $("#operationDetail").val();
	var operationTimeStart = $("#operationTimeStart").datetimebox('getValue');
	var operationTimeEnd = $("#operationTimeEnd").datetimebox('getValue');
	
	var params = {
			systemid:logSystem,creator:operationUser,description:operationDetail,
			operationTimeStart:operationTimeStart,operationTimeEnd:operationTimeEnd,r:new Date().getTime()
	}
	
	return params;
}


