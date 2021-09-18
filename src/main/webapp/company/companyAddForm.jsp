<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 19.07.2021
  Time: 21:39
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
<form:form modelAttribute="company">

   <p>Nazwa firmy:</p>
    <form:input path="name"/>
    <form:errors path="name"/>
    <p>Nip:</p>
    <form:input path="nip"/>
    <form:errors path="nip"/>
    <p>Numer telefonu:</p>
    <form:input path="phone"/>
    <form:errors path="phone"/>
    <button type="submit">Wyślij</button>


</form:form>
</body>
</html>
