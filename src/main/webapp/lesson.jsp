<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 11.03.2017
  Time: 0:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Урок</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <h3>${lesson.orderNum}. ${lesson.theme} - ${lesson.duration}</h3>
    <hr>
    <p>${lesson.content}</p>
</body>
</html>
