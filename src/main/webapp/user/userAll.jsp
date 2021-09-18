<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 21.07.2021
  Time: 09:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style> td, th { border: 1px solid black; } </style>

</head>
<body>
<%@ include file="/admin/header.jsp" %>
<h4>Lista pracowników:</h4>


    <table>
    <thead>
    <tr>
    <th>Imię</th><th>Nazwisko</th><th>Numer telefonu</th><th>Firma</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.firstName}</td><td>${user.lastName}</td><td>${user.phone}</td><td>${user.company.name}</td>
        </tr>

    </c:forEach>
</body>
</html>
