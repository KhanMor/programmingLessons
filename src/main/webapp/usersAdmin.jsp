<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 23.02.2017
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <link href="/css/usersAdmin.css" rel="stylesheet">
    <script src="/js/usersAdmin.js" type="text/javascript"></script>
    <title>Администрирование пользователей</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <nav class="navbar navbar-form navbar-fixed-bottom">
        <div class="container well well-sm">
            <button type="button" id="addUserBtn" class="btn btn-primary navbar-btn"><span class="glyphicon glyphicon-plus"></span> Добавить</button>
            <button type="button" id="editUserBtn" class="btn btn-warning navbar-btn"><span class="glyphicon glyphicon-edit"></span> Редактировать</button>
            <button type="button" id="deleteUserBtn" class="btn btn-danger navbar-btn"><span class="glyphicon glyphicon-remove"></span> Удалить</button>
        </div>
    </nav>
    <div class="container" id="usersTableContainer">
        <table class="table table-bordered table-hover" id="usersTable">
            <tr class="bg-info">
                <th>email</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Отчество</th>
                <th>Дата рождения</th>
                <th>Пол</th>
                <th>Роли</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr data-id="${user.id}">
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.surname}</td>
                    <td>${user.patronymic}</td>
                    <td>${user.birthday}</td>
                    <td>${user.sex}</td>
                    <td>
                        <ul class = "list-group">
                        <c:forEach items="${user.userRoles}" var="userRole">
                           <li data-rolename = "${userRole.role.role}" class = "list-group-item">${userRole.role.role}</li>
                        </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div id="UserModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Пользователь</h4>
                </div>
                <div class="modal-body">
                    <div class="checkbox" id="password-change-flag-container">
                        <label><input type="checkbox" value="" id="password-change-flag">Изменить пароль</label>
                    </div>
                    <jsp:include page="user.details.jsp"></jsp:include>
                    <h4>Роли пользователя</h4>
                    <div id = "userRolesContainer">
                        <div class="form-group">
                            Роль пользователя: <label class="label-info" id="currentUserRole"></label>
                        </div>
                        <div class="btn-group">
                            <button class="btn btn-default" id="setAdminBtn"> Сделать администратором</button>
                            <button class="btn btn-default" id="setAuthorBtn"> Сделать автором</button>
                            <button class="btn btn-default" id="setStudentBtn"> Сделать студентом</button>
                            <button class="btn btn-default" id="setBlockBtn"> Заблокировать</button>
                        </div>
                    </div>
                    <div id = "error-response">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="saveUserBtn">Сохранить</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
