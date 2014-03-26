<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="com.example.stores.*"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
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
<link rel="shortcut icon" href="${pageContent.request.contextPath}/EventMate/images/martiniicon.png" type="image/png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

<title>Event-Mate</title>
</head>
<body>
	<jsp:include page="Searchbar.jsp" />

	<div class="divMain2" style="min-height: 100%;">
		<jsp:include page="${pageContent.request.contextPath}/Header.jsp" />
		<br> <br> <br> <span class="blueFont"
			style="margin-left:40%;">Edit your profile</span> <br>
		<br>
		<div class="styleform">
			<form action = "${pageContent.request.contextPath}/EventMate/Settings" method = "post">
				<span class="font">Edit Name: </span> <input type="text" name="name" value="${user.getName()}">
				<br><br> 
				<span class="font">Edit Email: </span> <input type="text" name="email" value="${user.getEmail()}"><br><br>
				<span class="font">Edit Bio: </span> <textarea ROWS=2 COLS=20 name="editBio">${user.getBio()}</textarea><br>
				<br> <span class="font">Upload New Profile Picture: </span> <input type="button" class="button" name="uploadPic"
						value="Upload"> <br>
				<br> <span class="font">Edit Gender Preference: </span> <select
					name="editGenderPref">
					<option value="male" ${user.getGenderPref() == 'male' ? 'selected' : ''}>Male</option>
					<option value="female" ${user.getGenderPref() == 'female' ? 'selected' : ''}>Female</option>
					<option value="both" ${user.getGenderPref() == 'both' ? 'selected' : ''}>Both</option>
				</select> <br>
				<br> <span class="font">Edit Age Preference: <select
					name="minAge">
						<c:forEach begin="18" end="100" var="i">
							<option value="${i}" ${user.getAgeMin() == i ? 'selected' : ''}>${i}
							</option>
						</c:forEach>
				</select> - <select name="maxAge">
						<c:forEach begin="18" end="100" var="i">
							<option value = "${i}" ${user.getAgeMax() == i ? 'selected' : ''}>${i}
							</option>
						</c:forEach>
				</select></span> <br><br>
				<span class="font">Edit Location: </span> <input type="text" name="location" value="${user.getLocation()}"><br><br>
				<span class="font">Edit Postcode: </span> <input type="text" name="postcode" value="${user.getPostcode()}">
				<br><br> <span class="font">Edit Distance Preference: <select name="distance">
						<c:forEach begin="01" end="1000" var="i">
							<option value = "${i}" ${user.getDistance() == i ? 'selected' : ''}>${i}
							</option>
						</c:forEach>
				</select> km
				</span><br><br> <span class="font">Relationship Status: </span>
				<select name="relationshipStat">
					<option value="single">Single</option>
					<option value="in a relationship">In a Relationship</option>
					<option value="married">Married</option>
					<option value="engaged">Engaged</option>
					<option value="it's complicated">It's Complicated</option>
					<option value="not interested">Not Interested</option>
				</select> <br> <br> <span class="font">Interested In:</span> <select
					name="editGender">
					<option value="male"  ${user.getGenderPref() == 'male' ? 'selected' : ''}>Male</option>
					<option value="female"  ${user.getGenderPref() == 'female' ? 'selected' : ''}>Female</option>
						<option value="both"  ${user.getGenderPref() == 'both' ? 'selected' : ''}>Both</option>
				</select><br>
				<br>
				<br>
				<div style="text-align: center">
					<input type="submit" class="button" name="next"
						value="Next"></a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>