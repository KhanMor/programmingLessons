<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="<c:url value="${pageContext.request.contextPath}/webjars/jquery/3.1.1-1/jquery.min.js"/>" type="text/javascript"></script>
<link href="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>" rel="stylesheet">
<script src="<c:url value="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"/>" type="text/javascript"></script>
<link href="<c:url value="${pageContext.request.contextPath}/css/common.css"/>" rel="stylesheet">
<script src="<c:url value="${pageContext.request.contextPath}/js/common.js"/>" type="text/javascript"></script>
