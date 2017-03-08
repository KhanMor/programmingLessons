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
        <div class="row">
            <div class="col-sm-1">
                <div class="btn-group-vertical">
                    <a href="/courses/add" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> </a>
                    <a class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> </a>
                    <a class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> </a>
                </div>
            </div>
            <div class="col-sm-11">
                <table class="table table-bordered table-hover cell-border display" id="usersTable">
                    <tr class="bg-info">
                        <th>Название</th>
                        <th>Продолжительность</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${courses}" var="course">
                        <tr data-id="${course.id}">
                            <td>${course.name}</td>
                            <td>${course.duration}</td>
                            <td>${course.author.email}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
