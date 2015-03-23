<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
    <title>JSTL</title>
</head>
<body>
<h1>JSTL</h1>
<c:if test="${6 < 12}">
    <p>Tag 'if' works!</p>
</c:if>
<c:if test="${1 > 2}">
    <p>Fuck, 'if' doesn't works!</p>
</c:if>
</body>
</html>