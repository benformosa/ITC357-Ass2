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

	<c:if test="${param.error == 'login'}">
		<div class="error">Please log in</div>
	</c:if>

	<jsp:doBody />

	<br>
	<br>
	<footer>
		<div class="links">
			<a href="<c:url value="/secure/inbox" />">Inbox</a> <a
				href="<c:url value="/secure/trash" />">Trash</a> <a
				href="<c:url value="/secure/users" />">Users</a> <a
				href="<c:url value="/secure/newmessage" />">New Message</a>
		</div>
		<div class="login">
			<a href="<c:url value="/logout" />">Log out
				${sessionScope.username}</a>
		</div>
	</footer>
</body>
</html>