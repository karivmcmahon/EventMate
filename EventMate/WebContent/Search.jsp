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
<script>
 $(function () {
      
      $('.dd').on('click', function(event){
          event.stopPropagation();
          $('.wrapper-dropdown-3').toggleClass('active');
      });
      
      
      $(document).click(function () {
      // all dropdowns
      $('.wrapper-dropdown-3').removeClass('active');
    });

  });
 </script>
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

<div class="background"></div>
<ul class="header2" style="clear:both;">
	<div id="dd" class="wrapper-dropdown-3 dd">
		<form class="search"  method="post" action="${pageContent.request.contextPath}/EventMate/Event" style="float:left;margin-left:24%;margin-top:1%;" >
		 <input type="text" name="q" placeholder="Search For An Event"/>
		 
		 <ul class="results" >
			 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Sport" onclick="${pageContent.request.contextPath}/EventMate/Event/Sports">Sports<br /></a></li>
			 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Concert">Concerts<br /></a></li>
	 		<li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Food & Drink">Food & Drink<br /></a></li>
         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Social">Social</a></li>
		 </ul>
		
	</form>
	</div>
<center>
	<span class="eventMate">Event-Mate</span><img src="${pageContent.request.contextPath}/EventMate/images/martini2.jpg" width="40px" height="50px" style="margin-right:8%">
</center>
</ul>

<div class="divMain2" style="min-height:100%;">
	<jsp:include page="Header.jsp" />
	<br><br><br>
	<span class="blueFont" style="margin-left:30%;">Your search results:</span>
	<div id="loaddiv">
	
		
		<%
System.out.println("In render");
List<eventStore> lTweet = (List<eventStore>)request.getAttribute("Events");
if (lTweet==null){
 %>
	<p class="blueFont" style="margin-top:10%;">No event's found</p>
	<% 
}else{
%>


<% 
Iterator<eventStore> iterator;
iterator = lTweet.iterator();     
if(!iterator.hasNext())
{ %>
	<center>
	<p class="blueFont" style="margin-top:10%;">No event's found</p>
	</center>

<%
}

iterator = lTweet.iterator();     
while (iterator.hasNext()){
	eventStore ts = (eventStore)iterator.next();

	%>
	<div class="event">
	  	<% if(ts.getAttending() == false && ts.getNotAttending() == false)
	{%>
	 <form action="${pageContent.request.contextPath}/EventMate/NotAttending" method="post"> 
		
		<button  type="submit" value="<%=ts.getEvent() %>" name="cross" style="float:right;margin-top:3%;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/cross2.png" width="60px" height="60px" ></button>
		</form>
		 <form action="${pageContent.request.contextPath}/EventMate/Attending" method="post"> 
		 	<button  type="submit" value="<%=ts.getEvent() %>" name="tick" style="float:right;margin-top:3%;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/tick2.png" width="60px" height="60px" ></button>
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
		
		 <a href="${pageContent.request.contextPath}/EventMate/Event/<%=ts.getEvent()%>" class="blueFont3"><img src="${pageContent.request.contextPath}/EventMate/images/martini.jpg" width="60px" height="160px" style="float:left;margin-top:1%;" class="userimgBorder"></a>
		
		
		<a href="${pageContent.request.contextPath}/EventMate/Event/<%=ts.getEvent()%>" class="blueFont3" style="margin-left:2%" ><%=ts.getEvent() %></a> <span class="blueFont3">- <%=ts.getDatess() %></span><br>
		<span class="blackFont" style="margin-left:2%;"><%= ts.getDescription() %></span><br>
		<span class="blueFont2" style="margin-left:2%;">Attendee amount: <span class="blackFont" ><%= ts.getAttendee() %></span></span><br>
		<span class="blueFont2" style="margin-left:2%;">Event Requirements: <span class="blackFont" ><%= ts.getEventReq() %></span></span><br>
	    <span class="blueFont2" style="margin-left:2%;">Venue: <span class="blackFont" ><%= ts.getVenue() %></span></span><br>
     	<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" ><%= ts.getLocation() %></span></span><br>
     	<span class="blueFont2" style="margin-left:2%;">Event type: <span class="blackFont" ><%= ts.getCategory() %></span></span><br>
		<%if(ts.getCorrectDistance() == false)
					{%>
					 <span
			 
				class="blueFont2" style="margin-left:2%;">This event is not within your distance preferences</span>
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
	<p class="blueFont" style="margin-top:20%;">Could not find any of your friends</p>
	<% 
}else{
%>


<% 
Iterator<UserStore> iterator;


iterator = friends.iterator();     
if(!iterator.hasNext())
{ %>
	<center>
	<p class="blueFont" style="margin-top:20%;">Could not find any of your friends</p>
	</center>
<%
}
while (iterator.hasNext()){
	UserStore us = (UserStore)iterator.next();

	%>
	<div class="event">
			<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=us.getUsername()%>"><img src="${pageContent.request.contextPath}/EventMate/images/ryan.jpg" width="80px" height="130px" style="float:left;margin-top:1%;" class="userimgBorder"></a>
	<% if(us.getUserFriends() == true)
		{%>
	<form action="${pageContent.request.contextPath}/EventMate/Messages" method="get">
	<input type="hidden" name="name" value="<%= us.getName() %>">
	<button type="submit" value="<%= us.getUsername() %>" name="username" style="float:right;margin-top:2%;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/ic_chat_simple.png"  width="60px" height="60px"></button>
	</form>
		<%} else {%>
		  <span class="blueFont2" style="margin-top:3%;float:right;">You are not friends with this person </span>
		  <%} %>
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
	<%}
	}%></span></span><br>
	
	<!--  
		<img src="images/ryan.jpg" width="80px" height="110px" style="float:left;margin-top:1%;margin-bottom:2%;" class="userimgBorder">
		<img src="images/ic_chat_simple.png" width="60px" height="60px" style="float:right;margin-top:2%;">
		<span class="blueFont3" style="margin-left:2%;">Ryan Dawson</span><br>
		<span class="blackFont" style="margin-left:2%;">Hi there, i'm a 26 year old call center worker.I'm a fun loving lad who loves a good night out</span><br>
		<span class="blueFont2" style="margin-left:2%;">Age: <span class="blackFont" >26</span></span><br>
		<span class="blueFont2" style="margin-left:2%;">Location: <span class="blackFont" >Dundee</span></span><br>
		<span class="blueFont2" style="margin-left:2%;">Interests: <span class="blackFont" >Poetry,Indie,Outdoors</span></span><br>
		
		-->
	</div>


<%

}
}
%>
		
	
	
</div>




</body>
</html>