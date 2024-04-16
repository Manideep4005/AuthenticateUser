<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-Cache"); // HTTP 1.0
response.setHeader("Expires", "0"); // proxies

if(session.getAttribute("currentUserId") == null || session.getAttribute("currentUserName") == null) {
	

%>
	
	<jsp:forward page="/userlogin"></jsp:forward>

<% }%>