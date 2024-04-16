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

input {
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

input:focus {
	border-color: transparent;
	box-shadow: 0 0 0 2px white;
}

input:hover {
	box-shadow: 0 0 5px white;
}

input::placeholder {
	font-size: 17px;
	color: white;
}

input:focus::placeholder {
	opacity: 0.6;

}

button {
	display: block;
	width: 100%;
	cursor: pointer;
	font-size: 17px;
	padding: 10px;
	border: 2px solid white;
	color: black;
	background: white;
	transition-duration: .4s;
	margin: 12px 0;
	border-radius: 5px;
}

button:hover {
	background: transparent;
	color: white;
}



.btns {
	width: 400px;
	margin: 10px auto;
	
}

.btns span, a{
	display: block;
	text-align: center;
}

.btns a {
	color: black;
	text-decoration: none;
	width: 100px;
	margin: 8px auto;
	padding: 8px;
	background: white;
	border: 2px solid white;
	transition-duration: .4s;
	font-size: 17px;
}

.btns a:hover {

	background: transparent;
	color: white;
}



#msg {
	color: red;
	margin: 0;
	border-radius: 5px;
}

#userErrorRegister {
	/*background: rgba(255, 127, 127, 0.9);
	border: 1px solid rgba(255, 127, 127, 1);
	padding: 12px;
	border-radius: 1px;
	color: white;
	*/
	
	background: rgba(255, 127, 127, 1);
	border: 1px solid rgba(255, 127, 127, 1);
	padding: 12px;
	border-radius: 1px;
	color:white;
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
			<form:form action="${pageContext.request.contextPath}/register" method="POST" modelAttribute="user_auth" onsubmit="return validateForm()">
				<c:if test="${userRegisterError != null}">
					<div id="userErrorRegister">${userRegisterError }</div>
				</c:if>
				
				<form:hidden path="userId"/>

				<form:input path="firstName" placeholder="First Name" required="required"/>
				<form:input path="lastName" placeholder="Last Name" required="required"/>
				<form:input path="email" type="email" placeholder="Email" required="required"/>
				<form:input path="" type="password" placeholder="Password" required="required" id="pass" onkeyup="validatePassword()" />
				<form:input path="password" type="password" placeholder="Re enter Password" id="repass" required="required" onkeyup="validatePassword()"/>
				<p id="msg"></p>
				
				<button type="submit" class="button">Register</button>
			</form:form>
		</div>

		<div class="btns">
			<span>Already have an Account </span><a href="${pageContext.request.contextPath}/userlogin">Login</a>
		</div>
	</div>

	<script type="text/javascript">
		function validatePassword() {
			var pass = document.getElementById('pass').value;
			var repass = document.getElementById('repass').value;
			var isValid = false;
			
			
			
			if (pass.length < 1 || repass.length < 1) {

				document.getElementById('msg').innerHTML = '';
				isValid = false;
			} else if (pass.length <= 7 || repass.length <= 7) {
				document.getElementById('msg').innerHTML = 'Password must be 8 characters';
				document.getElementById('msg').style.color = 'red';
				isValid = false;
			}

			else if (pass.length != repass.length) {
				document.getElementById('msg').innerHTML = 'Password doesn\'t match';
				document.getElementById('msg').style.color = 'red';
				isValid = false;
			}

			else if (pass != repass) {
				document.getElementById('msg').innerHTML = 'Password doesn\'t match';
				document.getElementById('msg').style.color = 'red';
				isValid = false;
			} else {
				document.getElementById('msg').innerHTML = 'Password matched';
				document.getElementById('msg').style.color = 'darkgreen';
				isValid = true;
			}
			
			
			return isValid;
		}
		
		function validateForm() {
			
			var isValid = validatePassword();
			var btn = document.querySelector(".button");

	   		var container = document.querySelector(".container");
		       
	   		if (isValid) {
	   			btn.disabled = true;
	   			btn.style.opacity = "0.8";
	   			btn.style.cursor = "not-allowed";
	   			container.style.opcaity = "0.5";
				
	   			btn.addEventListener("mouseover", () => {
	   				btn.style.backgroundColor = "white";
	   				btn.style.color = "black";
	   			});
	   		}
			
		}
	
	</script>
	

</body>
</html>