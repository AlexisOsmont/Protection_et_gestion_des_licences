<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Controller.ControllerCommon"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link href="<%=request.getContextPath()%>/common/bootstrap.min.css"
	rel="stylesheet">
<style>
/*
	 * Globals                     
	 */

/* Custom default button */
.btn-secondary, .btn-secondary:hover, .btn-secondary:focus {
	color: #333;
	text-shadow: none; /* Prevent inheritance from `body` */
}

html 
body {
	height: 100%;
	font-size: .875rem;
	color: #fff !important;
}

.feather {
	width: 16px;
	height: 16px;
	vertical-align: text-bottom;
}

.ms-sm-auto {
	margin-left: auto !important;
}

/*
 * Sidebar
 */
.sidebar {
	position: fixed;
	top: 0;
	/* rtl:raw:
  right: 0;
  */
	bottom: 0;
	/* rtl:remove */
	left: 0;
	z-index: 100; /* Behind the navbar */
	padding: 48px 0 0; /* Height of navbar */
	box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
}

@media ( max-width : 767.98px) {
	.sidebar {
		top: 5rem;
	}
}

.sidebar-sticky {
	position: relative;
	top: 0;
	height: calc(100vh - 48px);
	padding-top: .5rem;
	overflow-x: hidden;
	overflow-y: auto;
	/* Scrollable contents if viewport is shorter than content. */
}

.sidebar .nav-link {
	font-weight: 500;
	color: #333;
}

.sidebar .nav-link .feather {
	margin-right: 4px;
	color: #727272;
}

.sidebar .nav-link.active {
	color: #2470dc;
}

.sidebar .nav-link:hover .feather, .sidebar .nav-link.active .feather {
	color: inherit;
}

.sidebar-heading {
	font-size: .75rem;
	text-transform: uppercase;
}

/*
 * Navbar
 */
.navbar-brand {
	padding-top: .75rem;
	padding-bottom: .75rem;
	font-size: 1rem;
	background-color: rgba(0, 0, 0, .25);
	box-shadow: inset -1px 0 0 rgba(0, 0, 0, .25);
}

.navbar .navbar-toggler {
	top: .25rem;
	right: 1rem;
}

.navbar .form-control {
	padding: .75rem 1rem;
	border-width: 0;
	border-radius: 0;
}

.form-control-dark {
	color: #fff;
	background-color: rgba(255, 255, 255, .1);
	border-color: rgba(255, 255, 255, .1);
}

.form-control-dark:focus {
	border-color: transparent;
	box-shadow: 0 0 0 3px rgba(255, 255, 255, .25);
}

.col {
	padding-bottom: 15px;
}

.form-signin input {
	margin-bottom: 20px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.input-darkmode {
	background-color: #111111;
	border-color: #000;
	color: #bbb;
}

/* use '*' to override existing form rule '*/
* .input-darkmode:focus {
	color: #bbb;
	background-color: #111111 !important;
	border-color: #ffffff3b;
	outline: 0;
	transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	box-shadow: 0 0 0 .2rem rgba(255, 255, 255, 0.1) !important;
}

.bg-darkmode {
	background-color: #222222 !important;
}

/*
 * Pagination
 */
.pagination>li>a {
	background-color: var(--dark);
	color: white;
	border-color: var(--secondary);
}

.pagination>li>a:focus, .pagination>li>a:hover, .pagination>li>span:focus,
	.pagination>li>span:hover {
	color: var(- -dark);
	background-color: var(--secondary);
	border-color: var(--secondary);
}

.pagination>.active>a {
	color: white;
	background-color: #252525;
	border: solid 1px var(--secondary);
}

.pagination>.active>a:hover {
	color: var(--dark);
	background-color: var(--secondary);
	border-color: var(--secondary);
}

/*
 * onliner button (used for close button because the one from
 *	bootstrap make it look weird)
 */
.oneliner-btn {
	font-size: 1.5rem;
	font-weight: 700;
	line-height: 1;
	color: #fff;
	text-shadow: 0 1px 0 #fff;
}
</style>
</head>

<body class="bg-dark bg-darkmode">

	<%=ControllerCommon.printErrorMsg(request)%>

	<header
		class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
		<a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="#"><%=ControllerCommon.printUsername(request)%></a>
		<button class="navbar-toggler position-absolute d-md-none collapsed"
			type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu"
			aria-controls="sidebarMenu" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="navbar-nav">
			<div class="nav-item text-nowrap">
				<a style="margin-right: 5px"
					class="btn btn-sm btn-secondary fw-bold border-white bg-white"
					href="<%=request.getContextPath()%>/PanelController?action=signout">Sign
					out</a>
			</div>
		</div>
	</header>

	<div class="container-fluid">
		<div class="row">