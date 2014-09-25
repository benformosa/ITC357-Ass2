<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/email.css" />">
</head>
<body>
	<h1>Email</h1>

	Welcome
	<%=session.getAttribute("username")%>
	<br>
	<br>

	<a href="<c:url value="/secure/messagescontroller" />">View Messages</a>
	<br>
  <br>

	<a href="<c:url value="/logout" />">Logout</a>
</body>
</html>