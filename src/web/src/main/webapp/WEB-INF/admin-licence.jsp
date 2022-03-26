<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Licence" %>
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
			%>
					<span
					class="position-absolute translate-middle badge rounded-pill bg-danger"
					style="left: 48% !important; top: 20% !important;"> <%=size%><%=size > 99 ? "+" : ""%>
						<span class="visually-hidden">notifications</span>
				</span>
			</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/client"
				class="nav-link"> Clients </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/licence"
				class="nav-link active"> Licences </a></li>
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
				<h1 class="jumbotron-heading">Tableau des licences</h1>
				
				<table style="margin-top: 5%;" class="table align-middle">
					<thead class="thead-dark">
						<tr>
							<th scope="col">ID</th>
							<th scope="col">Nom du logiciel</th>
							<th scope="col">Client</th>
							<th scope="col">Status</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<List<String>> li = (List<List<String>>) request.getAttribute("array-content");
						for (List<String> tuple : li) {
							int licenceId = Integer.valueOf(tuple.get(0));
							int status = Integer.valueOf(tuple.get(3));
						%>
						
							<tr>
								<th scope="row"><%=licenceId %></th>
								<td><%=tuple.get(1)%></td>
								<td><%=tuple.get(2)%></td>
								<td>
									<%
										if (status == Licence.Status.PENDING.ordinal()) {
										%>
											<span class="btn btn-info my-2 disabled"><%= Licence.Status.PENDING.toString()%></span>
										<% 
										} else if (status == Licence.Status.ACTIVATED.ordinal()) {
										%>
											<span class="btn btn-success my-2 disabled"><%= Licence.Status.ACTIVATED.toString()%></span>
										<% 
										} else if (status == Licence.Status.EXPIRED.ordinal()) {
										%>
											<span class="btn btn-warning my-2 disabled"><%= Licence.Status.EXPIRED.toString()%></span>
										<% 
										}
									
									%>
								</td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		</section>

	</main>

</body>
</html>