<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:layout>
	<h2>New Message</h2>
	<form id="newmessage" action="newmessagecontroller" method="POST"
		accept-charset="UTF-8">

		<c:choose>
			<c:when test="${requestScope.messageSent == 'false'}">
				<div class="error">Sending message failed.</div>
			</c:when>
			<c:when test="${requestScope.messageSent == 'nosuchuser'}">
				<div class="error">No user with that username exists.</div>
			</c:when>
			<c:when test="${requestScope.emptyAttribute == 'recipient'}">
				<div class="error">
					Please enter a recipient.<br>
				</div>
			</c:when>
			<c:when test="${requestScope.emptyAttribute == 'subject'}">
				<div class="error">
					Please enter a subject.<br>
				</div>
			</c:when>
		</c:choose>

		<label for="recipient">To:<br>required, separate usernames with semicolons (;)</label><br> <input type="text"
			id="recipient" name="recipient" value=${param.to}><br> <label
			for="subject">Subject: required</label><br> <input type="text"
			id="subject" name="subject" value="${param.subject}"><br>
		<label for="body">Body</label><br>
		<textarea id=body name=body form="newmessage"></textarea>
		<br> <input type="submit" value="Send"><br>
	</form>
</t:layout>