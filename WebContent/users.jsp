<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Users</h2>
	<c:set var="users" scope="session" value="${requestScope.users}" />
	<c:set var="contacted" scope="session"
		value="${requestScope.contacted}" />
	<c:set var="contacters" scope="session"
		value="${requestScope.contacters}" />
	<c:choose>
		<c:when test="${empty users}">
No users found.
</c:when>
		<c:otherwise>
			<table>
				<tr>
					<th>Name</th>
					<th>Username</th>
					<th>Sent Message</th>
					<th>Recieved Message</th>
					<th></th>
				</tr>
				<c:forEach var="u" items="${users}">
					<tr>
						<td><c:out value="${u.name}" />
						</td>
						<td><a
							href="<c:url value="/secure/user?user=${u.username}" />"><c:out
									value="${u.username}" /> </a></td>
						<td><c:forEach var="recipient" items="${contacted}">
								<c:if test="${recipient eq u.username}">
								Yes
							</c:if>
							</c:forEach>
						</td>
						<td><c:forEach var="sender" items="${contacters}">
								<c:if test="${sender eq u.username}">
								Yes
							</c:if>
							</c:forEach>
						</td>
						<td><a
							href="<c:url value="/secure/newmessage?to=${u.username}" />">Send
								message</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</t:layout>