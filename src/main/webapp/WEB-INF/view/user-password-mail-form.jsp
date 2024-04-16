<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
<!DOCTYPE html>
<html>
<head>
<title>Reset Password</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/png" href="<c:url value="/resources/images/logo.png"></c:url>">

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

.header {
	padding: 20px;
	box-shadow: 0 0 8px white;
	backdrop-filter: blur(10px);
}

.header h3 {
	text-align: center;
}

.container {
	position: absolute;
	top: 50%;
	left: 50%; 
	transform: translate(-50%, -50%);
	box-shadow: 0 0 4px 1px white;
	width: 400px;
	border-radius: 5px;
	backdrop-filter: blur(10px);
	background-color: rgba(255, 255, 255, 0.01);
}

form {
	width: 350px;
	margin-left:auto;
	margin-right: auto;
	padding: 20px;
}

 .input_field{
	display: block;
	margin: 12px 0;
	width: 100%;
	padding: 10px;
	font-size: 17px;
	background: transparent;
	outline: none;
	border: 1px solid white;
	transition-duration: .4s;
	border-radius: 5px;
}

.input_field:focus {
	border-color: transparent;
	box-shadow: 0 0 0 2px white;
}

.input_field:hover {
	box-shadow: 0 0 5px white;
}

.input_field::placeholder {
	font-size: 17px;
	color: white;
}

.input_field:focus::placeholder {
	opacity: 0.6;
}

button {
	display: block;
	width: 100%;
	cursor: pointer;
	font-size: 17px;
	padding: 10px;
	border: 2px solid white;
	color: white;
	background: transparent;
	transition-duration: .4s;
	margin: 12px 0;
	border-radius: 5px;
}

button:hover {
	background: white;
	color: black;
}

.btns {
	width: 350px;
	margin: auto;
	padding: 10px 20px;
}


.btns p {
 	display: block;
 	text-align: center;
 	margin: 10px 0;
}

.btns a {
	text-decoration: none;
	font-weight: 700;
	transition-duration: .4s;
}

.btns a:hover {
	 text-decoration: underline;
}


@media screen and (max-width: 600px) {
	.container {
		width: 320px;
	}
	
	form {
		width: 300px;
	}
	
	
}

	
</style>

</head>
<body>

	<div class="header"><h3>User Authorize Application</h3></div>
	
	<div class="container">
		
		<div class="form">
		<form method="POST" action="user-password-send-mail">
			<c:if test="${userPasswordMailError != null}">
				${userPasswordMailError }
			</c:if>
			<input class="input_field" type="email" required placeholder="Enter Email" name="userEmail">
			<button type="submit">Submit</button>
		</form>
		</div>
		
		
		<div class="btns">
			<p id="login"> Already have an Account&nbsp;
				<a href="${pageContext.request.contextPath}/userlogin">Login</a>
			</p>



			<div class="new">
				<p>
					Don't have a account <a
						href="${pageContext.request.contextPath}/register">Register</a>
				</p>
			</div>

		</div>
		
	</div>

</body>
</html>