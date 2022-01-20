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
			<li class="nav-item"><a class="nav-link active"
				href="<%=request.getContextPath()%>/PanelController?action=list">Lister
			</a></li>
			<li class="nav-item"><a class="nav-link"
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
		<h2>Liste des Ressources</h2>
	</div>
		
	<%= request.getAttribute("ressource-list") %>	

</main>

<%@include file="../controlPanelFooter.jsp"%>