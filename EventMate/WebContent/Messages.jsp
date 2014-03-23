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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
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
<ul class="header2">
<center>
	<span class="eventMate">Event-Mate</span><img src="images/martini2.jpg" width="40px" height="50px" >
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
	<p class="blueFont">No message's found</p>
	<% 
}else{
%>


<% 
Iterator<MessagerStore> iterator;


iterator = messages.iterator();     
while (iterator.hasNext()){
	MessagerStore ts = (MessagerStore)iterator.next();

	%>
	<div class="event">
	<form action="${pageContent.request.contextPath}/EventMate/Messages" method="get">
	<input type="hidden" name="name" value="<%= ts.getName() %>">
	<button type="submit" value="<%= ts.getMessager() %>" name="username" style="float:right;padding: 0;
border: none;
background: none;"><img src="images/ic_post.png"  width="60px" height="60px"></button>
	</form>

		
		<img src="images/ryan.jpg" width="60px" height="60px" style="float:left;" class="userimgBorder">
			<span class="blueFont"><%= ts.getName() %></span>
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