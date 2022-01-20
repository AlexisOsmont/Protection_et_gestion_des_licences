<%@include file="../controlPanelHeader.jsp"%>

<nav id="sidebarMenu"
	class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
	<div class="position-sticky pt-3">

		<!-- Anomaly -->
		<h6
			class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
			<span>Anomalies</span>
		</h6>

		<ul class="nav flex-column">
			<li class="nav-item"><a
				href="<%=request.getContextPath()%>/PanelController?action=home"
				class="nav-link"> Général </a></li>
		</ul>

		<!-- Ressource -->
		<h6
			class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
			<span>Ressources</span>
		</h6>

		<ul class="nav flex-column mb-2">
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/PanelController?action=list">Lister
			</a></li>
			<li class="nav-item"><a class="nav-link active"
				href="<%=request.getContextPath()%>/PanelController?action=add">Ajouter
			</a></li>
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/PanelController?action=print">Imprimer
			</a></li>
		</ul>
	</div>
</nav>

<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

	<div
		class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
		<h2>Ajouter une Ressources</h2>
	</div>

	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
		<main class="form-signin">
			<form action="PanelController" method="GET">
				<h2 class="h4 mb-3 fw-normal">Pour ajouter une nouvelle
					ressource, renseigner les champs suivants et cliquez sur ajouter</h2>

				<div class="form-floating text-left">
					<label for="localisation">Localisation de la Ressource</label> <input
						type="text" class="form-control input-darkmode" id="localisation"
						name="localisation" placeholder="ex: U2.2.39">
				</div>
				<div class="form-floating text-left" style="padding-bottom: 15px">
					<label for="description">Description de la Ressource</label>
					<textarea class="form-control input-darkmode" id="description"
						placeholder="ex: Video Projecteur de la Marque X, modèle Y, couleur rouge, ..."
						rows="3" name="description"></textarea>
				</div>

				<button
					class="w-75 btn btn-lg btn-secondary fw-bold border-white bg-white" style="margin-left: 10%"
					type="submit" name="action" value="addAnomaly">Ajouter</button>
			</form>
		</main>
	</div>
</main>

<%@include file="../controlPanelFooter.jsp"%>