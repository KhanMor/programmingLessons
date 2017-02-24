<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1-1/jquery.min.js" type="text/javascript"></script>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" type="text/javascript"></script>

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
