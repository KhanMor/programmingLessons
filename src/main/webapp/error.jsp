<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 23.02.2017
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Ошибка!</title>
</head>
<body>
    <h2>Произошла ошибка, простите :(</h2>
    <h4>Страница: <c:out value="${url}"/></h4>
    <h4>Ошибка: <c:out value="${exception}"/></h4>
</body>
</html>
