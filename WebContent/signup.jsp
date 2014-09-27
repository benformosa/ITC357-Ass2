<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Sign Up</h2>
	Please enter a username and password to create an account.<br>

	<c:choose>
		<c:when test="${!empty requestScope.usernameExists}">
			<div class="error">Sorry, that username is already in use.
				Please choose another.</div>
		</c:when>
		<c:when test="${requestScope.emptyAttribute == 'username'}">
			<div class="error">
				Please enter a username.<br>
			</div>
		</c:when>
		<c:when test="${requestScope.emptyAttribute == 'password'}">
			<div class="error">
				Please enter a password.<br>
			</div>
		</c:when>
		<c:otherwise>
			<br>
		</c:otherwise>
	</c:choose>

	<form id="signup" action="signup" method="POST" accept-charset="UTF-8">
		<label for="username">Username</label> <br> <input type="text"
			id="username" name="username" value="${param.username}"> <br>
		<label for="password">Password</label> <br> <input
			type="password" id="password" name="password"> <br> <input
			type="submit" value="Sign Up">
	</form>
	<a href="login">Login</a>
</t:layout>