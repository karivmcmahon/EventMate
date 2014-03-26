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
<title>Event-Mate</title>
<!-- Ajax script to refresh tweet timeline every 15 mins, fade out is fast to show refresh occuring -->
<script>
var auto_refresh = setInterval(
function()
{
 $.ajaxSetup({ cache: false });
$('#m').fadeOut('fast').load('${pageContext.request.contextPath}/EventMate/Messages #m').fadeIn("slow");
}, 900000);
</script>
  
   <script type="text/javascript">
   window.onload = initAll;
   function initAll() {
    var box = document.getElementById('m');
    box.scrollTop = box.scrollHeight;
   }
  
 
    </script>


</head>
<body>
   
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
	<span class="eventMate">Event-Mate</span><img src="images/martini2.jpg" width="40px" height="50px" style="margin-right:8%">
</center>
</ul>

<div class="divMain2" style="min-height:100%;">
	<jsp:include page="Header.jsp" />
	<br><br><br>
	<center><%UserStore friendName = (UserStore)request.getAttribute("Friend");%>
		<span class="blueFont" >Messages From <%= friendName.getName() %></span>
	</center>
	
	<div  id="m">
	
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
		<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=ts.getFrom()%>"><img src="<%= u.getPhoto() %>" width="60px" height="60px" style="float:right;" class="userimgBorder"></a>
		<div class="bubble" style="float:right;">
		
	  		<div class="smallBlackFont"><%= ts.getMessage()%></div>
	 	</div>
	 	</div>
	 	<%}
	else
	 		{%>
	 		<% System.out.println("Empty"); %>
		  <div class="beforeBubble2">
		   <center>
		   
		   <span class="smallBlackFont" ><%=ts.getDate()%></span>
		   </center>
		  <a href="${pageContent.request.contextPath}/EventMate/Profile/<%=ts.getFrom()%>"> <img src="<%= friendName.getPhoto() %>" width="60px" height="60px" style="float:left;" class="userimgBorder"></a>
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
	<form action="${pageContent.request.contextPath}/EventMate/Messages" method="POST">
	<textarea  name="postMessage" rows="6" cols="70" class="textarea" ></textarea>
	<input type="hidden"  value="<%= friendName.getName() %>" name="name">
	<input type="hidden"  value="<%= friendName.getUsername() %>" name="sendingTo">
	<input type="hidden"  value="<%= friendName.getPhoto() %>" name="photo">
	<input type="submit" value="Send" class="button" >
	</form>
	

	
</div>
</body>
</html>