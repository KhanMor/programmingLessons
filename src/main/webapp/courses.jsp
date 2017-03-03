<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 03.03.2017
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <div class="container" id="usersTableContainer">
        <table class="table table-bordered table-responsive" id="usersTable">
            <tr class="bg-info">
                <th>Название</th>
                <th>Продолжительность</th>
            </tr>
            <c:forEach items="${courses}" var="course">
                <tr data-id="${course.id}">
                    <td>${course.name}</td>
                    <td>${course.duration}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
