<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Brewery Login</title>
    <script type="text/javascript" src="${contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/brewery-utils.js"></script>
    <script type="text/javascript" src="${contextPath}/js/main.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/login-style.css">

</head>
<body>
    <div class="login-page">
        <div class="login-title">
            <h1>Brewery Login</h1>
        </div>
        <div class="login-inputs form-group">
            <label for="username"><spring:message code="login.username"/></label>
            <input class="form-control" id="username" type="text"/>
        </div>
        <div class="login-inputs form-group">
            <label for="password"><spring:message code="login.password"/></label>
            <input class="form-control" id="password" type="password"/>
        </div>
        <div class="login-button">
            <button type="button" class="btn btn-info login-btn" onclick="functionality.login()"><spring:message code="login.button"/></button>
            <button type="button" class="btn btn-info" onclick="history.back()"><spring:message code="login.cancel"/></button>
        </div>
        <span id="login-error" class="login-error"><spring:message code="login.error"/></span>
    </div>
</body>
</html>
