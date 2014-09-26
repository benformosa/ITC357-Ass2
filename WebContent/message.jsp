<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.github.benformosa.email.model.Message"
	import="com.github.benformosa.email.model.MessageDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<c:set var="message" scope="session" value="${requestScope.message}" />
	<c:choose>
		<c:when test="${empty message}">
  No message found.
</c:when>
		<c:otherwise>
			<strong class="from"> From <c:out value="${message.sender}" />
			</strong>
			<br>
			<i class="subject"> <c:out value="${message.subject}" /> </i>
			<pre class="body">
<c:out value="${message.body}" />
			</pre>
			<br>

			<c:url value="newmessage" var="reply">
				<c:param name="to" value="${message.sender}" />
				<c:param name="subject" value="RE: ${message.subject}" />
			</c:url>

			<a href="<c:url value="${reply}" />">Reply</a>
			<c:choose>
				<c:when test="${message.trash}">
					<a href="<c:url value="/secure/untrashmessage?id=${message.id}" />">Send
						to Inbox</a>
					<a href="<c:url value="/secure/deletemessage?id=${message.id}" />">Delete</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value="/secure/trashmessage?id=${message.id}" />">Trash</a>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</t:layout>