<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Brewery Login</title>
    <script type="text/javascript" src="${contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/brewery-utils.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/login-style.css">

</head>
<body>
    <div class="login-page">
        <div class="login-title">
            <h1>Brewery Login</h1>
        </div>
        <form:form method="post" modelAttribute="loginUser" action="login">
            <form:input path="username" type="text" />
            <form:input path="password" type="password" />
            <input type="submit" name="login" value="login">
        </form:form>
    </div>
</body>
</html>
