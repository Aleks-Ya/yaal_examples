<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>JSTL</title>
</head>
<body>
<h1>Formatting (fmt tag)</h1>

<fmt:setLocale value="ru_Ru"/>
<p>
    <c:set var="price" value="${213.678}"/>
    <fmt:formatNumber type="currency"
                      groupingUsed="false"
                      value="${price}"
                      maxFractionDigits="2"
                      minFractionDigits="2"/>
</p>

<p>
    <jsp:useBean id="date" class="java.util.Date"/>
    <fmt:formatDate value="${date}"
                    type="both"
                    dateStyle="medium"
                    timeStyle="medium"/>
</p>
</body>
</html>