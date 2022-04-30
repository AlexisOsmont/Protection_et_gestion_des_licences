<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Software"%>
<%@ page import="model.Licence"%>
<%@ page import="model.Client"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

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

	<%
	Licence licence = (Licence) request.getAttribute("licence");
	Software software = (Software) request.getAttribute("software");
	Client client = (Client) request.getAttribute("client");

	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	int status = licence.getStatus();
	%>

	<main style="padding-left: 15%; width: 100%;">
		<section class="jumbotron text-center">
			<div class="container">
				<h1 class="jumbotron-heading">
					Detail de la licence #<%=licence.getId()%></h1>
			</div>
		</section>

		<div class="container" style="padding-top: 4%">
			<div class="card">

				<div class="card-header">
					<div class="row">
						<div class="col-sm-10 align-self-center">
							<h4 class="font-weight-normal">Récapitulatif de la demande</h4>
						</div>
						<div class="col-sm-2">
							<%
							if (status == Licence.Status.PENDING.ordinal()) {
							%>
							<span class="btn btn-info disabled"><%=licence.getStatusString()%></span>
							<%
							} else if (status == Licence.Status.ACTIVATED.ordinal()) {
							%>
							<span class="btn btn-success disabled"><%=licence.getStatusString()%></span>
							<%
							} else if (status == Licence.Status.EXPIRED.ordinal()) {
							%>
							<span class="btn btn-warning disabled"><%=licence.getStatusString()%></span>
							<%
							}
							%>
						</div>
					</div>
				</div>

				<div class="card-body">

					<div class="row g-3">

						<div class="col-6">
							<label for="username" class="form-label">Pseudo</label>
							<div class="input-group has-validation">
								<span class="input-group-text">@</span> <input type="text"
									class="form-control" id="username" placeholder="Username"
									readonly="" value="<%=client.getUsername()%>">
							</div>
						</div>

						<div class="col-6">
							<label for="email" class="form-label">Email</label> <input
								type="email" class="form-control" id="email"
								placeholder="you@example.com" readonly=""
								value="<%=client.getEmail()%>">
						</div>

						<div class="col-6">
							<label for="software" class="form-label">Logiciel</label> <input
								type="text" class="form-control" id="software"
								placeholder="placeholder.exe" readonly=""
								value="<%=software.getName()%>">
						</div>

						<div class="col-6">
							<label for="software-desc" class="form-label">Description
								du logiciel</label> <input type="text" class="form-control"
								id="software-desc" placeholder="usefull" readonly=""
								value="<%=software.getDescription()%>">
						</div>

						<div class="col-md-6">
							<label for="date-start" class="form-label">Début
								d'utilisation</label> <input type="date" class="form-control"
								id="date-start" readonly=""
								value="<%=sdf.format(calendar.getTime())%>">
						</div>

						<div class="col-md-6">
							<label for="date-end" class="form-label">Fin
								d'utilisation</label> <input type="date" class="form-control"
								id="date-end" readonly=""
								value="<%=sdf.format(licence.getValidity())%>">

						</div>

						<div class="text-center">
							<%
							if (status == Licence.Status.PENDING.ordinal()) {
							%>

							<a
								href="?action=updateStatus&statusName=<%=Licence.Status.ACTIVATED.toString()%>"
								class="btn btn-success btn-yes">Accepter</a> <a
								href="?action=updateStatus&statusName=<%=Licence.Status.EXPIRED.toString()%>"
								class="btn btn-danger btn-no">Refuser</a>

							<%
							} else if (status == Licence.Status.ACTIVATED.ordinal()) {
							%>
							<p class="text-muted">@TODO</p>
							<%
							} else if (status == Licence.Status.EXPIRED.ordinal()) {
							%>
							<p class="text-muted">@TODO</p>
							<%
							}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

</body>
</html>