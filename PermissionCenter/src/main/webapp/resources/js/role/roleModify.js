$(document).ready(function() {
	
	init();
	
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
		
		var selectedIds = getSelectIds();
		
		var avail = $("#availSel").combobox('getValue');
		
		var system = $("#systemHid").val();
		
		$.ajax({
			url : "rolemodify",
			type : "POST",
			data : {
				id : id,name : name,system:system,avail:avail,selectedIds:selectedIds.toString()
			},
			success : function(data) {
				
				if (data == 2) {
					alert("系统已存在为：["+name+"]的角色");
					return;
				}else if(data != 0){
					alert("修改角色失败，请联系管理员！");
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
	initOption("role", $("#idHid").val());
	
	loadData( $("#systemHid").val(),  $("#systemNameHid").val());
}

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
