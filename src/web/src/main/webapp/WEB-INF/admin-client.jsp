<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<title>Gestionnaire de Licence - Panel</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/common/dark-mode.css"
	rel="stylesheet">

</head>

<body class="bg-light d-flex h-100">

	<%= Utils.ErrorMsg.printErrorMsg(request) %>
	
	<div class="d-flex flex-column flex-shrink-0 p-3 text-black bg-white"
		style="width: 15%; position: fixed; height: 100%;">
		<a href="/"
			class="d-flex align-items-center mb-3 mb-md-0 me-md-auto nav-link text-dark text-decoration-none">
			<span class="fs-4">Gestionnaire de Licence</span>
		</a>

		<hr>

		<ul class="nav nav-pills flex-column mb-auto">
			<li><a href="<%=request.getContextPath()%>/admin/notification" class="nav-link position-relative">
					Notifications 
				<% int size = (Integer)request.getAttribute("notification-list-size"); %>
				<span
					class="position-absolute translate-middle badge rounded-pill bg-danger"
					style="left: 48% !important; top: 20% !important;">
						<%=size%><%=size > 99 ? "+" : ""%> <span class="visually-hidden">notifications</span>
				</span> 
			</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/client" class="nav-link active"> Clients </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/licence" class="nav-link"> Licences </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/software" class="nav-link"> Logiciels </a></li>
		</ul>

		<hr>

		<div class="form-check form-switch">
			<input type="checkbox" class="form-check-input" id="darkSwitch">
		</div>
		<script src="<%=request.getContextPath()%>/common/dark-mode-switch.js"></script>
	</div>


	<main style="padding-left: 15%; width: 100%;">
		<section class="jumbotron text-center">
			<div class="container">
				<h1 class="jumbotron-heading">Tableau des clients</h1>
			</div>
		</section>

	</main>

</body>
</html>