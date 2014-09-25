<%@tag description="Email page template" pageEncoding="UTF-8"%>
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

	<jsp:doBody />

	<br>
	<br>
	<footer>
		<div class="links">
			<a href="<c:url value="/secure/main" />">Inbox</a> <a href="<c:url value="/secure/users" />">Users</a>
		</div>
		<div class="login">
			<a href="<c:url value="/logout" />">Log out
				${sessionScope.username}</a>
		</div>
	</footer>
</body>
</html>