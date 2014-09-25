<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email</title>
</head>
<body>

	<%
		session.setAttribute("username", null);
		session.invalidate();
		response.sendRedirect(request.getContextPath());
	%>

</body>
</html>