<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Login</h2>
	Welcome, please enter your username and password to log in.<br>

	<c:choose>
		<c:when test="${param.error == 'login'}">
			<div class="error">Please log in</div>
		</c:when>
		<c:when test="${param.error == 'logout'}">
			<div class="info">You have logged out.</div>
		</c:when>
		<c:when test="${param.status == 'new'}">
			<div class="info">Thank you for signing up, please log in.</div>
		</c:when>
		<c:when test="${!empty requestScope.loginFailed}">
			<div class="error">Incorrect username or password, please try
				again.</div>
		</c:when>
		<c:when test="${requestScope.emptyAttribute == 'username'}">
			<div class="error">Please enter a username</div>
		</c:when>
		<c:when test="${requestScope.emptyAttribute == 'password'}">
			<div class="error">Please enter a password</div>
		</c:when>
		<c:otherwise>
			<br>
		</c:otherwise>
	</c:choose>

	<form id="login" action="login" method="POST" accept-charset="UTF-8">
		<label for="username">Username</label><br> <input type="text"
			id="username" name="username" value="${param.username}"><br>
		<label for="password">Password</label><br> <input type="password"
			id="password" name="password"><br> <input type="submit"
			value="Login"><br>
	</form>
	<a href="signup">Sign Up</a>
</t:layout>