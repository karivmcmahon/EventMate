<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.stores.*"%>
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
<link rel="shortcut icon"
	href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png"
	type="image/png">
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
			List<ProfileStore> lTweet = (List<ProfileStore>) request
					.getAttribute("Profile");
			if (lTweet == null) {
		%>
		<p class="blueFont" style="margin-top:30%;">Profile wasn't found!</p>
		<%
			} else {
		%>


		<%
			Iterator<ProfileStore> iterator;

				iterator = lTweet.iterator();    
				if(!iterator.hasNext())
				{ %>
					<center>
					<p class="blueFont" style="margin-top:30%;">Profile wasn't found</p>
					</center>
				<%
				}
				while (iterator.hasNext()) {
					ProfileStore ts = (ProfileStore) iterator.next();
		%>
		<div class="profile">
			<div class="profilePicture">
				<img src="<%=ts.getPhoto() %>" class=imgBorder width="250px"
					height="300px">
			</div>
			<div class="profileRight">
				<p class="blueFont"><%=ts.getName()%></p>

				<br /> <br />
				<p class="blueFont2"><%=ts.getBio()%></p>
				<!-- NEED TO DO DOB!!!! -->
				<br> <br /> <span class="blueFont2"> Location: 
				</span> <span class="blackFont"><%=ts.getLocation()%></span> <br> <br>
				<span class="blueFont2"> Relationship
						Status: 
				</span> <span class="blackFont"><%=ts.getStatus()%></span> <br> <br>
				
				<span class="blueFont2"> Age: 
				</span> <span class="blackFont"><%=ts.getAge()%></span><br><br>
				
				<span class="blueFont2"> Interested in: 
				</span> <span class="blackFont"><%=ts.getInterestedIn()%></span><br><br>
				
				<% UserStore u = new UserStore();
				u = (UserStore) request.getSession().getAttribute("currentSeshUser");
				System.out.println("Ts username " + ts.getUsername());
				System.out.println("U username " + u.getUsername());
				if(ts.getUserFriends() == false && !ts.getUsername().equals(u.getUsername()))
		{%>
		 <span class="blueFont2" >You are not friends with this person </span>

		<%}%>
		 
		 
			</div>
			<div class="profileMore">
				<span class="blueFont2"> Music: 
				</span> <span class="blackFont"><%=ts.getMusic()%> </span> <br /> <br /> <span class="blueFont2"> Interests: 
				</span> <span class="blackFont"><%=ts.getInterests() %> </span> <br /> <br /> <span class="blueFont2"> Sports: 
				</span> <span class="blackFont"><%=ts.getSports() %> </span>
			</div>
			<div class="profileEvents">
				<span class="blueFont2"> Past Events: </span> <span class="blackFont"><%=ts.getEventList() %> </span>
			</div>
		</div>
		<%
			}
			}
		%>
	</div>
</body>
</html>