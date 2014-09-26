<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Users</h2>
	<c:set var="users" scope="session" value="${requestScope.users}" />
	<c:choose>
		<c:when test="${empty users}">
No users found.
</c:when>
		<c:otherwise>
			<table>
				<tr>
					<th>Name</th>
					<th>Username</th>
					<th></th>
				</tr>
				<c:forEach var="u" items="${users}">
					<tr>
						<td><c:out value="${u.name}" />
						</td>
						<td><a
							href="<c:url value="/secure/user?user=${u.username}" />"><c:out
									value="${u.username}" />
						</a></td>
						<td><a
							href="<c:url value="/secure/newmessage?to=${u.username}" />">Send
								Message</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</t:layout>