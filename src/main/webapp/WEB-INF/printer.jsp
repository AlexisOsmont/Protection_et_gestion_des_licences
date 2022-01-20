<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Impression</title>
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<style>
/* use '*' to override existing form rule '*/
* .card {
	border: 3px solid rgba(0, 0, 0, .125);
	margin: 5px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		window.print();
	</script>

	<div class="album py-5">
		<div class="container">
			<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

				<%
				List<Integer> imgList = (List<Integer>) request.getAttribute("img-list");
				for (Integer id : imgList) {
				%>

				<div class='col'>
					<div class='card'>
						<div class='card-body' style="width: 105mm; height: 42mm;">
							<div class="row" style="margin-top: -15px;">
								<img src="<%=request.getContextPath() + "/ressource-printer?id=" + id%>">
								<div
									style="margin-top: 10px; position: absolute; margin-left: 120px;">
									<h2>Flashez-moi</h2>
									<p>Pour signaler un problème avec ce matériel</p>
								</div>
							</div>
							<p class='card-text'><%=request.getContextPath() + "/ressource?id=" + id%></p>
						</div>
					</div>
				</div>

				<%}%>
			</div>
		</div>
	</div>
</body>
</html>