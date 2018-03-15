var departmentSetting = {
	check: {
		enable: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey:'id',
			pIdKey:'higherDepart'
		}
	},
	callback: {
//		beforeClick: beforeClick,
		onClick: onClick
	}
};

//function beforeClick(treeId, treeNode) {
//	var check = (treeNode && !treeNode.isParent);
//	if (!check) alert("只能选择城市...");
//	return check;
//}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("parentDepartMent"),
	nodes = zTree.getSelectedNodes(),
	nodesValue = "";
	nodesId = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		nodesValue += nodes[i].name + ",";
		nodesId += nodes[i].id + ",";
	}
	if (nodesValue.length > 0 ) {
		nodesValue = nodesValue.substring(0, nodesValue.length-1);		
		nodesId = nodesId.substring(0, nodesId.length-1);		
	}
	$("#higherDepart").attr("value", nodesValue);
	$("#departmentID").attr("value", nodesId);
}
function isIE(){
	if (window.navigator.userAgent.indexOf("MSIE")>=1) 
	return true; 
	else
	return false; 
	}
function showMenu() {
	var higherDepart = $("#higherDepart");
	var higherDepartOffset = $("#higherDepart").offset();
	if(isIE()){
		$("#menuContent").css({left:higherDepartOffset.left + "px", "top":(higherDepartOffset.top + higherDepart.outerHeight())/2 + "px"}).slideDown("fast");
	}else
		$("#menuContent").css({left:higherDepartOffset.left + "px", "top":higherDepartOffset.top + higherDepart.outerHeight() + "px"}).slideDown("fast");

	$("#menuContent").css("zIndex",9999999);
	$("#menuContent").css("position","absolute");
	
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}



$(document).ready(function() {
	init();
	loadData();

	$("#searchBtn").click (function() {
		reloadData();
	})
	
	$("#addBtn").click(function (){
		
		window.location.href= 'usercreate';
	})
})


function init() {
	
	$.ajax({
		url : "../department/departmentquery",
		type : "POST",
		data : {
			name : ''
		},
		success : function(data) {
	
			var tree = $.fn.zTree.init($("#parentDepartMent"), departmentSetting, data);
			tree.expandAll(true);
			var higherDepartHid = $("#higherDepartHid").val();
			var treeObj = $.fn.zTree.getZTreeObj("parentDepartMent");
			var nodes = treeObj.getNodeByParam('id', higherDepartHid);
			if(nodes != null){
				$("#higherDepart").attr("value", nodes.name);				
			}
		}
	})
}

function loadData() {
	
	var username = $("#usernameTxt").val();
	var showname = $("#shownameTxt").val();
	var department = $("#departmentID").val();
	var avail = $("#avail").combobox('getValue');
	var systemid = $("#systemTxt").combobox('getValue');
	var params = {
			username:username,	showname:showname ,	department:department ,avail:avail,systemid:systemid
	}
	
	$('#userInfoTbl').datagrid({
		url : 'userquery',
		queryParams : params,
		title: '用户列表',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		remoteSort: true,
		fitColumns: true,
		pagination:true,
		rownumbers:true, 
		columns : [ [ {field : 'id',title : '用户ID',width : 200
		}, {field : 'username',title : '用户登录名',width : 150 } 
		, {field : 'showname',title : '用户显示名',width : 150 }
		, {field : 'departmentShow',title : '所属部门',width : 230 }
		, {field : 'tel',title : '联系方式',width : 120 }
		, {field : 'systemName',title : '所属系统',width : 350 }
		, {field : 'avail',title : '状态',width : 100 ,
			formatter:function(value,row,index){  
				return value == '0' ? '启用':'停用';  
			}}
		, {field : 'do',title : '操作',width : 200 ,align:'center',
			formatter:function(value,row,index){  
                var cb = '<a href="javascript:void(0)" class="easyui-linkbutton" name="editBtn" onclick="editRow(\''+ row.id + '\')">编辑</a>' 
                		+'&nbsp;|&nbsp;' 
                		+ '<a href="javascript:void(0)" class="easyui-linkbutton" name="changeBtn" onclick="changeStatus(\''+ row.id + '\',\''+ row.avail + '\')">'
                		+ (row.avail == '0' ? '停用' : '启用')
                		+'</a>'
		                +'&nbsp;|&nbsp;' 
		                + '<a href="javascript:void(0)" class="easyui-linkbutton" name="resetBtn" onclick="resetPassword(\''+ row.id + '\')">'
		                + '重置密码'
		                +'</a>';
                return cb;  
            }}
          ] ],
      onLoadSuccess : function(data) {

      }
	});
}

function reloadData() {
	
	var username = $("#usernameTxt").val();
	var showname = $("#shownameTxt").val();
	var department = $("#departmentID").val();
	var avail = $("#avail").combobox('getValue');
	var systemid = $("#systemTxt").combobox('getValue');

	var params = {
			username:username,	showname:showname ,department:department ,avail:avail,systemid:systemid
	}
	
	$('#userInfoTbl').datagrid('options').queryParams = params;
	
	 $("#userInfoTbl").datagrid('reload');
}

function editRow(id) {
	
	window.location.href= 'usermodify?userId=' + id;
}

function changeStatus(id,avail) {
	$.messager.confirm('提示', '确认'+(avail == '0' ? '停用' : '启用')+'?', function (y) {
		if(y){
			$.ajax({
				url : "../user/changeStatus",
				type : "POST",
				data : {
					id : id,avail:avail
				},
				success : function(data) {
					if(data.status == '0'){
						alert("处理成功!");
						$('#userInfoTbl').datagrid('reload'); //设置好查询参数 reload 一下就可以了
					}else{
						alert("处理失败，请联系管理员!");
					}
				}
			});
		}
	});

}
function resetPassword(id) {
	$.messager.confirm('提示', '确认重置密码?', function (y) {
		if(y){
			$.ajax({
				url : "../user/reSetPassword",
				type : "POST",
				data : {
					id : id
				},
				success : function(data) {
					if(data.status == '0'){
						alert("密码重置成功！，初始密码为"+data.password);
						$('#userInfoTbl').datagrid('reload'); //设置好查询参数 reload 一下就可以了
					}else{
						alert("处理失败，请联系管理员!");
					}
					
					
				}
			});
		}
	});

	
}
