<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 24.02.2017
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Домашняя</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <h3><c:out value="${greeting_message}"/>, я домашняя страничка!</h3>
</body>
</html>
