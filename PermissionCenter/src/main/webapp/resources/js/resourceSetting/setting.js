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

var treeType;
var treeValue;

$(document).ready(function() {
	
	$("#systemSel").combobox({
		onSelect : function() {
			loadData();
		}
	});
	
	loadData();
	
	$("#treeSaveBtn").click(function() {
		
		var system = $("#systemSel").combobox('getValue');
		
		var selectedIds = getSelectIds();
		
		if (!treeType) {
			return;
		} else if (treeType == "depart") {
			data = {system : system,department:treeValue,selectedIds:selectedIds.toString() };
		}
		
		$.ajax({
			url : "../common/treesave",
			type : "POST",
			data : data,
			success : function(data) {
				
				//console.log(data);
				alert("保存成功");
			}
		})
		
	})
	
})

function initOption(type, value) {
	treeType = type;
	treeValue = value;
}

function loadData() {
	
	var system = $("#systemSel").combobox('getValue');
	
	var data;
	
	if (!treeType) {
		data = {system : system};
	} else if (treeType == "depart") {
		data = {system : system,department:treeValue};
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
