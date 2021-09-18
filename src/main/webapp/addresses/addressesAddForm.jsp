<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 30.07.2021
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<form:form modelAttribute="addresses">
   <table border="solid">
    <tr>
        <td colspan="2">Adres:</td>
    </tr>
    <tr>
        <td>Ulica:</td><td><form:input path="street"/><form:errors path="street"/> </td>
    </tr>
    <tr>
        <td>Numer domu:</td><td><form:input path="houseNumber"/><form:errors path="houseNumber"/> </td>

    </tr>
       <tr>
           <td>Miasto:</td><td><form:input path="city"/><form:errors path="city"/> </td>

       </tr>
       <tr>
           <td>Kod pocztowy:</td><td><form:input path="zipCode"/><form:errors path="zipCode"/> </td>

       </tr>
    <tr>
        <td>Typ mieszkania</td><td><form:input path="typeOfHouse"/><form:errors path="typeOfHouse"/> </td>

    </tr></table>
    <button type="submit">Wyślij</button>
</form:form>

</body>
</html>
