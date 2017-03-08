<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 08.03.2017
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <title>Добавить курс</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <h4>Новый курс</h4>
    <form action="/courses/add" method="post">
        <div class="form-group row">
            <label class="col-xs-2" for="name-input">Наименование</label>
            <div class="col-xs-10">
                <input type="text" class="form-control" id="name-input" name="name">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-xs-2" for="duration-input">Продолжительность</label>
            <div class="col-xs-10">
                <input type="number" step="0.01" class="form-control" id="duration-input" name="duration">
            </div>
        </div>
        <div class="form-group row">
            <div class="col-xs-offset-2">
                <button class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Сохранить</button>
            </div>
        </div>
    </form>
</body>
</html>
