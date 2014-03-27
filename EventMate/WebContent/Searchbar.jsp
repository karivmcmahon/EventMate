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

<!-- Displays search bar and drop down -->
<ul class="header2" style="clear:both;">
		<div id="dd" class="wrapper-dropdown-3 dd">
			
			<form class="search"  method="post" action="${pageContent.request.contextPath}/EventMate/Event" style="float:left;margin-left:22%;margin-top:1%;" >
			 <input type="text" name="q" placeholder="Search..."/>
			 
			 <ul class="results" >
				 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Sports & Fitness" onclick="${pageContent.request.contextPath}/EventMate/Event/Sports & Fitness">Sports & Fitness<br /></a></li>
				 <li class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Concert">Concerts<br /></a></li>
		 		<li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Food & Drink">Food & Drink<br /></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Social">Social</a></li>
	            <li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Lifestyle">Lifestyle<br /></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Hobbies">Hobbies<br/></a></li>
	            <li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Other">Other<br /></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Community">Community<br/></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Outdoors">Outdoors<br/></a></li>
	            <li  class="searchList"><a  class="search" href="${pageContent.request.contextPath}/EventMate/Event/Religion">Religion<br /></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Arts">Arts<br/></a></li>
	         	<li  class="searchList"><a class="search" href="${pageContent.request.contextPath}/EventMate/Event/Tech">Tech<br/></a></li>
	
			 </ul>
			
		</form>
	</div>
   
   <!-- Displays event-mate logo -->
	<span class="eventMate" ><b>Event-Mate</b><img src="${pageContent.request.contextPath}/EventMate/images/martini2.jpg" width="40px" height="50px"  ></span>

</ul>
</body>
</html>