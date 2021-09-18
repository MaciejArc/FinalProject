<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 19.07.2021
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style> td, th { border: 1px solid black; } </style>
</head>
<body>
<%@ include file="/admin/header.jsp" %>
<h4>Lista firm:</h4>
<table>

    <thead>
    <tr>
        <th>Nazwa</th><th>Nip</th><th>Numer telefonu</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${companys}" var="company">

        <tr>
            <td>${company.name}</td><td>${company.nip}</td><td>${company.phone}</td>
        </tr>

    </c:forEach>



    </tbody>

</table>
</body>
</html>
