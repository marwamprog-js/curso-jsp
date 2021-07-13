<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

	<jsp:include page="theme-loader.jsp"></jsp:include>

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbar-main-menu.jsp"></jsp:include>

					<div class="pcoded-content">

						<jsp:include page="page-header.jsp"></jsp:include>

						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">

								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-md-12">
												<div class="card">
													<div class="card-header">
														<h5>Cadastro de Usuários</h5>
													</div>
													<div class="card-block">
														<form class="form-material" id="formUser"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" value="${ modelLogin.id }">
																<span class="form-bar"></span> <label
																	class="float-label">Id</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" value="${ modelLogin.nome }"
																	required="required"> <span class="form-bar"></span>
																<label class="float-label">Nome</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" value="${ modelLogin.email }"
																	required="required" autocomplete="off"> <span
																	class="form-bar"></span> <label class="float-label">Email
																	(exa@gmail.com)</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" value="${ modelLogin.login }"
																	required="required" autocomplete="off"> <span
																	class="form-bar"></span> <label class="float-label">Login</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" value="${ modelLogin.senha }"
																	required="required" autocomplete="off"> <span
																	class="form-bar"></span> <label class="float-label">Senha</label>
															</div>

															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Gravar</button>
															<button type="button"
																class="btn btn-danger waves-effect waves-light"
																onclick="criarDeleteComAjax();">Excluir</button>
															<button type="button" class="btn btn-secondary"
																data-toggle="modal" data-target="#usuarioModal">
																Pesquisar</button>
														</form>
														<div id="msg" class="alert-success mt-5">${msg}</div>
														
														
														<div style="height: 300px; overflow-y: scroll;">
															<table class="table" id="tabelaListaUsuarios">
																<thead>
																	<tr>
																		<th scope="col">#</th>
																		<th scope="col">Nome</th>
																		<th scope="col">Ver</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${usuarios}" var="usuario">
																		<tr>
																			<td><c:out value="${usuario.id}"></c:out></td>
																			<td><c:out value="${usuario.nome}"></c:out></td>
																			<td><a href="ServletUsuarioController?acao=buscarEditar&id=${usuario.id}" class="btn btn-success">Ver</a></td>
																		</tr>
																	</c:forEach>
																</tbody>																
															</table>
														</div>
														
													</div>

												</div>
											</div>
										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="scripts.jsp"></jsp:include>

	<!-- Modal -->
	<div class="modal fade" id="usuarioModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
						Usuário</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="Pesquisar por nome" aria-label="Pesquisa por nome"
							name="pesquisar" id="pesquisar" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-success" onclick="buscarUsuario();"
								type="button">Buscar</button>
						</div>
					</div>

					<div style="height: 300px; overflow-y: scroll">
						<table class="table" id="tabelaUsuario">
							<thead>
								<tr>
									<th scope="col">#</th>
									<th scope="col">Nome</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>						
					</div>

					<span id="totalResultado" class="mt-2"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		function buscarUsuario() {
			var pesquisa = document.getElementById('pesquisar').value;
			var urlAction = document.getElementById("formUser").action;

			if (pesquisa != null && pesquisa != '' && pesquisa.trim() != '') {
				$
						.ajax(
								{

									method : "get",
									url : urlAction,
									data : "nomeBusca=" + pesquisa
											+ "&acao=pesquisarUsuario",
									success : function(response) {

										var json = JSON.parse(response);

										$('#tabelaUsuario > tbody > tr')
												.remove();

										for (var p = 0; p < json.length; p++) {
											$('#tabelaUsuario > tbody')
													.append(
															'<tr><td>'
																	+ json[p].id
																	+ '</td><td>'
																	+ json[p].nome
																	+ '</td><td><button class="btn btn-info" onclick="verEditar('+json[p].id+')">Ver</button></td></tr>');
										}
										
										document.getElementById("totalResultado").textContent = 'Resultados: ' + json.length;
										

									}

								}).fail(
								function(xhr, status, errorThrown) {
									alert('Erro ao buscar usuário por nome: '
											+ xhr.responseText);
								});
			}
		}
		
		function verEditar(id) {
			
			var urlAction = document.getElementById("formUser").action;
			
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
		}

		function limparForm() {
			var elementos = document.getElementById('formUser').elements; /*Retorna ps elementos html dentro do form*/

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}

		}

		function criarDeleteComAjax() {

			if (confirm("Deseja realmente excluir?")) {
				var urlAction = document.getElementById("formUser").action;
				var idUser = document.getElementById("id").value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=deletarajax",
					success : function(response) {
						limparForm();
						document.getElementById('msg').textContent = response;
					}

				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por id: '
									+ xhr.responseText);
						});
			}

		}

		function criarDelete() {

			if (confirm("Deseja realmente excluir?")) {
				document.getElementById('formUser').method = "get";
				document.getElementById('acao').value = "deletar";
				document.getElementById('formUser').submit();
			}

		}
	</script>

</body>

</html>
