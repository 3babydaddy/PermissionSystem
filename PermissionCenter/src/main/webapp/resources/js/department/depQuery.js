var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	data: {
		simpleData: {
			enable: true,
			idKey:'id',
			pIdKey:'higherDepart'
		}
	},
	view: {
		fontCss: getFont,
		nameIsHTML: true
	}
};




$(document).ready(function() {
	
	loadData();

	$("#searchBtn").click (function() {
		loadData();
	})
	
	$("#editBtn").click (function() {
		editDepartment();
	})
	
	$("#addBtn").click(function (){
		var systemId = $("#systemTxt").combobox('getValue');
		window.location.href= 'departmentcreate?systemId='+systemId;
	})
})

function loadData() {
	var name = $("#nameTxt").val();
	var systemId = $("#systemTxt").combobox('getValue');
	$.ajax({
		url : "../department/departmentquery",
		type : "POST",
		data : {
			name : name,
			systemId : systemId 
		},
		success : function(data) {
			$.each(data,function(i,o){
				if(o.avail == '1'){
					o.font = {'text-decoration':'line-through'};
					o.name = o.name + '(已撤销)';
				}
			});
			var tree = $.fn.zTree.init($("#departmentTree"), setting, data);
			tree.expandAll(true);
		}
	})
}
function getFont(treeId, node) {
	return node.font ? node.font : {};
}


function editDepartment() {
	
     var treeObj=$.fn.zTree.getZTreeObj("departmentTree"),
     nodes=treeObj.getCheckedNodes(true),
     value="";
     for(var i=0;i<nodes.length;i++){
	     value+= nodes[i].id;
	     if(i < nodes.length-1){
	    	 value+= ","
	     }
     }
     if(value == ""){
    	 alert("请选择部门");
     }else{
    	 var systemId = $("#systemTxt").combobox('getValue');
    	 window.location.href= 'departmentmodify?depId=' + value+'&systemId='+systemId;
     }
//     if(confirm("确认删除 节点 -- " + treeNode.name + " 吗？"))
     
}
