<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Inbox</h2>

	<c:choose>
		<c:when test="${param.status == 'sent'}">
			<div class="info">Message sent.</div>
			<br>
		</c:when>
		<c:when test="${param.status == 'trashed'}">
			<div class="info">Message moved to trash.</div>
			<br>
		</c:when>
	</c:choose>

	<c:set var="messages" scope="session" value="${requestScope.messages}" />
	<c:choose>
		<c:when test="${empty messages}">
	No messages found.
</c:when>
		<c:otherwise>
			<table>
				<tr>
					<th>From</th>
					<th>Subject</th>
					<th></th>
				</tr>
				<c:forEach var="m" items="${messages}">
					<tr>
						<td><c:out value="${m.sender}" />
						</td>
						<td><a href="<c:url value="/secure/message?id=${m.id}" />"><c:out
									value="${m.subject}" /> </a></td>
						<td><a
							href="<c:url value="/secure/trashmessage?id=${m.id}" />">Send
								to Trash</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</t:layout>