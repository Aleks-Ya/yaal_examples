<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info</title>
</head>
<body>
<h1>User Info</h1>
<p>ID: ${requestScope.userInfoAttr.id}</p>
<p>Login: ${requestScope.userInfoAttr.login}</p>
<p>E-mail: ${requestScope.userInfoAttr.email}</p>
</body>
</html>
