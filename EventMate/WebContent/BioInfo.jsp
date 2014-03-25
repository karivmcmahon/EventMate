<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.stores.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContent.request.contextPath}/css/stylesheet.css"
	rel="Stylesheet" type="text/css"></link>
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
         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Social">Social<br/></a></li>
		 </ul>
		
	</form>
	</div>
		<center>
			<span class="eventMate">Event-Mate</span><img
				src="images/martini2.jpg" width="40px" height="50px" style="margin-right:8%">
		</center>
	</ul>
	<div class="divMain2" style="min-height: 100%;">
		<jsp:include page="${pageContent.request.contextPath}/Header.jsp" />
		<br> <br> <br> <span class="blueFont"
			style="margin-left: 30%;">Please Enter Your Bio Information:</span> <br>
		<br>
		<div class="styleform">
			<form action="${pageContent.request.contextPath}/EventMate/Bio"
				method="post">
				<span class="font">Name:</span> <input type="text" name="name"><br>
				<span class="font" style="color:red;">${invalidName}</span>
				<br> <span class="font">Date of Birth: <select
					name="day">
						<c:forEach begin="01" end="31" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select> - <select name="month">
						<c:forEach begin="01" end="12" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select> - <select name="year">
						<c:forEach begin="1914" end="1996" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select></span><br> <br> <span class="font">Bio:</span> <input
					type="text" name="bio"> <br> <br> <span
					class="font">Gender: </span> <select name="gender">
					<option value="male">Male</option>
					<option value="female">Female</option>
				</select> <br> <br> <span class="font">Gender Pref for
					Attending Events With:</span> <select name="genderPref">
					<option value="male">Male</option>
					<option value="female">Female</option>
					<option value="both">Both</option>
				</select> <br> <br> <span class="font">Age Range: <select
					name="minAge">
						<c:forEach begin="18" end="100" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select> - <select name="maxAge">
						<c:forEach begin="18" end="100" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select></span> <br> <br> <span class="font">Location:</span> <input
					type="text" name="location"> <br> <br> <span
					class="font">Postcode: <input type="text" name="postcode">
					(No Spaces)
				</span>
				<span class="font" style="color:red;">${invalidPostcode}</span>
				 <br> <br> <span class="font">Distance Willing to
					Travel: <select name="distance">
						<c:forEach begin="01" end="1000" var="i">
							<option value="${i}">${i}</option>
						</c:forEach>
				</select> km
				</span> <br> <br> <span class="font">Relationship Status:</span>
				<select name="relationshipStat">
					<option value="single">Single</option>
					<option value="in a relationship">In a Relationship</option>
					<option value="married">Married</option>
					<option value="engaged">Engaged</option>
					<option value="it's complicated">It's Complicated</option>
					<option value="not interested">Not Interested</option>
				</select> <br> <br> <span class="font">Interested In:</span> <select
					name="editGender">
					<option value="male">Male</option>
					<option value="female">Female</option>
					<option value="both">Both</option>
				</select> <br>
				<br>
				<div class="divMain2" style="min-height: 100%;">
					<div style="text-align: center">
						<input type="submit" class="button" name="next" value="Next">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>