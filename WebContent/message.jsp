<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"%>
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

	<%
	  int id = Integer.parseInt(request.getParameter("id"));
	Message message = Message.getMessage(id);
	  if (message != null) {
	%>
	<h3>
		From
		<%=message.sender%></h3>
	<blockquote><%=message.body%></blockquote>
	<%
	  }
	%>


	<a href="<c:url value="/logout" />">Log out
		${sessionScope.username}</a>
</body>
</html>