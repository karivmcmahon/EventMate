<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
  <%@ page import="java.util.*" %>
      <%@ page import="com.example.stores.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContent.request.contextPath}/EventMate/css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
		<link href='http://fonts.googleapis.com/css?family=Vibur'
			rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Glegoo'
			rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Codystar'
			rel='stylesheet' type='text/css'>
		<link rel="shortcut icon" href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png" type="image/png">
		<title>Event-Mate</title>
	</head>
<body>
	<jsp:include page="Searchbar.jsp" />

	<div class="divMain2" style="min-height: 100%;">
		<!-- Shows Header.jsp with icon -->
		<jsp:include page="Header.jsp" />

		<!-- Displays page is not found -->
		<center>
			<p class="blueFont" style="margin-top:30%;">Oop's something went wrong....we could not find the page you were looking for :(</p>
		</center>
	</div>




</body>
</html>
