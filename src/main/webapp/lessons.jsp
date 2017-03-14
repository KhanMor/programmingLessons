<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 10.03.2017
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="common.head.jsp"></jsp:include>
    <script src="/js/lessons.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/lessons.css" rel="stylesheet">
    <c:choose>
        <c:when test="${not empty course}">
            <title>Уроки ${course.name}</title>
        </c:when>
        <c:otherwise>
            <title>Уроки</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
    <jsp:include page="mainmenu.jsp"></jsp:include>
    <c:if test="${not empty course}">
        <input type="hidden" id="course-id" name="id" value="${course.id}">
        <input type="hidden" id="course-name" name="name" value="${course.name}">
        <input type="hidden" id="course-duration" name="duration" value="${course.duration}">
        <div id="lessonsHeader">
            <h3>Содержание курса "${course.name}"</h3>
        </div>
    </c:if>

    <div class="container" id="lessonsContainer">
        <ul class="list-group" id="lessonsList">
            <c:forEach items="${lessons}" var="lesson">
                <li class="list-group-item" data-id="${lesson.id}">
                    <b><span class="lesson-name">${lesson.name}</span></b>
                    <div class="pull-right btn-group">
                        <sec:authorize access="hasRole('ROLE_admin') or hasRole('ROLE_author')">
                            <a href="${pageContext.request.contextPath}/lessons/edit?course_id=${course.id}&course_name=${course.name}&id=${lesson.id}"
                            class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> </a>
                            <button class="btn btn-default deleteLessonBtn"><span class="glyphicon glyphicon-remove"></span> </button>
                        </sec:authorize>
                        <a href="${pageContext.request.contextPath}/lessons/get/${lesson.id}"
                           class="btn btn-default"><span class="glyphicon glyphicon-circle-arrow-right"></span> </a>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <sec:authorize access="hasRole('ROLE_admin') or hasRole('ROLE_author')">
        <c:if test="${not empty course}">
            <div class="btn-group container" id="lessonsToolbar">
                <a id="addLessonBtn"
                   href="${pageContext.request.contextPath}/lessons/add?course_id=${course.id}&course_name=${course.name}"
                   class="btn btn-success">
                    <span class="glyphicon glyphicon-plus"></span> Добавить урок
                </a>
            </div>
        </c:if>

        <div id="lessonDeleteModal" class="modal fade" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Удалить?</h4>
                    </div>
                    <div class="modal-body">
                        Удалить урок <span id="deletingLessonName"></span>?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" id="removeLessonBtn">
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
