<%@ page import="filter.PutPojoAttrToRequestFilter" %>
<%@ page import="filter.PutPrimitiveAttrToRequestFilter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read request attribute</title>
</head>
<body>
<h1>Primitive attribute</h1>
<p>Attribute name: <%=PutPrimitiveAttrToRequestFilter.ATTR_NAME%>
</p>
<p>Attribute value: ${requestScope.primitiveAttr}</p>
<p>Attribute value: ${requestScope[PutPrimitiveAttrToRequestFilter.ATTR_NAME]}</p>
<p>Attribute value: ${requestScope["primitiveAttr"]}</p>

<h1>POJO attribute</h1>
<p>Attribute name: <%=PutPojoAttrToRequestFilter.ATTR_NAME%>
<p>Attribute value: ${requestScope["pojoAttr"].name}</p>
<p>Attribute value: ${requestScope.pojoAttr.age}</p>
<p>Attribute value: ${requestScope[PutPojoAttrToRequestFilter.ATTR_NAME].homeless}</p>
</body>
</html>
