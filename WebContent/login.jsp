<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
	<h2>Login</h2>
	<form id="login" action="logincontroller" method="POST"
		accept-charset="UTF-8">

		<%
		  String loginCreated = (String) request.getAttribute("logincreated");
		  String loginFailed = (String) request.getAttribute("loginfailed");
		  String emptyAttribute = (String) request.getAttribute("emptyattribute");

		  if (loginCreated != null) {
		%>
		<label for="username">Thank you for signing up.
			Please log in.</label><br>
		<%
		  }
		%>

		<%
		  if (loginFailed != null) {
		%>
		<label for="username" class="error">Incorrect username or
			password. Please try again</label><br>
		<%
		  } else if (emptyAttribute != null) {
		    if (emptyAttribute.equals("username")) {
		%>
		<label for="username" class="error">Please enter a username</label><br>
		<%
		  } else if (emptyAttribute.equals("password")) {
		%>
		<label for="password" class="error">Please enter a password</label><br>
		<%
		  }
		  }
		%>
		<label for="username">Username</label><br> <input type="text"
			id="username" name="username"><br> <label for="password">Password</label><br>
		<input type="password" id="password" name="password"><br>

		<input type="submit" value="Login"><br>
	</form>

	<a href="signup">Sign Up</a>

</body>
</html>