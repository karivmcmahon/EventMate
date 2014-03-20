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
	<span class="blueFont" style="margin-left:30%;">Your Friends</span>
	
	
		<%
System.out.println("In render");
List<UserStore> friends = (List<UserStore>)request.getAttribute("Friends");
if (friends==null){
 %>
	<p class="blueFont">No event's found</p>
	<% 
}else{
%>


<% 
Iterator<UserStore> iterator;


iterator = friends.iterator();     
while (iterator.hasNext()){
	UserStore us = (UserStore)iterator.next();

	%>
	<div class="event">
	<img src="images/ryan.jpg" width="80px" height="130px" style="float:left;margin-top:1%;" class="userimgBorder">
	<form action="${pageContent.request.contextPath}/EventMate/Messages" method="get">
	<button type="submit" value="<%= us.getUsername() %>" name="username" style="float:right;margin-top:2%;padding: 0;
border: none;
background: none;"><img src="images/ic_chat_simple.png"  width="60px" height="60px"></button>
	</form>
	<span class="blueFont3" style="margin-left:2%;"><%= us.getName() %></span><br>
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