<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>Trash</h2>

	<c:if test="${param.status == 'sent'}">
		<div class="info">Message sent.</div>
		<br>
	</c:if>

	<c:if test="${param.status == 'deleted'}">
		<div class="info">Message deleted.</div>
		<br>
	</c:if>

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
					<th></th>
				</tr>
				<c:forEach var="m" items="${messages}">
					<tr>
						<td><c:out value="${m.sender}" />
						</td>
						<td><a href="<c:url value="/secure/message?id=${m.id}" />"><c:out
									value="${m.subject}" /> </a></td>
						<td><a
							href="<c:url value="/secure/untrashmessage?id=${m.id}" />">Send
								to Inbox</a>
						</td>
						<td><a
							href="<c:url value="/secure/deletemessage?id=${m.id}" />">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</t:layout>