<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
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

	<%= Utils.ErrorMsg.printErrorMsg(request) %>
	
	<div class="d-flex flex-column flex-shrink-0 p-3 text-black bg-white"
		style="width: 15%; position: fixed; height: 100%;">
		<a href="/"
			class="d-flex align-items-center mb-3 mb-md-0 me-md-auto nav-link text-dark text-decoration-none">
			<span class="fs-4">Gestionnaire de Licence</span>
		</a>

		<hr>

		<ul class="nav nav-pills flex-column mb-auto">
			<li><a href="#" class="nav-link active position-relative">
					Notifications <%
			List<List<String>> lu = (List<List<String>>) request.getAttribute("notification-list");
			int size = lu.size();
			if (size > 0) {%> 
				<span
					class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
						<%=size%><%=size > 99 ? "+" : ""%> <span class="visually-hidden">notifications</span>
				</span> 
			<%}%>
			</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/client" class="nav-link"> Clients </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/licence" class="nav-link"> Licences </a></li>
			<li><a href="<%=request.getContextPath()%>/admin/software" class="nav-link"> Logiciels </a></li>
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
				<h1 class="jumbotron-heading">Tableau de Notifications</h1>
				<p class="lead text-muted">Voici la liste de toutes les
					notifications en attente</p>
			</div>
		</section>

		<div class="album py-5 bg-light">
			<div class="container">
				<div class="row">

					<%
					List<List<String>> li = (List<List<String>>) request.getAttribute("notification-list");
					for (List<String> tuple : li) {
						int licenceId = Integer.valueOf(tuple.get(0));
					%>

					<div class="col-md-4"
						style="border: 3px solid transparent; border-radius: 5px;">

						<a href="/licence/<%=licenceId%>"> 

							<div class="card mb-4 box-shadow card-shadow-effect" style="width: 18rem;">
							 	<svg
									xmlns="http://www.w3.org/2000/svg" width="32" height="32"
									fill="currentColor" class="card-img bi bi-envelope" viewBox="0 0 16 16">
 									<path
										d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 
										1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 
										9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
								</svg>
								
							  	<div class="card-body">
							    	<p class="card-text">
							    		ID Licence: <%=licenceId%> <br>
							    		Logiciel: <%= tuple.get(1)%> <br>
							    		par: <%= tuple.get(2)%> <br>
							    	</p>
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