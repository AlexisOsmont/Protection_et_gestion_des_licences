<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="d-flex flex-column flex-shrink-0 p-3 text-black bg-white"
		style="width: 15%; position: fixed; height: 100%;">
		<a href="/"
			class="d-flex align-items-center mb-3 mb-md-0 me-md-auto nav-link text-dark text-decoration-none">
			<span class="fs-4">Gestionnaire de Licence</span>
		</a>

		<hr>

		<ul class="nav nav-pills flex-column mb-auto">
			<li><a href="<%=request.getContextPath()%>/product-buy"
				class="nav-link active"> Achetez un logiciel </a></li>
			<li><a href="<%=request.getContextPath()%>/product-owned"
				class="nav-link"> Vos produits </a></li>
			<li><a href="#" class="nav-link"> Télécharger le logiciel
					d'activation </a></li>
		</ul>

		<hr>

		<div class="form-check form-switch">
			<input type="checkbox" class="form-check-input" id="darkSwitch">
		</div>
		<script src="<%=request.getContextPath()%>/common/dark-mode-switch.js"></script>
	</div>

	<main style="padding-left: 20%">
		<section class="jumbotron text-center">
			<div class="container">
				<h1 class="jumbotron-heading">Listes des logiciels disponibles</h1>
				<p class="lead text-muted">Voici tous les logiciels disponibles
					à l'achat</p>
				<div>
					<a href="#" class="btn btn-primary my-2">bouton inutile n°1</a> <a
						href="#" class="btn btn-secondary my-2">bouton inutile n°2</a> <input
						class="form-control" id="input" type="text"
						placeholder="Recherchez..">
				</div>
			</div>
		</section>


		<div class="album py-5 bg-light">
			<div class="container">
				<div class="row">

					<%
					for (int i = 0; i < 10; ++i) {
					%>


					<div class="col-md-4"
						style="border: 3px solid transparent; border-radius: 5px;">

						<a href="/product/<%=i%>">

							<div class="card mb-4 box-shadow card-shadow-effect">
								<img class="card-img-top"
									data-src="holder.js/100px225?theme=thumb&amp;bg=55595c&amp;fg=eceeef&amp;text=Thumbnail"
									alt="Thumbnail [100%x225]"
									style="height: 225px; width: 100%; display: block;"
									src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22348%22%20height%3D%22225%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20348%20225%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_17ef8899786%20text%20%7B%20fill%3A%23eceeef%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A17pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_17ef8899786%22%3E%3Crect%20width%3D%22348%22%20height%3D%22225%22%20fill%3D%22%2355595c%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22114.79166793823242%22%20y%3D%22122.1%22%3EThumbnail%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
									data-holder-rendered="true">
								<div class="card-body card-body-thin">
									<div class="card-body-top">
										<p class="card-text" style="margin-bottom: 0;">
											Nom du logiciel n°<%=i%></p>
										<span class="subtitle text-capitalize"
											style="position: relative; color: #989ea4"> <em>
												Description du logiciel</em>
										</span>
									</div>

									<div
										class="d-flex justify-content-between align-items-center card-body-bottom">
										<small style="color: #989ea4">prix en euros</small>
									</div>
								</div>
							</div>
						</a>
					</div>

					<%}%>

				</div>
			</div>
		</div>
	</main>

</body>
</html>