<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*" %>
      <%@ page import="com.example.stores.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Codystar' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png" type="image/png">
<title>Event-Mate</title>
</head>
<body>
<ul class="header2">
<center>
	<span class="eventMate">Event-Mate</span><img src="images/martini2.jpg" width="40px" height="50px" >
</center>
</ul>

<div class="divMain2" style="min-height:100%;">
	<jsp:include page="Header.jsp" />
	<br><br><br>
	<span class="blueFont" style="margin-left:30%;">Your Messages:</span>
	<%
System.out.println("In render");
List<MessagerStore> messages = (List<MessagerStore>)request.getAttribute("Messsages");
if (messages==null){
 %>
	<p class="blueFont">No event's found</p>
	<% 
}else{
%>


<% 
Iterator<MessagerStore> iterator;


iterator = messages.iterator();     
while (iterator.hasNext()){
	MessagerStore ms = (MessagerStore)iterator.next();

	%>
	<div class="event">
	<img src="images/ic_arrow_circle_right.png" width="60px" height="60px" style="float:right;">
		<center>
			<span class="blueFont"><%= ms.getMessager() %></span>
			<br><br><br>
		</center>
	</div>
<%

}
}
%>
	

	
</div>
</body>
</html>