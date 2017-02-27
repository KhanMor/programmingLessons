<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 27.02.2017
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <div class="container">
        <div class="input-group">
            <select id = "rolesSelect" class="form-control">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}">${role.role}</option>
                </c:forEach>
            </select>
            <div class="input-group-btn">
                <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Добавить</button>
                <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> Редактировать</button>
                <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Удалить</button>
            </div>
        </div>
        <div class="form-group row">

        </div>
    </div>
</body>
</html>
