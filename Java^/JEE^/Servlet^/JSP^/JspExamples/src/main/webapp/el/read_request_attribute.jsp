<%@ page import="filter.PutAttributeToRequestFilter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read request attribute</title>
</head>
<body>
<p>Attribute name: <%=PutAttributeToRequestFilter.ATTR_NAME%>
</p>

<p>Attribute value: ${requestScope.puttedAttr}</p>
<p>Attribute value: ${requestScope[PutAttributeToRequestFilter.ATTR_NAME]}</p>
<p>Attribute value: ${requestScope["puttedAttr"]}</p>
</body>
</html>
