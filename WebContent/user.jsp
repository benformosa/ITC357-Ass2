<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<c:set var="user" scope="session" value="${requestScope.user}" />
	<h2><c:out value="${user.username}'s Profile" /></h2>
	
	<c:choose>
		<c:when test="${user.username == sessionScope.username}">

			<c:if test="${param.status == 'updated'}">
				<div class="info">Profile updated.</div>
			</c:if>

			<form id="user" action="user" method="POST" accept-charset="UTF-8">
				<label for="username">Username</label> <br> <input type="text"
					id="username" name="username" value=${sessionScope.username
					}
					readonly disabled> <br> <label for="Name">Name</label>
				<br> <input type="text" id="name" name="name" value=${user.name}>
				<br> <input type="submit" value="Update">

			</form>
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<th>Name</th>
					<th>Username</th>
					<th></th>
				</tr>
				<tr>
					<td><c:out value="${user.name}" />
					</td>
					<td><c:out value="${user.username}" /></td>
					<td><a
						href="<c:url value="/secure/newmessage?recipient=${user.username}" />">Send
							Message</a>
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</t:layout>