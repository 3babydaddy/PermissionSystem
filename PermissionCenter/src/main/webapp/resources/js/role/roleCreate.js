$(document).ready(function() {
	
	$("#gobackBtn").click( function(){
		
		window.history.go(-1);
	})
	
	$("#roleSystemSel").combobox({
		onSelect : function(record) {
			loadData(record.value,record.text);
		}
	});
	
	loadData( $("#roleSystemSel").combobox('getValue'), $("#roleSystemSel").combobox('getText'));
	
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
		var system = $("#roleSystemSel").combobox('getValue');
		var avail = $("#availSel").combobox('getValue');
		
		var selectedIds = getSelectIds();
		
		$.ajax({
			url : "rolecreate",
			type : "POST",
			data : {
				name : name,system:system,avail:avail,selectedIds:selectedIds.toString()
			},
			success : function(data) {
				
				if (data == 2) {
					alert("系统已存在为：["+name+"]的角色");
					return;
				}else if(data != 0){
					alert("新增角色失败，请联系管理员！");
					return;
				}
				alert("提交成功");
				
				window.location.href= 'query';
			}
		});
		
	})
	
})

function validate() {
	
	var name = $("#nameTxt").val();

	if (name == null || trim(name) == "") {
		return "名称";
	}
	
	return "";
}

function trim(input) {
	
	return input.replace(/(^\s*)|(\s*$)/g,'');
}
