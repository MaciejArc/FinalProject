<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 28.07.2021
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<form:form modelAttribute="faultOrder">

    <p>Opis usterki:</p><form:input path="description"/>
    <form:errors path="description"/>

    <p>Adres:</p><form:select path="address" items="${addresses}" itemLabel="fullAddress" itemValue="id"/>
    <form:errors path="address"/>
    <button type="submit">Wyślij</button>
</form:form>

</body>
</html>
