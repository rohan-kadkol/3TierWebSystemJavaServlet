<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration</title>
</head>
<body>
<h1 align="center"><img src="${imageLink}">Welcome ${name}</h1>
<ul>
    <li><b>Your name: </b>${name}</li>
    <li><b>Your email: </b>${email}</li>
    <li><b>Your location: </b>${location}</li>
    <li><b>Your gender: </b>${gender}</li>
    <li><b>Your experience: </b>${experience}</li>
</ul>
<br>
<br>
</body>
</html>