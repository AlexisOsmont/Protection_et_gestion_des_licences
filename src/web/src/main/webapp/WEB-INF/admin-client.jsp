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
				class="nav-link active"> Clients </a></li>
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
				<h1 class="jumbotron-heading">Tableau des clients</h1>

				<%
				String[][] filtersDesc = (String[][]) request.getAttribute("filters-description");
				%>
				<script>
					
					function getParameterByName(name) {
					    let match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
					    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
					}
					
					function setSelectedIndex(el, index){
						 el.selectedIndex = index;
						 el.dispatchEvent(new Event('change', { bubbles : true }));
					}
					
					// add the code in onload so we don't have to move the code down
					window.onload = function () {
						
						// set a handler when the value inside of the select changes
						document.getElementById('select-filter').onchange = function (event) {
						  let choice = event.target.value;
						  let node = document.getElementById('changeableForm');
						  // first clear all the children of the node 
						  while (node.firstChild != undefined) {
						    node.removeChild(node.lastChild);
						  }
						  label = undefined;
						  input = undefined;
						  
						  // for now there are only one filter available
						  switch (choice) {

						  	case "<%=filtersDesc[1][0]%>":
					  			// create client filter's input		
					  			label = document.createElement("label");
							    label.className = "me-2 sr-only";
							    label.textContent = "Mail du client";
	                  			label.setAttribute("for", "changeableForm-input");
	                  
							    input = document.createElement("input");
							    input.className = "form-control";
							    input.type = "email";
	                  			input.id = "changeableForm-input";
	                  			input.setAttribute("name", "<%=filtersDesc[1][1]%>");
						      	break;
						    
							default:
								break;
							}
							// check if we have something to append
							if (label != undefined) {
								node.appendChild(label);
								node.appendChild(input);
							}
						}
						// then trigger the handler so we update the last selected filter
						
						let select = document.getElementById('select-filter');
						let lastSelectedIndex = Math.max(
							[...select.options] // his options
							.map(function (x) { return x.value})
							.indexOf(getParameterByName('filter')), // last select filter 
							0 // in case if no filter were selected
						);
						setSelectedIndex(select, lastSelectedIndex);
						
						let node = document.getElementById('changeableForm'); 
						let arg = undefined;
						
						switch (select.value) {
						  	case "<%=filtersDesc[1][0]%>":
								// retrieve the argument in the url
								arg = getParameterByName("<%=filtersDesc[1][1]%>");
						  		node.children[1].value = arg;
						      	break;
							default:
								break;
						}
						
						// the last thing to do is to set the pagination
						pageElem = document.getElementById('page-number');
						pageNumber = pageElem.value * 1;
						items = [...document.getElementById('pagination').children];
						count = document.getElementsByTagName('tbody')[0].children.length;
						formElem = document.getElementById('table-filter');
						
						items[0].className = "page-item" + (pageNumber == 0 ? " disabled" : "");
						if (pageNumber != 0) {
							// add the onclick if the item isn't disabled
							items[0].onclick = function (event) {
								pageElem.value = 0;
								formElem.submit();
							}	
						}
						
						items[1].className = "page-item" + (pageNumber == 0 ? " active" : "");
						items[1].children[0].textContent = (pageNumber == 0 ? 1 : pageNumber);
						items[1].onclick = function (event) {
							pageElem.value = Math.max(pageNumber - 1, 0);
							formElem.submit();
						}
						
						items[2].className = "page-item" + (pageNumber != 0 ? " active" : "");
						items[2].children[0].textContent = (pageNumber == 0 ? 2 : pageNumber + 1);
						items[2].onclick = function (event) {
							pageElem.value = pageNumber + (pageNumber == 0);
							formElem.submit();
						}
						
						items[3].className = "page-item" + 
							(count != 10 && pageNumber > 1 ? " active" : 
							(count != 10 && pageNumber == 1 ? " disabled" : ""));
						
						items[3].children[0].textContent = (pageNumber < 2 ? 3 : (count != 10 ? pageNumber : pageNumber + 1));
						
						if (count == 10) {
							// add the onclick if the item isn't disabled
							items[3].onclick = function (event) {
								pageElem.value = pageNumber + 1 + (pageNumber == 0);
								formElem.submit();
							}
						}
						
						items[4].className = "page-item" + (count != 10 ? " disabled" : "");
						// no onclick for now 

					}
				</script>

				<form id="table-filter" style="margin-top: 5%;" class="form-inline"
					method="GET" action="/admin/client">

					<div class="form-group mb-2">
						<label class="me-2" for="select-filter">Filtres</label> <select
							name="filter" class="form-control" id="select-filter">
							<%
							for (String[] elem : filtersDesc) {
							%>
							<option value="<%=elem[0]%>"><%=elem[2]%></option>
							<%
							}
							%>
						</select>
					</div>

					<input id="page-number" type="hidden" name="page"
						value="<%=request.getAttribute("current-page")%>">

					<div id="changeableForm" class="form-group mx-sm-3 mb-2"></div>

					<button style="margin-bottom: 10px;" type="submit" name="action"
						value="apply" class="btn btn-secondary">Appliquer</button>

				</form>

				<%
				List<List<String>> li = (List<List<String>>) request.getAttribute("array-content");
				if (li == null) {
					// show a message indicating that now rows where correpond to 
					// to the filters settings
				%>
				<div style="margin-top: 1%;" class="alert alert-primary"
					role="alert">
					Une erreur dans les filtres que vous avez choisis nous empechent
					d'afficher correctement cette page, essayez avec <a
						href="<%=request.getContextPath()%>/admin/licence"
						class="alert-link">ceci</a>
				</div>
				<%
				} else {
				%>

				<table style="margin-top: 1%;"
					class="table align-middle table-hover table-sm">
					<thead class="thead-dark">
						<tr>
							<th scope="col">ID</th>
							<th scope="col">Nom d'utilisateur</th>
							<th scope="col">Email</th>
						</tr>
					</thead>
					<tbody>

						<%
						// print the list of all the information we got
						for (List<String> tuple : li) {
							int clientId = Integer.valueOf(tuple.get(0));
						%>

						<!-- @TMP set the height of each row to match the one of the admin/licence -->
						<tr style="height: 54px;">
							<th scope="row"><%=clientId%></th>
							<td><%=tuple.get(1)%></td>
							<td><%=tuple.get(2)%></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<nav aria-label="pagination">
					<ul id="pagination" class="pagination justify-content-end">
						<li class="page-item disabled"><a class="page-link" href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>

						<li class="page-item active"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>

						<li class="page-item"><a class="page-link" href="#"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</ul>
				</nav>

				<%
				}
				%>
			</div>
		</section>

	</main>

</body>
</html>