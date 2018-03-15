<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tf.base.common.utils.VersionUtil" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/default/easyui.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/icon.css'/>" />
<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/jquery.easyui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/easyui-lang-zh_CN.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/layer/layer.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/layer/extend/layer.ext.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/index/public.js'/>"></script>
<c:set var="jsversion" value="<%=VersionUtil.version%>" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script>
var ctx="${ctx}";
</script>	
