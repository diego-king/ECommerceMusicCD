<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" href="/css/parsley.css">
<title>Login</title>
</head>
<body>
	<c:if test="${sessionScope.message != null}">
		<p class="alert alert-info" role="alert">
			<c:out value="${sessionScope.message}" />
			<c:remove var="message" scope="session" />
		</p>
	</c:if>
  <div class="container w-50">
  	<h2>Login</h2>
    <form method="POST" action="login" data-parsley-validate>
		  <div class="form-group">
		    <label for="username">Username (email)</label>
		    <input type="email" class="form-control" id="userame" name="username" data-parsley-trigger="change" required>
		  </div>
		  <div class="form-group">
		    <label for="password">Password</label>
		    <input type="password" class="form-control" id="password" name="password" required>
		  </div>
		  <button type="submit" class="btn btn-primary">Submit</button>
		</form>
		<br>
		<p>New user? <a href="<%= request.getContextPath() %>/account">Sign up here</a>.
  </div>
  <script src="/js/jquery-3.2.1.min.js"></script>
  <script src="/js/bootstrap.min.js"></script>
	<script src="/js/parsley.min.js"></script>
</body>
</html>