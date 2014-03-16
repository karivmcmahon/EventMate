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
<title>Event-Mate</title>
</head>
<body>
	<ul class="header2">
		<center>
			<span class="eventMate">Event-Mate</span><img
				src="images/martini2.jpg" width="40px" height="50px">
		</center>
	</ul>

	<div class="divMain2" style="min-height: 100%;">
		<jsp:include page="${pageContent.request.contextPath}/Header.jsp" />
		<br> <br> <br> <span class="blueFont"
			style="margin-left: 30%;">Please Enter Your Bio Information:</span> <br>
		<br>
		<div class="styleform">
			<form>
				<span class="font">Bio:</span> <input type="text" name="Bio">
				<br>
				<br> <span class="font">Gender: </span><select name="gender">
					<option value="male">Male</option>
					<option value="female">Female</option>
				</select> <br>
				<br> <span class="font">Gender Pref:</span> <select
					name="gender">
					<option value="male">Male</option>
					<option value="female">Female</option>
					<option value="both">Both</option>
				</select> <br>
				<br> <span class="font">Location:</span> <input type="text"
					name="EmailSignUp"> <br>
				<br> <span class="font">Postcode:</span> <input type="text"
					name="EmailSignUp"> <br>
				<br> <span class="font">Distance Willing to Travel:</span> <input
					type="text" name="EmailSignUp"> <br>
				<br> <span class="font">Relationship Status:</span> <input
					type="text" name="EmailSignUp"> <br>
				<br>
				<div style="text-align: center">
					<input type="submit" class="button" name="next"
						value="Next">
				</div>
			</form>
		</div>
	</div>
</body>
</html>