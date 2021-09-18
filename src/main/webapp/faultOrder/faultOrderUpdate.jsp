<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 31.07.2021
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/admin/header.jsp" %>
<form:form modelAttribute="faultOrder">

    <p>Opis usterki:</p><form:input path="description"/>
    <form:errors path="description"/>


    <p>Wykonawca:</p><form:select path="user" items="${users}" itemLabel="fullName" itemValue="id"/>
    <form:errors path="user"/>
    <p>Status</p><form:input path="status"/>
    <button type="submit">Wyślij</button>
</form:form>
</body>
</html>
