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
	<ul class="header2">
		<center>
			<span class="eventMate">EventMate</span><img
				src="${pageContent.request.contextPath}/EventMate/images/martini2.jpg" width="40px" height="50px">
		</center>
	</ul>

	<div class="divMain2" style="min-height: 100%;">
		<jsp:include page="Header.jsp" />

<%
System.out.println("In render");
eventStore lTweet = (eventStore)request.getAttribute("Event");
if (lTweet==null){
 %>
	<p class="blueFont">No event's found</p>
	<% 
}else{
%>



		<div class="randomEvent">
			<div class="eventPicture">
				<center>
					<img src="${pageContent.request.contextPath}/EventMate/images/beyonce.jpg" class=imgBorder width="636px"
						height="322px">
				</center>		
			</div>
			
			<br> <br> <span class="blueFont" style="margin-left:12%;"><%=lTweet.getEvent() %></span> <span
				class="blueFont2"><%=lTweet.getDatess() %></span> <br> <br>
			<span class="blackFont" style="margin-left:12%;"><%=lTweet.getDescription() %></span> <br> <span class="blueFont2" style="margin-left:12%;">Attending:
			</span> <span class="blackFont"><%= lTweet.getAttendee() %></span> <br>
			 <span class="blueFont2" style="margin-left:12%;">Venue: </span> <span class="blackFont"><%= lTweet.getVenue() %></span> <br>
			  <span class="blueFont2" style="margin-left:12%;">Location: </span> <span class="blackFont"><%= lTweet.getLocation() %></span>
			 <br> <span
			 
				class="blueFont2" style="margin-left:12%;">Event Requirements: </span> <span

				class="blackFont"><%=lTweet.getEventReq() %></span> <br>
				 <span
			 
				class="blueFont2" style="margin-left:12%;">Event Type: </span> <span

				class="blackFont"><%=lTweet.getCategory() %></span> <br>
	
	 <form action="${pageContent.request.contextPath}/EventMate/NotAttending2" method="post"> 
		
		<button  type="submit" value="<%=lTweet.getEvent() %>" name="cross" style="margin-top:2%;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/cross2.png" width="100px" height="100px" ></button>
		</form>
		 <form action="${pageContent.request.contextPath}/EventMate/Attending2" method="post"> 
		 	<button  type="submit" value="<%=lTweet.getEvent() %>" name="tick" style="margin-top:2%;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/tick2.png" width="100px" height="100px" ></button>
		</form>

		
	</div>
	<%
	}
	%>


	</div>




</body>
</html>
