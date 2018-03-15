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

function showMenu() {
	var higherDepart = $("#higherDepart");
	var higherDepartOffset = $("#higherDepart").offset();
	$("#menuContent").css({left:higherDepartOffset.left + "px", top:higherDepartOffset.top + higherDepart.outerHeight() + "px"}).slideDown("fast");

	$("#menuContent").css("zIndex",999);
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



var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

$(document).ready(function() {
	init();
	$("#gobackBtn").click( function(){
		
		window.history.go(-1);
	})
	
	$.ajax({
		url : "userRoleInit",
		type : "POST",
		success : function(data) {
			
			//console.log(data);			
			
			$.fn.zTree.init($("#sysRoleTree"), setting, data);
		}
	})
	
	$("#submitBtn").click(function (){
		
		var v = validate();
		if (v != "") {
			$.messager.alert({
				title:'警告',
				msg:'请输入' + v + "！"
			});
			
			return;
		}
		
		var name = $("#nameTxt").val();
		var showName = $("#showNameTxt").val();
		var tel = $("#telTxt").val();
		var avail = $("#availSel").combobox('getValue');
		var depr = $("#departmentID").val();
		var sys = $("#userSysSel").combobox('getValues');
		
		var system = sys.toString();
		var selectedIds = getSelectIds();
		
		
		$.ajax({
			url : "usercreate",
			type : "POST",
			data : {
				name : name,showName:showName,tel:tel,avail:avail,
				depr:depr,system:system, selectedIds:selectedIds.toString()
			},
			success : function(data) {
				
				if (data == 2) {
					alert("系统已存在登录名为：["+name+"]的用户");
					return;
				}else if(data != 0){
					alert("新增用户失败，请联系管理员！");
					return;
				}
				
				alert("提交成功");
				
				window.location.href= 'query';
			}
		});
		
	})
	
})
function init() {
	
	$.ajax({
		url : "../department/departmentquery",
		type : "POST",
		data : {
			name : '',
			avail:'0'
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
function validate() {
	
	var name = $("#nameTxt").val();
	var showName = $("#showNameTxt").val();
	var departmentID = $("#departmentID").val();
	var sysIDs = $("#userSysSel").combobox('getValues');
	
	if (name == null || trim(name) == "") {
		return "用户登入名";
	}
	
	if (showName == null || trim(showName) == "") {
		return "用户显示名";
	}
	if (departmentID == null || trim(departmentID) == "") {
		return "所属部门";
	}
	
	if(sysIDs == null || sysIDs == ""){
		return "有效系统";
	}
	
	return "";
}

function trim(input) {
	
	return input.replace(/(^\s*)|(\s*$)/g,'');
}

function getSelectIds() {
	
	var treeObj = $.fn.zTree.getZTreeObj("sysRoleTree");
	var nodes = treeObj.getCheckedNodes(true);
	
	var selectedIds = [];
	
	for (var i=0;i< nodes.length;i++) {
		selectedIds.push(nodes[i].id);
	}
	
	return selectedIds;
}
