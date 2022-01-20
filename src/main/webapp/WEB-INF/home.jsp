<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Controller.ControllerCommon"%>

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

.bg-darkmode {
	background-color: #222222 !important;
}
</style>
</head>
<body class="d-flex h-100 text-center text-white bg-dark bg-darkmode">

	<%=ControllerCommon.printErrorMsg(request)%>

	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
		<header class="mb-auto">
			<div>
				<h3 class="float-md-start mb-0">Plateforme de signalement</h3>
				<nav class="nav nav-masthead justify-content-center float-md-end">
					<a class="nav-link active" aria-current="page"
						href="<%=request.getContextPath()%>/home">Accueil</a> <a
						class="nav-link" href="<%=request.getContextPath()%>/login">Connexion</a> <a class="nav-link"
						href="<%=request.getContextPath()%>/register">Inscription</a>
				</nav>
			</div>
		</header>

		<main class="px-3">
			<h1>Un problème avec notre matériel ?</h1>
			<p class="lead">Bien que cela reste impossible, en effet
				l'université débordant de moyen, tout à été fait afin que vous
				disposiez de matériel de qualité ! Toutefois, si jamais un problème
				survient n'hésitez pas à nous le déclarer.</p>

			<p class="lead" style="height: 100px">
				<a href="<%=request.getContextPath()%>/ressource"
					class="btn btn-lg btn-secondary fw-bold border-white bg-white">Déclarer
					un problème</a>
			</p>

			<div class="my-3 p-3 bg-dark rounded box-shadow">
				<h6 class="border-bottom border-gray pb-2 mb-0">Dernières
					Réparations Effectués</h6>

				<%=request.getAttribute("last-resolved-anomaly") == null ? 
						"Aucune Anomalie detectée" : request.getAttribute("last-resolved-anomaly")%>

			</div>
		</main>

		<footer class="mt-auto text-white-50">
			<p>
				Projet de LW1-M1INFO, by <a href="#" class="text-white">Sami &amp;
					Louka</a>.
			</p>
		</footer>
	</div>

</body>

</html>