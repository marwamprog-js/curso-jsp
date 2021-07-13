<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Curso JSP</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<style type="text/css">
.form-container {
	width: 50%;
	margin: 0 auto;
}

.msg {
	color: #842029;
    background-color: #f8d7da;
    border-color: #f5c2c7;
}

</style>

</head>
<body>

	<div class="container">
		<h1 class="text-center mt-3">Curso JSP - Tela de Login</h1>

		<hr>

		<p class="text-center msg mt-5">${msg}</p>

		<div class="form-container mt-5">
			<form action="<%= request.getContextPath() %>/ServletLogin" method="post">
				<input type="hidden" value="<%=request.getParameter("url")%>"
					name="url">

				<div class="row">
					<div class="mb-3 col-md-12">
						<label for="login" class="form-label">Login</label> <input
							type="text" class="form-control" id="login" name="login">
					</div>
					<div class="mb-3 col-md-12">
						<label for="senha" class="form-label">Senha</label> <input
							type="Password" class="form-control" id="senha" name="senha">
					</div>
				</div>

				
				<input type="submit" class="btn btn-primary form-control"  value="Logar">
			</form>
		</div>
		

	</div>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>