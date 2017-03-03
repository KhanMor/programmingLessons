<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="common.head.jsp"></jsp:include>
        <title>Регистрация</title>
    </head>
    <body>
        <div class="container">
            <h4>Регистрация</h4>
            <hr>
            <form method="post" action="${pageContext.request.contextPath}/registration">
                <jsp:include page="user.details.jsp"></jsp:include>
                <div class="form-group row">
                        <div class="offset-sm-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Регистрация</button>
                        </div>
                </div>
            </form>
        </div>
    </body>
</html>
