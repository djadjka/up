<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Hello World Sample
    </title>
</head>

<body>
<h1>
    <c:out value="${12+56*2}" />
</h1>
<form action="vars" method="post">
    <input type="text" size="40" name="login" value="abc">
    <input type="password" size="40" name="password" value="abc">
    <button type=”submit”>Submit</button>
</form>
</body>
</html>