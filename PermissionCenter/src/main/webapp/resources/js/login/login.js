function   keyLogin() { 
	if(event.keyCode=="13") { 
		addOrUpdateInfo();
	} 
} 
	
	
//function addOrUpdateInfo(){
//	var r = $('#loginForm').form('validate');
//	if(!r) {
//			return false;
//	}
//	var name=document.getElementById("name").value;
//	var password=document.getElementById("password").value;
//		if(name==null||name==""){
//			alert("请输入用户名");
//			return;
//		}
//		if(password==null||password==""){
//			alert("请输入密码");
//			return;
//		}
////		$('#loginForm').submit();
//		$.post("user/login?name="+name+"&password="+password,function(data){//,$("#loginForm").serializeArray()
//			alert(data.error);
//			if(data.error==''){
//					window.location.href = 'index';
//			}else{
//					alert("用户名或密码输入有误");
//			} 
//			
//		});
//}