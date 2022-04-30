<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Software"%>
<%@ page import="model.Licence"%>
<%@ page import="model.Client"%>

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

	<%=Utils.ErrorMsg.printErrorMsg(request)%>

	<div class="d-flex flex-column flex-shrink-0 p-3 text-black bg-white"
		style="width: 15%; position: fixed; height: 100%;">
		<a href="/"
			class="d-flex align-items-center mb-3 mb-md-0 me-md-auto nav-link text-dark text-decoration-none">
			<span class="fs-4">Gestionnaire de Licence</span>
		</a>

		<hr>

		<ul class="nav nav-pills flex-column mb-auto">
			<li><a href="<%=request.getContextPath()%>/admin/notification"
				class="nav-link position-relative"> Notifications <%
			int size = (Integer) request.getAttribute("notification-list-size");
			%> <span
					class="position-absolute translate-middle badge rounded-pill bg-danger"
					style="left: 48% !important; top: 20% !important;"> <%=size%><%=size > 99 ? "+" : ""%>
						<span class="visually-hidden">notifications</span>
				</span>
			</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/client"
				class="nav-link"> Clients </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/licence"
				class="nav-link"> Licences </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/software"
				class="nav-link"> Logiciels </a></li>
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
				<h1 class="jumbotron-heading">Création d'un nouveau logiciel</h1>
			</div>
		</section>

		<div class="container" style="padding-top: 4%">
			<div class="card">

				<div class="card-header">
					<div class="row">
						<div class="col-sm-10 align-self-center">
							<h4 class="font-weight-normal">Informations du nouveau
								logiciel</h4>
						</div>

					</div>
				</div>

				<div class="card-body">

					<form class="row g-3" method="POST" enctype="multipart/form-data">

						<div class="col-6">
							<label for="software-name" class="form-label">Nom du
								Logiciel</label> <input type="text" class="form-control" required=""
								id="software-name" name="software-name"
								placeholder="placeholder.exe">
						</div>

						<div class="col-6">
							<label for="software-desc" class="form-label">Description
								du logiciel</label> <input type="text" class="form-control"
								id="software-desc" name="software-desc" required=""
								placeholder="Logiciel fontionnant sur un ordinateur">
						</div>

						<div class="col-4">
							<label for="price" class="form-label">Prix du logiciel <span
								class="text-muted">(Par An)</span>
							</label>
							<div class="input-group mb-3">
								<input type="text" id="price" name="price" required=""
									placeholder="100" class="form-control" aria-label="Amount">
								<span class="input-group-text">$</span>
							</div>
						</div>

						<div class="col-4">
							<label for="price-multiplier" class="form-label">Multiplicateur
								de prix</label> <input type="text" class="form-control"
								id="price-multiplier" name="price-multiplier" required=""
								placeholder="1">
						</div>

						<div class="col-4">
							<label for="icon-file" class="form-label">Icone du
								logiciel <span class="text-muted">(Optionel)</span>
							</label> <input class="form-control" type="file" id="icon-file"
								name="icon-file">
						</div>

						<div class="text-center">
							<button id="action" type="submit" class="btn btn-success btn-yes">Ajouter</button>
						</div>

					</form>
				</div>
			</div>
		</div>
	</main>

</body>
</html>