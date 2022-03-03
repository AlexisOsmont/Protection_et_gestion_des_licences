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

	<div class="container d-flex p-3 mx-auto w-100 flex-column">
		<header class="mb-auto">
			<div class="float-md-start fw-bold fs-5">Projet - Gestionnaire
				de Licence</div>
			<nav class="nav justify-content-center float-md-end">
				<a class="nav-link" href="<%=request.getContextPath()%>/home">Accueil</a>
				<a class="nav-link active"
					href="<%=request.getContextPath()%>/login">Connexion</a> <a
					class="nav-link" href="<%=request.getContextPath()%>/register">Inscription</a>
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
					<h1 class="fw-bold mb-4">Connexion</h1>

					<form method="POST" action="login">
						<div class="mb-3">
							<label for="email">Adresse Mail</label> <input type="email" id="email"
								name="email" class="form-control " autocomplete="off"
								autocapitalize="none" autocorrect="off" required="" value="">
							<p class="invalid-feedback d-block"></p>
						</div>

						<div class="mb-3">
							<label for="password">Mot de passe</label> <input type="password"
								class="form-control" id="password" name="password" required="">
							<p class="invalid-feedback d-block"></p>
						</div>
						<div class="mb-3">
							<button class="btn btn-custom btn-secondary" type="submit"
								name="action" value="login">
								<span class="btn-ripple-container">
									<span class="btn-ripple" style="top: 0px; left: 0px;"></span>
								</span>
								<span class="btn-custom-children" style="background: none">Valider</span>
							</button>
							<p class="mt-5 mb-3 text-muted">
								Vous n'avez pas de compte? <a
									href="<%=request.getContextPath()%>/register">Créez en un</a>
							</p>
						</div>
					</form>
				</div>
			</div>
		</div>


		<footer class="text-center mt-auto">
			<p>&copy; Projet de M1-SSI 2022</p>
		</footer>
	</div>
</body>
</html>