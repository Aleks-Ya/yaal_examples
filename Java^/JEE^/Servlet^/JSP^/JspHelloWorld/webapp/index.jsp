<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>Hello, JSP-world!</title>
</head>
<body>
<h1>Hello, JSP-world!</h1>

<h1>3+4=${3+4}</h1>

<p>Server date and time: <%=new Date()%>
</p>

<%--<p>
    <c:if test="${4 > 3}">
        Four are more than three!
    </c:if>
</p>--%>
</body>
</html>
