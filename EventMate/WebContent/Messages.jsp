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
$('#loaddiv').fadeOut('fast').load('${pageContext.request.contextPath}/EventMate/Messagers #loaddiv').fadeIn("slow");
}, 900000);
</script>
<title>Event-Mate</title>
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
	<span class="eventMate">Event-Mate</span><img src="${pageContent.request.contextPath}/EventMate/images/martini2.jpg" width="40px" height="50px" style="margin-right:8%" >
</center>
</ul>

<div class="divMain2" style="min-height:100%;">
	<jsp:include page="Header.jsp" />
	<br><br><br>
	<center>
	<span class="blueFont" >Your Messages:</span>
	</center>

	<div id="loaddiv">

			<%
System.out.println("In render");
List<MessagerStore> messages = (List<MessagerStore>)request.getAttribute("Message");
if (messages==null){
 %>
	<p class="blueFont" style="margin-top:30%;">Could not find any messages</p>
	<% 
}else{
%>


<% 
Iterator<MessagerStore> iterator;


iterator = messages.iterator();    
if(!iterator.hasNext())
{ %>
	<center>
	<p class="blueFont" style="margin-top:30%;">Could not find any messages</p>
	</center>
<%
}
while (iterator.hasNext()){
	MessagerStore ts = (MessagerStore)iterator.next();

	%>
	<div class="event">
	<form action="${pageContent.request.contextPath}/EventMate/DisplayMessages" method="post">
	<input type="hidden" name="name" value="<%= ts.getName() %>">
	<button type="submit" value="<%= ts.getMessager() %>" name="username" style="float:right;padding: 0;
border: none;
background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/ic_post.png"  width="60px" height="60px"></button>
	</form>


		<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=ts.getMessager()%>"><img src="${pageContent.request.contextPath}/EventMate/images/ryan.jpg" width="60px" height="60px" style="float:left;" class="userimgBorder"></a>
		<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=ts.getMessager()%>" class="blueFont">	<%= ts.getName() %></a>
			<br><br><br>

	</div>
<%

}
}
%>


</div>	
</div>
</body>
</html>