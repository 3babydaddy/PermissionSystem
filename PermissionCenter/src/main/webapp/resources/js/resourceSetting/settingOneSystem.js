var setting = {
	check: {
		enable: true,
		chkboxType: { "Y": "ps", "N": "s" }
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};

var treeType;
var treeValue;

function initOption(type, value) {
	treeType = type;
	treeValue = value;
}

function loadData(system,show) {
	
	$("#systemSpn").html(show);
	
	var data;
	
	if (!treeType) {
		data = {system : system};
	} else if (treeType == "role") {
		data = {system : system,role:treeValue};
	}
	
	//console.log(data);	
	$.ajax({
		url : "../common/treeinfo",
		type : "POST",
		data : data,
		success : function(data) {
			
			//console.log(data);			
			
			$.fn.zTree.init($("#recTree"), setting, data);
		}
	})
}

function getSelectIds() {
	
	var treeObj = $.fn.zTree.getZTreeObj("recTree");
	var nodes = treeObj.getCheckedNodes(true);
	
	var selectedIds = [];
	
	for (var i=0;i< nodes.length;i++) {
		//console.log(nodes[i].id);	
		selectedIds.push(nodes[i].id);
	}
	
	return selectedIds;
}
