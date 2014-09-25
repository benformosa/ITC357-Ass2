<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"
	import="com.github.benformosa.email.model.MessageDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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

	  MessageDAO messageDAO = new MessageDAO(this.getServletConfig()
	      .getServletContext().getRealPath("/WEB-INF"));
	  Message message = messageDAO.getMessage(id);
	  if (message != null) {
	    if (message.recipient.equals(session.getAttribute("username"))) {
	%>
	<h3>
		From
		<%=message.sender%></h3>
	<blockquote><%=message.body%></blockquote>
	<%
	  } else {
	%>
	<div class="error">No such message.</div>
	<%
	  }
	  } else {
	%>
	<div class="error">No such message.</div>
	<%
	  }
	%>

	<br>
	<br>
	<footer>
		<div class="links">
			<a href="<c:url value="/secure/main" />">Inbox</a>
		</div>
		<div class="login">
			<a href="<c:url value="/logout" />">Log out
				${sessionScope.username}</a>
		</div>
	</footer>
</body>
</html>