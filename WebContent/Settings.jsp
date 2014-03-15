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
<link rel="shortcut icon" href="/images/Red-Solo-Cup3.jpg"
	type="image/jpg">
<title>EventMate - Settings</title>
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
			style="margin-left:40%;">Edit your profile</span> <br>
		<br>
		<div class="styleform">
			<form>
				<span class="font">Edit Name:</span> <input type="text" name="Name">
				<br>
				<br> <span class="font">Edit Bio: </span> <input type="text" name="editBio"><br>
				<br> <span class="font">Upload New Profile Picture: </span> <input type="button" class="button" name="uploadPic"
						value="Upload"> <br>
				<br> <span class="font">Edit Gender Preference:</span> <select
					name="editGender">
					<option value="male">Male</option>
					<option value="female">Female</option>
				</select> <br>
				<br> <span class="font">Edit Age Preference:</span> <input type="text" name="agePref"> <br>
				<br> <span class="font">Edit Distance Preference:</span> <input type="text"
					name="distPref"> <br>
				<br>
				<br>
				<div style="text-align: center">
					<a href="SettingInterests.jsp"><input type="button" class="button" name="next"
						value="Next"></a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>