<%--
  Created by IntelliJ IDEA.
  User: Mordr
  Date: 09.03.2017
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<input type="hidden" id="course-id-input" name="id">
<div class="form-group">
    <label class="col-md-2" for="course-name-input">Наименование</label>
    <div class="col-md-10">
        <input type="text"
               class="form-control"
               id="course-name-input"
               name="name"
               placeholder="Введите название курса"
               maxlength="100"
               minlength="2">
    </div>
    <form:errors path="*"/>
</div>
<div class="form-group">
    <label class="col-md-2" for="course-duration-input">Продолжительность</label>
    <div class="col-md-10">
        <input type="number"
               step="0.5"
               class="form-control"
               id="course-duration-input"
               name="duration"
               placeholder="Введите продолжительность"
               min="0"
               max="9999">
    </div>
</div>
