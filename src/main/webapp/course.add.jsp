<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 08.03.2017
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Добавить курс</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <h4>Новый курс</h4>
    <hr>
    <form action="<c:url value="${pageContext.request.contextPath}/courses/add"/>" method="post">
        <jsp:include page="course.details.jsp"></jsp:include>
        <hr>
        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
        <div class="form-group">
            <div class="col-xs-offset-2 col-xs-10">
                <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> Сохранить</button>
            </div>
        </div>
    </form>
</body>
</html>
