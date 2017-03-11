<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 11.03.2017
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Добавить урок</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <h4>Редактировать урок "${lesson.theme}" к курсу "${course_name}"</h4>
    <form action="${pageContext.request.contextPath}/lessons/edit" method="post">
        <c:set var="course" value="${course_id}" scope="request" />
        <c:set var="lesson" value="${lesson}" scope="request" />
        <input type="hidden" id="lesson-id" name="id" value="${lesson.id}">
        <jsp:include page="lesson.details.jsp"></jsp:include>
        <div class="form-group">
            <div class="col-xs-offset-2 col-xs-10">
                <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> Сохранить</button>
            </div>
        </div>
    </form>
</body>
</html>
