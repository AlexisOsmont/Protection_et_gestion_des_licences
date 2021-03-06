<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en" class="h-100">

<head>
<title>Gestionnaire de Licence - Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/common/dark-mode.css"
	rel="stylesheet">

</head>

<body class="bg-white d-flex h-100">

	<%= Utils.ErrorMsg.printErrorMsg(request) %>

	<div class="container d-flex p-3 mx-auto w-100 flex-column">
		<header class="mb-auto">
			<div class="float-md-start fw-bold fs-5">Projet - Gestionnaire
				de Licence</div>
			<nav class="nav justify-content-center float-md-end">
				<a class="nav-link active" href="<%=request.getContextPath()%>/home">Accueil</a>
				<a class="nav-link" href="<%=request.getContextPath()%>/login">Connexion</a>
				<a class="nav-link" href="<%=request.getContextPath()%>/register">Inscription</a>
				<div class="nav-link">
					<div class="form-check form-switch">
						<input type="checkbox" class="form-check-input" id="darkSwitch">
					</div>
					<script
						src="<%=request.getContextPath()%>/common/dark-mode-switch.js"></script>

				</div>
			</nav>
		</header>

		<main class="w-75" role="main">
			<h1>Bienvenue sur le site de Gestion de Licence</h1>
			<p class="lead">Ce site vous permet de vous créer un compte et d'acheter des 
				logiciels parmis une liste variés de produits, vous pourrez ensuite les 
				installer sur votre ordinateur ! Pour commencer, cliquez sur Inscription</p>
			<p>
				<a href="<%=request.getContextPath()%>/register" class="btn btn-lg btn-secondary"> C'est parti ! </a>
			</p>
		</main>

		<footer class="text-center mt-auto">
			<p>&copy; Projet de M1-SSI 2022</p>
		</footer>

	</div>
</body>

</html>