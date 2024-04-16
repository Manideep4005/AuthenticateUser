<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="user-session-validate.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>Home | User Authorize Mail</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/png"
	href="<c:url value="/resources/images/logo.png"></c:url>">



<style type="text/css">
* {
	margin: 0;
	padding: 0;
	font-family: 'Poppins', sans-serif;
	color: white;
	box-sizing: border-box;
}

body {
	background-image: url("<c:url value="/resources/images/user-login-background.png"></c:url>");
	background-size: cover;
	background-attachment: fixed;
}

ul {
	list-style-type: none;
	margin: 0;
	padding: 10px;
	overflow: hidden;
	box-shadow: 0px 0px 10px #e3e3e3;
}

li {
	float: left;
}

li a {
	display: inline-block;
	text-align: center;
	padding: 10px 16px;
	text-decoration: none;
	font-weight: 600;
	transition-duration: .4s;
	border-radius: 5px;
	color: black;
}

li a:hover, .dropdown:hover {
	background-color: skyblue;
	color: white;
}

li.dropdown {
	display: inline-block;
	border-radius: 5px;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-image: url("<c:url value="/resources/images/user-login-background.png"></c:url>");
	background-size: cover;
	background-attachment: fixed;
	min-width: 160px;
	margin-left: -30px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
	border-radius: 10px;
	background-size: cover;
	background-attachment: fixed;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
	font-weight: 300;
}

.dropdown-content a:hover {
	background-color: skyblue;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropbtn {
	display: inline-flex;
	height: 38px;
	padding: 0;
	background: transparent;
	border: none;
	outline: none;
	overflow: hidden;
	font-size: 16px;
}

.dropbtn span {
	color: black;
}

.dropbtn span:hover {
	color: white;
}

a img {
	height: auto;
	width: 1em;
	display: flex;
}

.button-text, .button-icon {
	display: inline-flex;
	align-items: center;
	padding: 3px;
	height: 100%;
	font-weight: 600;
}

.button-icon {
	font-size: 1.5em;
}

#a {
	display: block;
	text-decoration: none;
	font-size: 18px;
	width: 100px;
	padding: 8px;
	border: 2px solid black;
	color: white;
	background: black;
	transition-duration: .4s;
	text-align: center;
}

#a:hover {
	color: black;
	background: white;
}

@media screen and (max-width: 600px) {
	li a {
		padding: 10px 4px;
	}
	li {
		display: flex;
	}
	.dropdown-content {
		
	}
}

.container {
	top: 50%;
	left: 50%;
	position: absolute;
	transform: translate(-50%, -50%);
	
}

.header {
	padding: 10px;
}

.header p{
	text-align: center;
	font-size: 24px;
}
</style>

</head>
<body>

	<div class="topnav">
		<ul>
			<div class="header"><p>User Authorize Application</p></div>
			<li><a href="${pageContext.request.contextPath }/Home">Home</a></li>
			<li><a href="#">Contact Us</a></li>
			<li><a href="#">About us</a></li>
			<li class="dropdown" style="float: right; margin-right: 10px;"><a
				class="dropbtn" class="button-icon"> <span class="button-icon"><img
					src="<c:url value="/resources/images/user.png"></c:url>"></span><span class="button-text">${currentUserName }</span>
			</a>


				<div class="dropdown-content">
					<a href="user-details?id=${currentUserId}">My Account</a> <a
						href="${pageContext.request.contextPath }/logout">Logout</a>
				</div></li>
		</ul>
	</div>

	

	<div class="container">
		<p>UserName : ${currentUserName }</p>
		<br>

		<p>User Id : ${currentUserId }</p>
		<br> <a href="${pageContext.request.contextPath}/logout" id="a">Logout</a>

	</div>


</body>
</html>