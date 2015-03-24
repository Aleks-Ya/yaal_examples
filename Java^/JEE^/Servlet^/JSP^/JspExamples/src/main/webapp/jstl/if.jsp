<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<html>
<head>
    <title>JSTL</title>
</head>
<body>
<h1>if, choose</h1>

<p>
    <c:if test="${3 < 4}">
        Три больше четырех
    </c:if>
</p>

<p>
    <c:set var="number" value="${33}"/>
    <c:choose>
        <c:when test="${number > 0}">
            Число ${number} положительное.
        </c:when>
        <c:when test="${number == 0}">
            Число ${number} равно нулю.
        </c:when>
        <c:otherwise>
            Число ${number} отрицательное.
        </c:otherwise>
    </c:choose>
</p>


</body>
</html>