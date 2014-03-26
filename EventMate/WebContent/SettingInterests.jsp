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
		<div align="center">
		<br> <br> <br> <span class="blueFont" >Edit Your Interest Settings</span> <br></div>
		<br>
		<div c align="center">
		<span class="fontCheck">Interests:</span><br><br>
			<form action = "${pageContent.request.contextPath}/EventMate/SettingInterests" method = "post">
				<span class="fontCheck"><input type="checkbox" name="interest" value="Reading" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Reading' ? 'checked' : ''}
</c:forEach>>Reading</span> 
<span class="fontCheck"><input type="checkbox" name="interest" value="Poetry" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Poetry' ? 'checked' : ''}
</c:forEach>>Poetry</span>
<span class="fontCheck"><input type="checkbox" name="interest" value="Drawing" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Drawing' ? 'checked' : ''}
</c:forEach>>Drawing</span>
<span class="fontCheck"><input type="checkbox" name="interest" value="Tech" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Tech' ? 'checked' : ''}
</c:forEach>>Tech</span><br>
<span class="fontCheck"><input type="checkbox" name="interest" value="Video Games" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Video Games' ? 'checked' : ''}
</c:forEach>>Video Games</span>
<span class="fontCheck"><input type="checkbox" name="interest" value="Outdoors" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Outdoors' ? 'checked' : ''}
</c:forEach>>Outdoors</span>
<span class="fontCheck"><input type="checkbox" name="interest" value="Watching TV" <c:forEach items="${user.getInterests()}" var="interests">
${interests == 'Watching TV' ? 'checked' : ''}
</c:forEach>>Watching TV</span><br><br>

<br><br>
<span class="fontCheck">Sports:</span><br><br>

<span class="fontCheck"><input type="checkbox" name="sport" value="Running" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Running' ? 'checked' : ''}
</c:forEach>>Running</span> 
<span class="fontCheck"><input type="checkbox" name="sport" value="Cycling" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Cycling' ? 'checked' : ''}
</c:forEach>>Cycling</span>
<span class="fontCheck"><input type="checkbox" name="sport" value="Tennis" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Tennis' ? 'checked' : ''}
</c:forEach>>Tennis</span>
<span class="fontCheck"><input type="checkbox" name="sport" value="Football" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Football' ? 'checked' : ''}
</c:forEach>>Football</span><br>
<span class="fontCheck"><input type="checkbox" name="sport" value="Rugby" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Rugby' ? 'checked' : ''}
</c:forEach>>Rugby</span>
<span class="fontCheck"><input type="checkbox" name="sport" value="Swimming" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Swimming' ? 'checked' : ''}
</c:forEach>>Swimming</span>
<span class="fontCheck"><input type="checkbox" name="sport" value="Ultimate" <c:forEach items="${user.getSports()}" var="sports">
${sports == 'Ultimate' ? 'checked' : ''}
</c:forEach>>Ultimate Frisbee</span><br><br>
<br><br>

<span class="fontCheck">Music:</span><br><br>

<span class="fontCheck"><input type="checkbox" name="music" value="Pop" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Pop' ? 'checked' : ''}
</c:forEach>>Pop</span> 
<span class="fontCheck"><input type="checkbox" name="music" value="Rock" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Rock' ? 'checked' : ''}
</c:forEach>>Rock</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Indie" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Indie' ? 'checked' : ''}
</c:forEach>>Indie</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Metal" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Metal' ? 'checked' : ''}
</c:forEach>>Metal</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Jazz" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Jazz' ? 'checked' : ''}
</c:forEach>>Jazz</span>
<span class="fontCheck"><input type="checkbox" name="music" value="J-Pop" <c:forEach items="${user.getMusic()}" var="music">
${music == 'J-Pop' ? 'checked' : ''}
</c:forEach>>J-Pop</span><br>
<span class="fontCheck"><input type="checkbox" name="music" value="Classical" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Classic' ? 'checked' : ''}
</c:forEach>>Classical</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Dubstep" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Dubstep' ? 'checked' : ''}
</c:forEach>>Dubstep</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Garage" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Garage' ? 'checked' : ''}
</c:forEach>>Garage</span>
<span class="fontCheck"><input type="checkbox" name="music" value="Grunge" <c:forEach items="${user.getMusic()}" var="music">
${music == 'Grunge' ? 'checked' : ''}
</c:forEach>>Grunge</span><br><br>
				<div style="text-align: center">
					<input type="submit" class="button" name="buttonFinishEdit"
						value="Finished">
				</div>
				<br><br>

			</form>
		</div>
	</div>
</body>
</html>