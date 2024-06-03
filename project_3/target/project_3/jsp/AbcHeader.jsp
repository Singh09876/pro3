<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.AbcDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
.raj {    
	background-image: linear-gradient(to bottom right,#8A2BE2, pink);
}
</style>
</head>
<body>



	<%
	AbcDTO abcDto = (AbcDTO) session.getAttribute("user");

		boolean userLoggedIn = abcDto != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {
			welcomeMsg += abcDto.getName() ;
		} else {
			welcomeMsg += "Guest";
		}
	%>
	<div class="header">
		<nav class="navbar navbar-expand-lg raj fixed-top"> <a
			class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"><img
			src="<%=ORSView.APP_CONTEXT%>/img/custom.png" width="190px"
			height="50px"></a>
		<button class="navbar-toggler " type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"> <i class="fas fa-bars"
				style="color: #fff; font-size: 28px;"></i></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="nav navbar-nav ml-auto">
				<a class="nav-link" href="#"> <span class="sr-only">(current)</span>
				</a>
		

	 <%
							if (userLoggedIn) {
						%>
						
					<li class="nav-item dropdown" style="padding-left: 5px;"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">ABC</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.ABC_CTL%>"><i
							class="fas fa-user-circle"></i>Add Member</a> <a class="dropdown-item"
							href="<%=ORSView.ABC_LIST_CTL%>"><i
							class="fas fa-user-friends"></i>Member List</a>
					</div></li>
						<%
							} 
						%> --%>


























</body>
</html>