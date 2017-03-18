<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 11.03.2017
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" name="course_id" value="${course_id}">
<div class="form-group">
    <label class="col-md-2" for="lesson-orderNum"><b>Номер урока:</b></label>
    <div class="col-md-10">
        <input type="number"
               class="form-control"
               id="lesson-orderNum"
               name="orderNum"
               min="0"
               max="9999"
               <c:if test="${not empty lesson}">value="${lesson.orderNum}"</c:if>>
    </div>
</div>

<div class="form-group">
    <label class="col-md-2" for="lesson-theme"><b>Тема урока:</b></label>
    <div class="col-md-10">
        <input type="text"
               class="form-control"
               id="lesson-theme"
               name="theme"
               maxlength="100"
               minlength="2"
               <c:if test="${not empty lesson}">value="${lesson.theme}"</c:if>>
    </div>
</div>

<div class="form-group">
    <label class="col-md-2" for="lesson-duration"><b>Продолжительность урока:</b></label>
    <div class="col-md-10">
        <input type="number"
               step="0.5"
               class="form-control"
               id="lesson-duration"
               name="duration"
               min="0"
               max="9999"
               <c:if test="${not empty lesson}">value="${lesson.duration}"</c:if>>
    </div>
</div>
<div class="form-group">
    <label for="lesson-content"><b>Содержание урока:</b></label>
    <textarea id="lesson-content"
              class="form-control"
              name="content">
        <c:if test="${not empty lesson}">${lesson.content}</c:if>
    </textarea>
</div>