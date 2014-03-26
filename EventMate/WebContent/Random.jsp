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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<title>Event-Mate</title>
</head>
<body>
	<jsp:include page="Searchbar.jsp" />

	<div class="divMain2" style="min-height: 100%;">
		<jsp:include page="Header.jsp" />

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
if(!iterator.hasNext())
{ %>
	<center>
	<p class="blueFont" style="margin-top:30%;">No event's found</p>
	</center>
<%
}


iterator = lTweet.iterator();     
while (iterator.hasNext()){
	eventStore ts = (eventStore)iterator.next();

	%>



		<div class="randomEvent">
			<div class="eventPicture">
				<center>
					<img src="${pageContent.request.contextPath}/EventMate/images/crowd.jpg" class=imgBorder width="636px"
						height="322px">
				</center>		
			</div>
		
		   <center>
			<br> <br> <span class="blueFontWithoutMarg" ><%=ts.getEvent() %> - </span> <span
				class="blueFont2"><%=ts.getDatess() %></span> <br> <br>
			<span class="blackFont" ><%=ts.getDescription() %></span> <br> <span class="blueFont2">Attending:
			</span> <span class="blackFont"><%= ts.getAttendee() %></span> <br>
			 <span class="blueFont2" >Venue: </span> <span class="blackFont"><%= ts.getVenue() %></span> <br>
			  <span class="blueFont2" >Location: </span> <span class="blackFont"><%= ts.getLocation() %></span>
			 <br> <span
			 
				class="blueFont2" >Event Requirements: </span> <span

				class="blackFont"><%=ts.getEventReq() %></span> <br>
				 <span
			 
				class="blueFont2" >Event Type: </span> <span

				class="blackFont"><%=ts.getCategory() %></span> <br><br><br>
				
				<%if(ts.getCorrectDistance() == false)
					{%>
					 <span
			 
				class="blueFont2" >This event is not within your distance preferences</span><br>
				<%} %>
				
	
	<% if(ts.getAttending() == false && ts.getNotAttending() == false && ts.getEventPassed() == false)
	{%>
	 <div style="width:50%;float:right;" > 
<form action="${pageContent.request.contextPath}/EventMate/NotAttending2" method="post"> 
<button type="submit" value="<%=ts.getEvent() %>" name="cross" style="float:left;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/cross2.png" width="100px" height="100px"></button>
</form> 
</div>
<div style="width:50%;float:left;">
<form action="${pageContent.request.contextPath}/EventMate/Attending2" method="post"> 
<button type="submit" value="<%=ts.getEvent() %>" name="tick" style="float:right;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/tick2.png" width="100px" height="100px"></button>
</form>
</div>
		<%
		}
		else if(ts.getAttending() == true)
		{%>
			<span class="blueFont2">You are attending this event</span>
		<%} 
		else if(ts.getNotAttending() == true)
		{%>
			<span class="blueFont2" >You are not attending this event</span>
		<%} 
	     else if(ts.getEventPassed() == true)
		 { %>
			<span class="blueFont2" >Event has passed</span>
		<%} %>
		</center>
	</div>
	<%
	}
	}
	%>
   

	</div>




</body>
</html>
