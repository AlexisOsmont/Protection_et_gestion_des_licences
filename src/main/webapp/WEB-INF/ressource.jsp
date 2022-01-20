<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Controller.ControllerCommon" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<style>
/*
	 * Globals                     
	 */

/* Custom default button */
.btn-secondary, .btn-secondary:hover, .btn-secondary:focus {
	color: #333;
	text-shadow: none; /* Prevent inheritance from `body` */
}

/*
	 * Base structure
	 */
html {
	height: 100%;
}

body {
	text-shadow: 0 .05rem .1rem rgba(0, 0, 0, .5);
	box-shadow: inset 0 0 5rem rgba(0, 0, 0, .5);
}

.cover-container {
	max-width: 42em;
}

/*
	 * Header
	 */
.nav-masthead .nav-link {
	padding: .25rem 0;
	font-weight: 700;
	color: rgba(255, 255, 255, .5);
	background-color: transparent;
	border-bottom: .25rem solid transparent;
}

.nav-masthead .nav-link:hover, .nav-masthead .nav-link:focus {
	border-bottom-color: rgba(255, 255, 255, .25);
}

.nav-masthead .nav-link+.nav-link {
	margin-left: 1rem;
}

.nav-masthead .active {
	color: #fff;
	border-bottom-color: #fff;
}

.input-darkmode {
	background-color: #111111;
	border-color: #000;
	color: #bbb;
}

/* use '*' to override existing form rule '*/
* .input-darkmode:focus {
	color: #bbb;
	background-color: #111111 !important;
	border-color: #ffffff3b;
	outline: 0;
	transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	box-shadow: 0 0 0 .2rem rgba(255, 255, 255, 0.1) !important;
}

.bg-darkmode {
	background-color: #222222 !important;
}

.h-10 {
	height: 10% !important
}
</style>
</head>
<body class="d-flex h-100 text-center text-white bg-dark bg-darkmode">

	<script>
		function toggleDisplay(id) {
			let elem = document.getElementById(id);
			elem.style.display = elem.style.display == 'none' ? 'block'
					: 'none';
		}
	</script>
	
	<%= ControllerCommon.printErrorMsg(request) %>
	
	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
		<header class="mb-auto">
			<div>
				<h3 class="float-md-start mb-0">Plateforme de signalement</h3>
				<nav class="nav nav-masthead justify-content-center float-md-end">
					<a class="nav-link active" aria-current="page"
						href="<%=request.getContextPath()%>/home">Accueil</a> <a
						class="nav-link" href="<%=request.getContextPath()%>/login">Connexion</a> 
						<a class="nav-link"
						href="<%=request.getContextPath()%>/register">Inscription</a>
				</nav>
			</div>
		</header>

		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<h2 class="text-center h-10" style="padding-bottom: 10%">Déclarer
						un Incident</h2>
					<% Boolean value = (Boolean) request.getAttribute("disp-info");
					   if (value != null && value) { %>
					<p>
						Cette page est un example de formulaire permettant de déclarer une anomalie,
						vous pouvez y accéder via l'url ou en scannant un QR-code.<br>
						Vous pouvez choisir une anomalie déjà existante ou en créer un nouvelle en 
						cliquant sur le bouton '+'.
					</p>
					
					<%} %>

					<h4 class="text-center h-10">Ressource N°<%= request.getAttribute("res-id") %></h4>

					<form method="GET" action="ressource">
						<dl class="row text-left">
							<dt class="col-sm-3">Description</dt>
							<dd class="col-sm-9"><%= request.getAttribute("res-description") %></dd>

							<dt class="col-sm-3">Localisation</dt>
							<dd class="col-sm-9"><%= request.getAttribute("res-location") %></dd>

							<dt class="col-sm-3">Responsable de Maintenance</dt>
							<dd class="col-sm-9"><%= request.getAttribute("res-maintainer") %></dd>

							<dt class="col-sm-3">Anomalies connues</dt>
							<dd class="col-sm-9">

								<div class="input-group">
									<%= request.getAttribute("res-anomaly-list") %>
									
									<div class="input-group-append">
										<button class="btn btn-secondary" type="button"
											onclick="toggleDisplay('anomaly')">+</button>
									</div>
								</div>

							</dd>
						</dl>

						<div id="anomaly" style="display: none; padding-bottom: 5%;">
							<div class="form-floating text-left">
								<label for="anomaly-desc">Description</label> <input type="text"
									class="form-control input-darkmode" id="anomaly-desc"
									name="anomaly-desc" placeholder="description de l'anomalie">
							</div>
						</div>

						<button type="submit" name="action" value="ressource"
							class="btn btn-lg btn-secondary fw-bold border-white bg-white">Envoyer</button>
					</form>
				</div>
			</div>
		</div>

		<footer class="mt-auto text-white-50">
			<p>
				Projet de LW1-M1INFO, by <a href="#" class="text-white">Sami &amp;
					Louka</a>.
			</p>
		</footer>
	</div>
</body>
</html>