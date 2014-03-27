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
		$('#loaddiv').fadeOut('fast').load('${pageContext.request.contextPath}/EventMate/Friends #loaddiv').fadeIn("slow");
		}, 900000);
		</script>
		
		<title>Event-Mate</title>
	</head>
<body>

<!-- Searchbar and event-mate title -->
<jsp:include page="Searchbar.jsp" />

<div class="divMain2" style="min-height:100%;">
	
	<!-- Icons header inside rounded div -->
	<jsp:include page="Header.jsp" />
	
	<br><br><br>
	
	<div id="loaddiv">
	
		<center>
			<span class="blueFont">Your Friends</span>
		</center>
		
	
			<%
			System.out.println("In render");
			List<UserStore> friends = (List<UserStore>)request.getAttribute("Friends");
			if (friends==null){
			 %>
				<p class="blueFont" style="margin-top:30%">Could not find any of your friends</p>
				<% 
			}
			else
			{
		
				Iterator<UserStore> iterator;
				
				
				iterator = friends.iterator();     
				if(!iterator.hasNext())
				{ %>
					<center>
					<p class="blueFont" style="margin-top:30%;">Could not find any of your friends</p>
					</center>
				<%
				}
				while (iterator.hasNext()){
					UserStore us = (UserStore)iterator.next();
				
					%>
					<!--  Displays list of friends -->
					<div class="event"  style="margin-top:2%;margin-bottom:2%;">
						
						<a href="${pageContent.request.contextPath}/EventMate/Profile/<%=us.getUsername()%>"><img src="<%= us.getPhoto() %>" height="130px" style="float:left;margin-top:1%;" class="userimgBorder"></a>
						
						<form action="${pageContent.request.contextPath}/EventMate/Messages" method="get">
								<input type="hidden" name="name" value="<%= us.getName() %>">
								<button type="submit" value="<%= us.getUsername() %>" name="username" style="float:right;margin-top:2%;padding: 0;border: none;background: none;"><img src="${pageContent.request.contextPath}/EventMate/images/ic_chat_simple.png"  width="60px" height="60px"></button>
						</form>
						
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
							{
							%>
								<%= list.get(i) %>,
							<%
							}
						}
						%>
						</span>
						</span>
						<br>
				
					</div>
				
				
					<%
					
					}
					}
					%>
				</div>
	
</div>




</body>
</html>