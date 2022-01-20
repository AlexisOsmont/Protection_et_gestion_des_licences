<%@include file="../controlPanelHeader.jsp"%>

<nav id="sidebarMenu"
	class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
	<div class="position-sticky pt-3">

		<!-- Administrator -->
		<h6
			class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
			<span>Resp. Maintenance</span>
		</h6>

		<ul class="nav flex-column mb-2">
			<li class="nav-item"><a class="nav-link active"
				href="<%=request.getContextPath()%>/PanelController?action=list">Lister</a></li>
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/PanelController?action=remove">Supprimer</a></li>
		</ul>

	</div>
</nav>

<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

	<div
		class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
		<h2>Liste des Responsables de Maintenances</h2>
	</div>
	
	<%= request.getAttribute("maintainer-list") %>

</main>

<%@include file="../controlPanelFooter.jsp"%>