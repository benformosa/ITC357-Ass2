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
	<h2>Messages</h2>

	<%
	  Message[] messages = (Message[]) request.getAttribute("messages");
	  if (messages != null) {

	    if (messages.length == 0) {
	%>
	No messages found.
	<%
	  } else {
	%>
	<table>
	<tr><th>From</th><th>Subject</th></tr>
		<%
		  for (Message m : messages) {
		%>

		<!-- 	link direct to jsp, send the message as an attribute -->

		<tr>
			<td><%=m.sender%></td>
			<td><a href="<%=response.encodeURL("message?id="+m.id)%>"><%=m.subject%></a>
			</td>
		</tr>
		<%
		  }
		%>
	</table>
	<%
	  }
	  } else {
	%>
	No messages found.
	<%
	  }
	%>
	<br>
	<a href="<c:url value="/logout" />">Log out
		${sessionScope.username}</a>
</body>
</html>