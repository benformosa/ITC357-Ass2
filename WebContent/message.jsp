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
	<h3>
		From
		<c:out value="${message.sender}" /></h3>
	<blockquote><c:out value="${message.body}" /></blockquote>
</c:otherwise>
</c:choose>
</t:layout>