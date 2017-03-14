<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 11.03.2017
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Личный кабинет</title>
</head>
<body>
    <h4>Профиль</h4>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <div class="container">
        <form action="<c:url value="${pageContext.request.contextPath}/profile"/>" method="post">
            <input type="hidden" name="id" value="${user.id}">
            <div class="form-group row">
                <label class="col-xs-2" for="email-input">email-адрес</label>
                <div class="col-xs-10">
                    <input type="text" class="form-control" id="email-input" name="email" placeholder="email-адрес" value="${user.email}" disabled>
                </div>
            </div>
            <hr>
            <div class="form-group row">
                <label class="col-xs-2" for="firstname-input">Имя</label>
                <div class="col-xs-10">
                    <input type="text" class="form-control" id="firstname-input" name="firstname" placeholder="Введите имя" value="${user.firstName}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-xs-2" for="surname-input">Фамилия</label>
                <div class="col-xs-10">
                    <input type="text" class="form-control" id="surname-input" name="surname" placeholder="Введите фамилию" value="${user.surname}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-xs-2" for="patronymic-input">Отчество</label>
                <div class="col-xs-10">
                    <input type="text" class="form-control" id="patronymic-input" name="patronymic" placeholder="Введите отчество" value="${user.patronymic}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-xs-2" for="birthday-input">Дата рождения</label>
                <div class="col-xs-10">
                    <input type="date" class="form-control" id="birthday-input" name="birthday" required value="${user.birthday}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-xs-2" for="sex-input">Пол</label>
                <div class="col-xs-10">
                    <select class="form-control" id="sex-input" name="sex">
                        <option
                                <c:if test="${user.sex == M}">
                                    select="selected"
                                </c:if>
                                value="M">Мужской</option>
                        <option
                                <c:if test="${user.sex == F}">
                                    select="selected"
                                </c:if>
                                value="F">Женский</option>
                    </select>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
            <div class="form-group row">
                <button type="submit" class="btn btn-primary"> Сохранить изменения</button>
            </div>
        </form>
    </div>
</body>
</html>
