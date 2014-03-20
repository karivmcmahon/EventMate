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
	<center>
		<span class="blueFont" >Messages From ....</span>
	</center>
	<div class="message">
	
	<%
System.out.println("In render");
List<MessageStore> Message = (List<MessageStore>)request.getAttribute("Messages");
if (Message==null){
 %>
 <% System.out.println("Empty"); %>
	<p class="blueFont">No event's found</p>
	<% 
}else{
%>


<% 
UserStore u = (UserStore)request.getSession().getAttribute("currentSeshUser");
Iterator<MessageStore> iterator;

System.out.println("User logged in " + u.getUsername());
iterator = Message.iterator();     
if(!iterator.hasNext())
{ %>
	<center>
	<p class="blueFont" style="margin-top:50%;">Go on send a message......</p>
	</center>
<%
}
else
{ 
while (iterator.hasNext()){
	MessageStore ts = (MessageStore)iterator.next();
	if(u.getUsername().equals(ts.getFrom()))
	{
	%>
		
		<% System.out.println("Empty"); %>
		<div class="beforeBubble">
		<center>
		<span class="smallBlueFont"><%=ts.getDate()%></span>
		</center>
		<img src="images/ryan.jpg" width="60px" height="60px" style="float:right;" class="userimgBorder">
		<div class="bubble" style="float:right;">
		
	  		<div class="senderPost"><%= ts.getMessage()%></div>
	 	</div>
	 	</div>
	 	<%}
	else
	 		{%>
	 		<% System.out.println("Empty"); %>
		  <div class="beforeBubble2">
		   <center>
		   
		   <span class="smallBlueFont" ><%=ts.getDate()%></span>
		   </center>
		   <img src="images/ryan.jpg" width="60px" height="60px" style="float:left;" class="userimgBorder">
		  <div class="bubble2" style="float:left;">
	 
	  <div class="senderPost"><%= ts.getMessage() %></div>
	  </div>
	  </div>
	  <%} %>
	  
	 
	 
<%
}
	 		}
}

%>
	
	 
	 </div>

	<textarea  name="postTweet" rows="6" cols="70" class="textarea" ></textarea>
	<input type="submit" value="Send" class="button" >
	

	
</div>
</body>
</html>