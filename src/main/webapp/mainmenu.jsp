<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 24.02.2017
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <nav id="mainmenu" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#mainmenu-collapse" aria-expanded="false">
                    <span class="sr-only">Навигация</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="${pageContext.request.contextPath}/home" />">STC Khan Project</a>
            </div>

            <div class="collapse navbar-collapse" id="mainmenu-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="<c:url value="${pageContext.request.contextPath}/usersAdmin" />">
                            <span class="glyphicon glyphicon-user"></span> Пользователи
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="${pageContext.request.contextPath}/courses" />">
                            <span class="glyphicon glyphicon-book"></span> Курсы
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<c:url value="${pageContext.request.contextPath}/logout" />"><span class="glyphicon glyphicon-log-out"></span> ВЫХОД</a></li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
