<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>forEach tag</title>
</head>
<body>
<h1> forEach tag </h1>

<p>From 1 to 3:</p>
<table>
    <tr>
        <c:forEach var="counter" begin="1" end="3">
            <td>${counter}</td>
        </c:forEach>
    </tr>
</table>
<p>Collection walk:</p>
<table>
    <tr>
        <c:forEach var="city" items="${requestScope.collectionAttr}">
            <td>${city}</td>
        </c:forEach>
    </tr>
</table>
</body>
</html>