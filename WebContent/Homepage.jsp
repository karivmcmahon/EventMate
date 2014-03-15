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
<link rel="shortcut icon" href="/images/Red-Solo-Cup3.jpg" type="image/jpg">
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
	<span class="blueFont" style="margin-left:30%;">Popular Events Suggested For You:</span>
	
	
<!-- 		<img src="images/martini.jpg" width="60px" height="120px" style="float:left;margin-top:1%;" class="userimgBorder"> -->
<!-- 		<img src="images/cross2.png" width="60px" height="60px" style="float:right;margin-top:3%;"> -->
<!-- 		<img src="images/tick2.png" width="60px" height="60px" style="float:right;margin-top:3%;"> -->
<!-- 		<p class="blueFont">Beyonce Live - Tue 18th March - 9:00pm</p> -->
<!-- 		<span class="blackFont" style="margin-left:2%;">Beyonce's back on her Mrs Carter World Tour at Glasgow Hydro</span><br> -->
<!-- 		<span class="blueFont2" style="margin-left:2%;">Attendee amount: <span class="blackFont" >200</span></span><br> -->
<!-- 		<span class="blueFont2" style="margin-left:2%;">Event Requirements: <span class="blackFont" > Over 14's</span></span><br> -->
<!-- 		<span class="blueFont2" style="margin-left:2%;">Venue: <span class="blackFont" >Hydro</span></span><br> -->
<!-- 		<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" >Glasgow</span></span><br> -->
		
		<%
System.out.println("In render");
List<eventStore> lTweet = (List<eventStore>)request.getAttribute("Events");
if (lTweet==null){
 %>
	<p class="blueFont">No event's found</p>
	<% 
}else{
%>


<% 
Iterator<eventStore> iterator;


iterator = lTweet.iterator();     
while (iterator.hasNext()){
	eventStore ts = (eventStore)iterator.next();

	%>
	<div class="event">
		 <img src="images/martini.jpg" width="60px" height="120px" style="float:left;margin-top:1%;" class="userimgBorder"> 
		<img src="images/cross2.png" width="60px" height="60px" style="float:right;margin-top:3%;">
		<img src="images/tick2.png" width="60px" height="60px" style="float:right;margin-top:3%">
		<p class="blueFont"><%=ts.getEvent() %> - <%=ts.getDatess() %> </p>
		<span class="blackFont" style="margin-left:2%;"><%= ts.getDescription() %></span><br>
		<span class="blueFont2" style="margin-left:2%;">Attendee amount: <span class="blackFont" ><%= ts.getAttendee() %></span></span><br>
		<span class="blueFont2" style="margin-left:2%;">Event Requirements: <span class="blackFont" ><%= ts.getEventReq() %></span></span><br>
	    <span class="blueFont2" style="margin-left:2%;">Venue: <span class="blackFont" ><%= ts.getVenue() %></span></span><br>
     	<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" ><%= ts.getLocation() %></span></span><br>
	</div>
<%

}
}
%>
		
		
	
	
</div>




</body>
</html>