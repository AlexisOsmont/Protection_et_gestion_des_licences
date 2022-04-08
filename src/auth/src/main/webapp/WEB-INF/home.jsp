<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Gestionnaire de Licence - Login</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/common/dark-mode.css"
	rel="stylesheet">
</head>

<body class="bg-white d-flex h-100">

	<%	Boolean logged = (Boolean) session.getAttribute("logged");
		if (logged == null) {
			logged = false;
		}
	%>

	<div class="container d-flex p-3 mx-auto w-100 flex-column">
		<header class="mb-auto">
			<div class="float-md-start fw-bold fs-5">Projet - Gestionnaire
				de Licence</div>
			<nav class="nav justify-content-center float-md-end">
				<a class="nav-link" href="<%=request.getContextPath()%>/home">Accueil</a>
				<% if (!logged) { %>
					<a class="nav-link active" href="<%=request.getContextPath()%>/login">Connexion</a> 
					<a class="nav-link" href="<%=request.getContextPath()%>/register">Inscription</a>
				<% }  else { %>
				    <a class="nav-link active" href="<%=request.getContextPath()%>/login?disconnect=1">Déconnexion</a>
			    <%} %>
				
				<div class="nav-link">
					<div class="form-check form-switch">
						<input type="checkbox" class="form-check-input" id="darkSwitch">
					</div>
					<script
						src="<%=request.getContextPath()%>/common/dark-mode-switch.js"></script>

				</div>
			</nav>
		</header>

		<div class="d-grid place-items-center h-100"
			style="position: relative;">
			<div class="card border-0 col-sm-9 col-lg-7">
				<div class="card-body" style="padding: 30% 1rem">
					
					<% if (logged) { %>
						<h1 class="fw-bold mb-4">Bienvenue <%= session.getAttribute("username")%> !</h1>
						<p>Votre adresse mail est  : <%= session.getAttribute("mail")%> </p>
					<%} else {%>
						<h1 class="fw-bold mb-4">Bienvenue !</h1>
						<p>Vous pouvez vous connecter afin d'accéder à votre compte.</p>
					<%}%>
					
				</div>
			</div>
		</div>


		<footer class="text-center mt-auto">
			<p>&copy; Projet de M1-SSI 2022</p>
		</footer>
	</div>
</body>
</html>