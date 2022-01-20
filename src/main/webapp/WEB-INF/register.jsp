<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Controller.ControllerCommon" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
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

.form-signin input {
	margin-bottom: 20px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
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
</style>
</head>

<body class="d-flex h-100 text-center text-white bg-dark bg-darkmode">
	
	<%= ControllerCommon.printErrorMsg(request) %>
	
	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
		<header class="mb-auto">
			<div>
				<h3 class="float-md-start mb-0">Plateforme de signalement</h3>
				<nav class="nav nav-masthead justify-content-center float-md-end">
					<a class="nav-link" href="<%=request.getContextPath()%>/home">Accueil</a>
					<a class="nav-link" href="<%=request.getContextPath()%>/login">Connexion</a> 
					<a class="nav-link active" aria-current="page"
						href="<%=request.getContextPath()%>/register">Inscription</a>
				</nav>
			</div>
		</header>

		<main class="form-signin">
			<form method="GET" action="register">
				<h1 class="h3 mb-3 fw-normal">S'inscrire sur la plateforme</h1>
				<div class="form-floating text-left">
					<label for="username">Identifiant</label> <input type="text"
						class="form-control input-darkmode" name="username" id="username"
						placeholder="username">
				</div>
				<div class="form-floating text-left">
					<label for="email">Email</label> <input type="email"
						class="form-control input-darkmode" name="email" id="email"
						placeholder="name@example.com">
				</div>
				<div class="form-floating text-left">
					<label for="password">Mot de Passe</label> <input type="password"
						class="form-control input-darkmode" name="password" id="password"
						placeholder="Password">
				</div>
				<button
					class="w-100 btn btn-lg btn-secondary fw-bold border-white bg-white"
					type="submit" name="action" value="register">Valider</button>
				<p class="mt-5 mb-3 text-muted">
					Vous avez déjà un compte? <a
						href="<%=request.getContextPath()%>/login">Connectez vous</a>
				</p>
			</form>
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
