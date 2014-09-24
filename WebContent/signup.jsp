<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email</title>
<link rel="stylesheet" type="text/css" href="static/css/email.css">
</head>
<body>
	<h1>Email</h1>
	<h2>Sign Up</h2>
	<form id="signup" action="signupcontroller" method="POST"
		accept-charset="UTF-8">

		<% String usernameExists = (String) request.getAttribute("usernameexists"); %>
		<% if(usernameExists != null) { %>
		<label for="username" class="error">Sorry, that username is
			already in use. Please choose another</label><br>
		<% } %>

		<label for="username">Username</label><br> <input type="text"
			id="username" name="username"><br> <label for="password">Password</label><br>
		<input type="password" id="password" name="password"><br>

		<input type="submit" value="Sign Up"><br>
	</form>
</body>
</html>