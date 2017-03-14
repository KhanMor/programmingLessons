<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 03.03.2017
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/js/courses.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/courses.css" rel="stylesheet">
    <title>Курсы</title>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <div id="coursesHeader">
        <h3>Каталог курсов</h3>
    </div>
    <div class="container" id="coursesContainer">
        <ul class="panel-group list-group" id="coursesList">
            <c:forEach items="${courses}" var="course">
                <li panel="panel panel-default list-group-item" data-id="${course.id}">
                    <div class="panel-heading">
                        <a href="#course${course.id}" data-toggle="collapse" data-parent="#coursesList" class="nounderline">
                            <h4 class="panel-title">
                                <div class="course-name">${course.name}</div>
                                <span class="caret pull-right"></span>
                            </h4>
                        </a>
                    </div>
                    <div id="course${course.id}" class="panel-collapse collapse">
                        <div class="panel-body">
                            <div class="col-form-label">Продолжительность:</div>
                            <div class="course-duration">
                                ${course.duration}
                            </div>
                            <div class="col-form-label">Автор:</div>
                            <div class="course-author-email">
                                ${course.author.email}
                            </div>
                            <sec:authorize access="hasRole('ROLE_admin') or hasRole('ROLE_author')">
                                <div class="btn-group btn-edit-group">
                                    <button class="btn btn-default editCourseBtn"><span class="glyphicon glyphicon-edit"></span> Изменить</button>
                                    <button class="btn btn-default deleteCourseBtn"><span class="glyphicon glyphicon-remove"></span> Удалить</button>
                                </div>
                            </sec:authorize>
                            <a class="btn btn-primary goInsideCourseBtn"
                               href="<c:url value="${pageContext.request.contextPath}/lessons?course_id=${course.id}"/>">
                                <span class="glyphicon glyphicon-arrow-right"></span> Просмотр
                            </a>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <sec:authorize access="hasRole('ROLE_admin') or hasRole('ROLE_author')">
        <div class="btn-group container" id="coursesToolbar">
            <a id="addCourseBtn" href="<c:url value="${pageContext.request.contextPath}/courses/add"/>" class="btn btn-success">
                <span class="glyphicon glyphicon-plus"></span> Добавить курс
            </a>
        </div>

        <div id="courseModal" class="modal fade" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Курс</h4>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="course.details.jsp"></jsp:include>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="saveCourseBtn">
                            <span class="glyphicon glyphicon-floppy-save"></span> Сохранить
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="courseDeleteModal" class="modal fade" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Удалить?</h4>
                    </div>
                    <div class="modal-body">
                        Удалить курс <span id="deletingCourseName"></span>?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" id="removeCourseBtn">
                            <span class="glyphicon glyphicon-remove-sign"></span> Удалить
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>
    </sec:authorize>
</body>
</html>
