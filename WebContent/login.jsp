<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Login</h2>

	<c:if test="${param.error == 'logout'}">
		<div class="info">You have logged out.</div>
	</c:if>

	<c:if test="${param.status == 'new'}">
		<div class="info">Thank you for signing up, please log in.</div>
	</c:if>

	<form id="login" action="login" method="POST" accept-charset="UTF-8">

		<c:choose>
			<c:when test="${!empty requestScope.loginFailed}">
				<div class="error">Incorrect username or password. Please try
					again</div>
			</c:when>
			<c:when test="${requestScope.emptyAttribute == 'username'}">
				<div class="error">Please enter a username</div>
			</c:when>
			<c:when test="${requestScope.emptyAttribute == 'password'}">
				<div class="error">Please enter a password</div>
			</c:when>
		</c:choose>
		<label for="username">Username</label><br> <input type="text"
			id="username" name="username"><br> <label for="password">Password</label><br>
		<input type="password" id="password" name="password"><br>
		<input type="submit" value="Login"><br>
	</form>
	<a href="signup">Sign Up</a>
</t:layout>