<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/email.css">
</head>
<body>
	<h1>Email</h1>

	Welcome
	<%=session.getAttribute("username")%>
	<br> You are signed in.

	<a href="logout">Logout</a>
</body>
</html>