<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Codystar'
	rel='stylesheet' type='text/css'>
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
</head>
<body>
	<ul class="header2" style="clear:both;">
	<div id="dd" class="wrapper-dropdown-3 dd">
		<form class="search"  method="post" action="${pageContent.request.contextPath}/EventMate/Event" style="float:left;margin-left:24%;margin-top:1%;" >
		 <input type="text" name="q" placeholder="Search For An Event"/>
		 
		 <ul class="results" >
			 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Sport" onclick="${pageContent.request.contextPath}/EventMate/Event/Sports">Sports<br /></a></li>
			 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Concert">Concerts<br /></a></li>
	 		<li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Food & Drink">Food & Drink<br /></li>
         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Social">Social</a></li>
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
		<div align="center">
		<br> <br> <br> <span class="blueFont" ><b>Please Enter Your Bio Information</b></span> <br></div>
		<br>
		<div c align="center">
		<span class="fontCheck"><b>Interests: (Pick Up to 5)</b></span><br><br>
			<form action="${pageContent.request.contextPath}/EventMate/Interests"
				method="post">
				<span class="fontCheck"><input type="checkbox" name="interest" value="Reading">Reading</span> 
				<span class="fontCheck"><input type="checkbox" name="interest" value="Poetry">Poetry</span>
				<span class="fontCheck"><input type="checkbox" name="interest" value="Drawing">Drawing</span>
				<span class="fontCheck"><input type="checkbox" name="interest" value="Tech">Tech</span><br>
				<span class="fontCheck"><input type="checkbox" name="interest" value="Video Games">Video Games</span>
				<span class="fontCheck"><input type="checkbox" name="interest" value="Outdoors">Outdoors</span>
				<span class="fontCheck"><input type="checkbox" name="interest" value="Watching TV">Watching TV</span><br><br>

			<br><br>
			<span class="fontCheck"><b>Sports: (Pick Up to 5)</b></span><br><br>

				<span class="fontCheck"><input type="checkbox" name="sport" value="Running">Running</span> 
				<span class="fontCheck"><input type="checkbox" name="sport" value="Cycling">Cycling</span>
				<span class="fontCheck"><input type="checkbox" name="sport" value="Tennis">Tennis</span>
				<span class="fontCheck"><input type="checkbox" name="sport" value="Football">Football</span><br>
				<span class="fontCheck"><input type="checkbox" name="sport" value="Rugby">Rugby</span>
				<span class="fontCheck"><input type="checkbox" name="sport" value="Swimming">Swimming</span>
				<span class="fontCheck"><input type="checkbox" name="sport" value="Ultimate">Ultimate Frisbee</span><br><br>
			<br><br>

			<span class="fontCheck"><b>Music: (Pick Up to 5)</b></span><br><br>

				<span class="fontCheck"><input type="checkbox" name="music" value="Pop">Pop</span> 
				<span class="fontCheck"><input type="checkbox" name="music" value="Rock">Rock</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Indie">Indie</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Metal">Metal</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Jazz">Jazz</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="J-Pop">J-Pop</span><br>
				<span class="fontCheck"><input type="checkbox" name="music" value="Classical">Classical</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Dubstep">Dubstep</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Garage">Garage</span>
				<span class="fontCheck"><input type="checkbox" name="music" value="Grunge">Grunge</span><br><br>

				<div style="text-align: center">
					<input type="submit" class="button" name="buttonSignUp"
						value="Sign Up">
				</div>
				<br><br>

			</form>
		</div>
	</div>
</body>
</html>