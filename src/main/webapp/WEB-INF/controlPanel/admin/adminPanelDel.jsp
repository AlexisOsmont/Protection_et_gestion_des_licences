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
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/PanelController?action=list">Lister</a></li>
			<li class="nav-item"><a class="nav-link active"
				href="<%=request.getContextPath()%>/PanelController?action=remove">Supprimer</a></li>
		</ul>

	</div>
</nav>

<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

	<div
		class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
		<h2>Supprimer un Responsable de Maintenances</h2>
	</div>

	<div class="container-fluid">
		<div
			class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center">
			<p style="font-size: large;">
				Afin de supprimer un responsable de maintance de l'application vous choisir un autre responsable 
				qui héritera (le chanceux) de toutes les ressources de celui-ci ainsi que de leurs anomalies. 
				Pour ce faire vous devez sélectionner dans le tableau de gauche, le responsable à supprimer 
				(de l'application seulement, la gestion des tueurs à gâges sera effectués dans la V2) puis selectionner
				son héritier dans le tableau de droite et cliquer sur 'Supprimer'. <br>
				Attention: vous ne pouvez pas choisir comme héritier le responsable que vous voulez supprimer 
				(par conséquent vous devez garder au moins un responsable)
				
			</p>
		</div>
		
		<form action="PanelController" method="GET">
			<div class="row">
				<div class="col-md-5">
					<%=request.getAttribute("maintainer-list-left")%>
				</div>

				<div class="col-md-2">
					<p>
						<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
							width="792px" height="792px" viewBox="0 0 792 792"
							style="enable-background: new 0 0 792 792; width: 64px; height: 64px; margin-top: 45%; margin-left: 40%"
							xml:space="preserve">
						<path style="fill:white"
								d="M578.035,306.001L272.033,612l-61.022-61.021l244.978-244.978L211.011,
							   61.022L272.033,0L578.035,306.001z M211.735,184.52  L90,306.255l120.701,
							   120.7l121.734-121.734L211.735,184.52z" />
					</svg>
					</p>
				</div>

				<div class="col-md-5">
					<%=request.getAttribute("maintainer-list-right")%>
				</div>

			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col">
					<button
						class="w-100 btn btn-lg btn-secondary fw-bold border-white bg-white"
						type="submit" name="action" value="remove">Supprimer</button>
				</div>
				<div class="col"></div>
				
			</div>
		</form>
	</div>

</main>

<%@include file="../controlPanelFooter.jsp"%>