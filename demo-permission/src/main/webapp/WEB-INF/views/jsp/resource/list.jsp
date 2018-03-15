<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <style>
        #table th, #table td {
            font-size: 14px;
            padding : 8px;
        }

    </style>
</head>
<body>
资源管理<br>
<a href="${pageContext.request.contextPath}/resource/create">新增</a><br/>
<a href="${pageContext.request.contextPath}/resource/update">修改</a><br/>
</body>
</html>