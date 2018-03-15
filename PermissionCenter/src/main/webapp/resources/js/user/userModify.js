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
	$("#departmentShowHid").attr("value", nodesValue);
	$("#departmentHid").attr("value", nodesId);
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
	initDepartMent();
	$("#gobackBtn").click( function(){
		
		window.history.go(-1);
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
		
		var id = $("#idHid").val();
		var name = $("#nameTxt").val();
		var showName = $("#showNameTxt").val();
		var tel = $("#telTxt").val();
		var avail = $("#availSel").combobox('getValue');
		var depr = $("#departmentHid").val();
		var system = $("#userSysSel").combobox('getValues');
		
		var selectedIds = getSelectIds();
		
		$.ajax({
			url : "usermodify",
			type : "POST",
			data : {
				id:id,name : name,showName:showName,tel:tel,avail:avail,
				depr:depr, system:system.toString(), selectedIds:selectedIds.toString()
			},
			success : function(data) {
				
				if (data == 2) {
					alert("系统已存在登录名为：["+name+"]的用户");
					return;
				}else if(data != 0){
					alert("修改用户失败，请联系管理员！");
					return;
				}
				
				alert("提交成功");
				
				window.location.href= 'query';
			}
		});
		
	})
	
})

function init() {
	
	$("#availSel").combobox("select", $("#availHid").val())
	$("#userDeprSel").combobox("select", $("#departmentHid").val())
	$("#userSysSel").combobox("setValues", $("#availSystemsHid").val().replace("[","").replace("]","").split(', '))
	
	$.ajax({
		url : "userRoleModifyInit",
		type : "POST",
		data:{user: $("#idHid").val()},
		success : function(data) {
			
			$.fn.zTree.init($("#sysRoleTree"), setting, data);
		}
	})
}

function initDepartMent() {
	
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
			var departmentHid = $("#departmentHid").val();
			var treeObj = $.fn.zTree.getZTreeObj("parentDepartMent");
			var nodes = treeObj.getNodeByParam('id', departmentHid);
			if(nodes != null){
				$("#higherDepart").attr("value", nodes.name);				
			}
		}
	})
}

function validate() {
	
	var name = $("#nameTxt").val();
	var showName = $("#showNameTxt").val();
	var sysIDs = $("#userSysSel").combobox('getValues');
	
	if (name == null || trim(name) == "") {
		return "用户登入名";
	}
	
	if (showName == null || trim(showName) == "") {
		return "用户显示名";
	}
	
	if(sysIDs == null ||  sysIDs == ""){
		return "有效系统";
	}
	
	return "";
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

function trim(input) {
	
	return input.replace(/(^\s*)|(\s*$)/g,'');
}
