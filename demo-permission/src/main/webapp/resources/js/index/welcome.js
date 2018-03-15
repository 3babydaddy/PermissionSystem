$(document).ready(function() {
	
	$('#projectInfoTbl').datagrid({
		title: '项目结构',
		fitColumns : true,
		striped : true,
		singleSelect : true,
		fitColumns: true,
		columns : [ [ {field : 'resource',title : '文件目录',width : 200
		}, {field : 'mean',title : '意义',width : 250 } ] ]
	});
	
	var data = {total:1,rows:[
	                          {resource : "src/main/webapp/WEB-INF/views", mean : "存放显示用JSP（按照业务分文件夹）"},
	                          {resource : "src/main/webapp/resources", mean : "存放前台页面使用资源（包含JS,CSS,IMAGES等）"},
	                          {resource : "src/main/java/com/shijie99/base", mean : "存放后台逻辑"},
	                          {resource : "src/main/java/com/shijie99/base/index/contorller", mean : "业务路由信息"},
	                          {resource : "src/main/java/com/shijie99/base/index/blogic", mean : "业务逻辑信息"},
	                          {resource : "src/main/java/com/shijie99/base/index/domain", mean : "业务BEAN"},
	                          {resource : "src/main/java/com/shijie99/base/index/persistence", mean : "业务持久化接口"},
	                          {resource : "src/main/java/com/shijie99/base/index/service", mean : "业务内共通服务"},
	                          {resource : "src/main/java/com/shijie99/base/common", mean : "系统级共通服务"},
	                          {resource : "src/main/resources", mean : "后台逻辑对应资源（包括系统properties、数据库处理文件）"}]};
	
	$('#projectInfoTbl').datagrid('loadData', data);
})
