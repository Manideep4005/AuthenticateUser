<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<title>User Login</title>
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

label {
	display: block;
}

.show {
	font-size: 15px;
	display: flex;
	align-items: center;
	}

.show input {
	width: 15px;
	height: 15px;
	margin: 5px .4em 5px 0;
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

#loginFail {
	background: rgba(255, 127, 127, 0.9);
	border: 1px solid rgba(255, 127, 127, 1);
	padding: 12px;
	border-radius: 1px;
	color: white;
}

.msgSuccess {
	
	background: rgba(208, 240, 192, 1);
	border: 1px solid green;
	padding: 12px;
	border-radius: 1px;
	color: green;
	
}

@media screen and (max-width: 600px) {
	.container {
		width: 320px;
	}
	
	form {
		width: 300px;
	}
	
	.btns {
		width: 300px;
	}
}

</style>

</head>
<body>

	<div class="header">
		<h3>User Authorize Application</h3>
	</div>


	<div class="container">
		<div class="form">
			<form method="POST" action="${pageContext.request.contextPath}/loginuser">
				<c:if test="${userCreated != null}">
					<div class="msgSuccess">${userCreated}</div>
				</c:if>
				
				<c:if test="${userLoginFailed != null}">
					<div id="loginFail">${userLoginFailed}</div>
				</c:if>
				
				<c:if test="${userLogoutMessage != null}">
					<div class="msgSuccess">${userLogoutMessage}</div>
				</c:if>
				
				<c:if test="${userVerfiyEmail != null}">
					<div class="msgSuccess">${userVerfiyEmail}</div>
				</c:if>
				
				<c:if test="${accountActivateMsg != null}">
					<div class="msgSuccess">${accountActivateMsg}</div>
				</c:if>
				
				<c:if test="${passwordChangeSuccess != null}">
					<div class="msgSuccess">${passwordChangeSuccess}</div>
				</c:if>
				
				
				
				<input type="email" placeholder="Email"
					name="username" required="required" class="input_field">
				<p id="error_user"></p>
				<input type="password" placeholder="Password" name="password"
					required="required" id="password" class="input_field">
				<p id="pass_user_error"></p>

				<label class="show"> <input type="checkbox"
					onclick="showPassword()">Show Password
				</label>

				<button type="submit">Login</button>
			</form>
		</div>


		<div class="btns">
			<p id="forgot">
				<a href="${pageContext.request.contextPath}/user-password-mail-form">forgot
					password?</a>
			</p>



			<div class="new">
				<p>
					Don't have a account <a
						href="${pageContext.request.contextPath}/register">Register</a>
				</p>
			</div>

		</div>
	</div>









	<script>
		function showPassword() {
			var password = document.getElementById('password');

			if (password.type === 'password') {
				password.type = 'text';

			} else {
				password.type = 'password';
			}
		}
	</script>
</body>
</html>