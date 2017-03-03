<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="common.head.jsp"></jsp:include>
        <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
        <title>Авторизация</title>
    </head>
    <body>
        <div id="login-form-container">
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon" id="email-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input class="form-control" type="text" placeholder="Введите email" id="email-input" aria-describedby="email-addon" name="email">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon" id="password-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input class="form-control" type="password" placeholder="Введите пароль" id="password-input" aria-describedby="password-addon" name="password">
                    </div>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary btn-block"><span class="glyphicon glyphicon-log-in"></span> Войти</button>
            </form>
            <a href="/registration" class="">Регистрация</a>
            <div>
                <label>${error_message}</label>
            </div>
        </div>
    </body>
</html>