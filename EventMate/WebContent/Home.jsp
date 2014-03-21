<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/stylesheet.css" rel="Stylesheet" type="text/css"></link>
<link href='http://fonts.googleapis.com/css?family=Vibur' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Glegoo' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Codystar' rel='stylesheet' type='text/css'>
<link rel="shortcut icon" href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png" type="image/png">
<title>Event-Mate</title>
</head>
<body class="signUp">

<ul class="header2">
<center>
	<span class="eventMate">Event-Mate</span><img src="images/martini2.jpg" width="40px" height="50px" >
</center>
</ul>
<div class="main">



<div class="divRight">
<span class="eventMate2">Login</span><br>
<form action="${pageContent.request.contextPath}/EventMate/Login" method="post">
	<span class="font">Username</span>
	<input type="text"  name="username" class="buttonMargin"><br>
	<span class="font">Password</span>
	<input type="password"  name="password" >
	<input type="submit" class="button" name="login" value="Login">
</form>
</div>



<div class="divLeft">
<span class="eventMate2">What is Event-Mate ?</span><br>
<span class="smallGreyFont">
Sign Up. <br></span>
<span class="smallGreyFont">
Select events you want to attend. <br></span>
<span class="smallGreyFont">
Event-Mate will enable you to be friends with 3 people </span> <span class="smallGreyFont"> who have the most in common with you.<br></span>
<span class="smallGreyFont">
Message each other and arrange going to events  </span> <span class="smallGreyFont"> together. <br><br>
</span>
<span class="eventMate2">What type of events ?</span><br><br>
<span class="smallGreyFont">Festivals. Gigs.Football games.Poetry <span class="smallGreyFont"> readings.Running club. And many more. </span></span>
</div>

<div class="divRight2">
<span class="eventMate2">Sign Up</span><br>
<form action="${pageContent.request.contextPath}/EventMate/SignUp" method="post">
<span class="font">Username:</span>
<input type="text"  name="usernameSignUp" class="buttonMargin"><br>
<span class="font">Password:</span>
<input type="password"  name="passwordSignUp" >
<span class="font">Email:</span>
<input type="text"  name="emailSignUp" >
<input type="submit" class="button" name="buttonSignUp" value="Sign Up"style="float: right;">
</form>
</div>






</div>

</body>
</html>