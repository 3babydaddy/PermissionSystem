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
	$("#higherDepartHid").attr("value", nodesId);
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
	if(isIE())
		$("#menuContent").css({left:higherDepartOffset.left + "px", "top":(higherDepartOffset.top + higherDepart.outerHeight())/2 + "px"}).slideDown("fast");
	else
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




$(document).ready(function() {
	
	init();
	
	
	$("#gobackBtn").click( function(){
		
		//window.history.go(-1);
		var systemId = $("#systemId").val();
   	 	window.location.href= 'query?systemId='+systemId;
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
		var higherDepart = $("#higherDepartHid").val();
		var systemId = $("#systemId").val();
//		var selectedIds = getSelectIds();
//		
//		var system = $("#systemSel").combobox('getValue');
		
		$.ajax({
			url : "departmentcreate",
			type : "POST",
			data : {
				name : name,higherDepart:higherDepart,systemId:systemId/*,selectedIds:selectedIds.toString(),system : system*/
			},
			success : function(data) {
				
				if (data == 2) {
					alert("系统已存在为：["+name+"]的部门");
					return;
				}else if(data != 0){
					alert("新增部门失败，请联系管理员！");
					return;
				}
				
				alert("提交成功");
				
				//window.location.href= 'query';
		   	 	window.location.href= 'query?systemId='+systemId;
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
			systemId : $("#systemId").val(),
			avail:'0'
		},
		success : function(data) {
	
			var tree = $.fn.zTree.init($("#parentDepartMent"), departmentSetting, data);
			tree.expandAll(true);
			var id = $("#idHid").val();
			var treeObj = $.fn.zTree.getZTreeObj("parentDepartMent");
			var nodes = treeObj.getNodeByParam('id', id);
			if(nodes != null){
				$("#higherDepart").attr("value", nodes.name);				
			}
		}
	})
}
function validate() {
	
	var name = $("#nameTxt").val();
	var higherDepart = $("#higherDepartHid").val();

	if (name == null || trim(name) == "") {
		return "名称";
	}
//	if (higherDepart == null || trim(higherDepart) == "") {
//		return "上级部门";
//	}
	
	return "";
}

function trim(input) {
	
	return input.replace(/(^\s*)|(\s*$)/g,'');
}
