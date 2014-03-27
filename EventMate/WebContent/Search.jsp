<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
      <%@ page import="com.example.stores.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContent.request.contextPath}/EventMate/css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
		<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Codystar' rel='stylesheet' type='text/css'>
		<link rel="shortcut icon" href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png" type="image/png">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
		
		<!-- Ajax script to refresh tweet timeline every 15 mins, fade out is fast to show refresh occuring -->
		<script>
		var auto_refresh = setInterval(
		function()
		{
		 $.ajaxSetup({ cache: false });
		$('#loaddiv').fadeOut('fast').load('${pageContext.request.contextPath}/EventMate/Event #loaddiv').fadeIn("slow");
		}, 900000);
		</script>
		
		<title>Event-Mate</title>
	</head>
<body>


<jsp:include page="Searchbar.jsp" />


<div class="divMain2" style="min-height:100%;">
	<jsp:include page="Header.jsp" />
	
	<br><br><br>
	<center>
		<span class="blueFont">Your search results:</span>
	</center>
	<div id="loaddiv">
	
		
<%
System.out.println("In render");
List<eventStore> lTweet = (List<eventStore>)request.getAttribute("Events");
if (lTweet==null)
{
%>
 	<center>
		<p class="blueFont" style="margin-top:30%;">No event's found in search</p>
	</center>
<% 
}
else
{
%>


<% 
Iterator<eventStore> iterator;
iterator = lTweet.iterator();     
if(!iterator.hasNext())
{ %>
	<center>
		<p class="blueFont" style="margin-top:30%;">No event's found in search</p>
	</center>

<%
}

iterator = lTweet.iterator();     
while (iterator.hasNext()){
	eventStore ts = (eventStore)iterator.next();

	%>
	<div class="event">
	  	<% if(ts.getAttending() == false && ts.getNotAttending() == false && ts.getEventPassed() == false)
		{%>
			 <form action="${pageContent.request.contextPath}/EventMate/NotAttending" method="post"> 
				<button  type="submit" value="<%=ts.getEvent() %>" name="cross" style="float:right;margin-top:3%;padding: 0;border: none;background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/cross2.png" width="60px" height="60px" ></button>
			</form>
			 <form action="${pageContent.request.contextPath}/EventMate/Attending" method="post"> 
				 	<button  type="submit" value="<%=ts.getEvent() %>" name="tick" style="float:right;margin-top:3%;padding: 0;border: none;background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/tick2.png" width="60px" height="60px" ></button>
			</form>
		
 	<% } else if(ts.getEventPassed() == true)
		{%>
			<span class="blueFont2" style="float:right;margin-top:6%;margin-right:1%;">This event has passed.</span>
		<%} 
		else if(ts.getAttending() == true)
		{%>
			<span class="blueFont2" style="float:right;margin-top:6%;margin-right:1%;">You are attending this event</span>
		<%} 
		else if(ts.getNotAttending() == true)
		{%>
			<span class="blueFont2" style="float:right;margin-top:6%;margin-right:1%;">You are not attending this event</span>
		<%} %>
		
		 <a href="${pageContent.request.contextPath}/EventMate/Event/<%=ts.getEvent()%>" class="blueFont3"><img src="${pageContent.request.contextPath}/EventMate/images/crowd.jpg" width="160px" height="160px" style="float:left;margin-top:1%;" class="userimgBorder"></a>
		
		
		<a href="${pageContent.request.contextPath}/EventMate/Event/<%=ts.getEvent()%>" class="blueFont3" style="margin-left:2%" ><%=ts.getEvent() %></a> <span class="blueFont3">- <%=ts.getDatess() %></span><br>
		<span class="blackFont" style="margin-left:2%;"><%= ts.getDescription() %></span><br>
		<span class="blueFont2" style="margin-left:2%;">Attendee amount: <span class="blackFont" ><%= ts.getAttendee() %></span></span><br>
		<span class="blueFont2" style="margin-left:2%;">Event Requirements: <span class="blackFont" ><%= ts.getEventReq() %></span></span><br>
	    <span class="blueFont2" style="margin-left:2%;">Venue: <span class="blackFont" ><%= ts.getVenue() %></span></span><br>
     	<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" ><%= ts.getLocation() %></span></span><br>
     	<span class="blueFont2" style="margin-left:2%;">Event type: <span class="blackFont" ><%= ts.getCategory() %></span></span><br>
		<%if(ts.getCorrectDistance() == false)
		{%>
			 <span class="blueFont2" style="margin-left:2%;">This event is not within your distance preferences</span>
		<%} %>
</div>	
	
<%
	
}
}
%>

	
<%
System.out.println("In render");
List<UserStore> friends = (List<UserStore>)request.getAttribute("Friends");
if (friends==null){
 %>
	 <center>
		<p class="blueFont" style="margin-top:33%;">No users were found in search</p>
	</center>
<% 
}
else
{
%>


<% 
Iterator<UserStore> iterator;


iterator = friends.iterator();     
if(!iterator.hasNext())
{ 
%>
	<center>
		<p class="blueFont" style="margin-top:33%;">No users were found in search</p>
	</center>
<%
}
while (iterator.hasNext()){
	UserStore us = (UserStore)iterator.next();

	%>
	<div class="event" style="margin-top:2%;margin-top:2%;">
			
			<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=us.getUsername()%>"><img src="<%= us.getPhoto() %>" width="80px" height="130px" style="float:left;margin-top:1%;" class="userimgBorder"></a>
		
		  <% if(us.getUserFriends() == true)
			{%>
				<form action="${pageContent.request.contextPath}/EventMate/Messages" method="get">
					<input type="hidden" name="name" value="<%= us.getName() %>">
					<button type="submit" value="<%= us.getUsername() %>" name="username" style="float:right;margin-top:2%;padding: 0;border: none;background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/ic_chat_simple.png"  width="60px" height="60px"></button>
				</form>
			<%} 
			else 
			{%>
			  <span class="blueFont2" style="margin-top:3%;float:right;">You are not friends with this person </span>
			<%}%>
		
			<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=us.getUsername()%>" class="blueFont3" style="margin-left:2%;"><%= us.getName() %></a><br>
			<span class="blackFont" style="margin-left:2%;"><%= us.getBio() %></span><br>
			<span class="blueFont2" style="margin-left:2%;">Age: <span class="blackFont" ><%= us.getAge() %></span></span><br>
			<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" ><%= us.getLocation() %></span></span><br>
			<span class="blueFont2" style="margin-left:2%;">Interests: <span class="blackFont" ><%=  us.getInterests() %></span></span><br>
			<span class="blueFont2" style="margin-left:2%;">Events wanting to attend: <span class="blackFont" >
			<% ArrayList<String> list = new ArrayList<String>(); 
			list = us.getEventList(); 
			if(list.isEmpty())
			{
			
			}
			else
			{
				for(int i= 0;i < list.size();i++)
				{%>
					<%= list.get(i) %>,
				<%
				}
			}%>
			</span>
			</span>
			<br>
		
		
	
</div>

<%

}
}

%>
		
	
	
</div>




</body>
</html>