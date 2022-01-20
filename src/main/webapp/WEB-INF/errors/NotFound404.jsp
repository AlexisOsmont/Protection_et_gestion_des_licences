<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Not Found</title>
<style type="text/css">
*, :after, :before, html {
    box-sizing: border-box;
}

body {
    margin: 10px;
    background-color: #000084;
    color: #bbb;
    font-family: 'Courier New', monospace;
    font-size: 10px;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

@media (min-width: 600px) {
    body {
        font-size: 16px;
    }
}

@media (min-width: 900px) {
    body {
        font-size: 24px;
        font-weight: 400;
    }
}

.notfound {
    width: 70ch;
    height: 25ch;
    background-color: #000084;
}

.row {
    text-align: left;
}

.centered {
    text-align: center;
}

.inverted {
    background-color: #bbb;
    color: #000084;
}

.shadow {
    background-color: #000;
    color: #000084;
}

.blink {
    animation: blinkingText .8s infinite;
}

@keyframes blinkingText {
    0% {
        opacity: 0;
    }
    
    49% {
        opacity: 0;
    }
    
    50% {
        opacity: 1;
    }
}

</style>
</head>
<body>
	<script>
		window.onload = function () {
			window.onkeydown = function () {
				window.location.pathname = "<%= request.getContextPath() %>/home";
			}
		};
	</script>
	<div class="notfound">
		<div class="centered">
			<span class="inverted">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;
		</div>
		<div class="centered">
			<span class="inverted">&nbsp;4&nbsp;0&nbsp;4&nbsp;</span><span
				class="shadow">&nbsp;</span>
		</div>
		<div class="centered">
			<span class="inverted">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span
				class="shadow">&nbsp;</span>
		</div>
		<div class="centered">
			&nbsp;<span class="shadow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		</div>
		<div class="row">&nbsp;</div>
		<div class="row">A fatal exception 404 has occurred at
			C0DE:ABAD1DEA in 0xC0DEBA5E.</div>
		<div class="row">The current request will be terminated.</div>
		<div class="row">&nbsp;</div>
		<div class="row">* Press any key to return to the previous page.</div>
		<div class="row">* Press CTRL+ALT+DEL to restart your computer.
			You will</div>
		<div class="row">&nbsp;&nbsp;lose any unsaved information in all
			applications.</div>
		<div class="row">&nbsp;</div>
		<div class="centered">
			Press any key to continue...<span class="blink">&#9608;</span>
		</div>
	</div>
</body>
</html>