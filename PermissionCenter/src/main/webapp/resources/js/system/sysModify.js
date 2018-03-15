$(document).ready(function() {
	
	$("#gobackBtn").click( function(){
		
		window.history.go(-1);
	})
	
	init();
	
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
		var description = $("#detailTxt").textbox('getValue');
		
		$.ajax({
			url : "systemmodify",
			type : "POST",
			data : {
				id : id,name : name,description:description
			},
			success : function(data) {
				
				if (data == 2) {
					alert("已存在为：["+name+"]的系统名称");
					return;
				}else if(data != 0){
					alert("修改系统失败，请联系管理员！");
					return;
				}
				
				alert("提交成功");
				
				window.location.href= 'query';
			}
		});
		
	})
	
})

function init() {
	
	$("#detailTxt").textbox('setValue', $("#descriptionHid").val());
	$("#nameTxt").val($("#nameHid").val());
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
