<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email</title>
</head>
<body>
<form id=signup action="signupcontroller" method="POST" accept-charset="UTF-8">
<label for=givenname>Username</label><br>
<input type="text" id=username name=username><br>

<label for=givenname>Password</label><br>
<input type="password" id=password name=password><br>

<input type=submit value="Sign Up"><br>
</form>
</body>
</html>