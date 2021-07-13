package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;


@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DAOUsuarioRepository dao = new DAOUsuarioRepository();


	public ServletUsuarioController() {

	}


	/*
	 * GET
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String msg = "";
			String idUser = "";
			String acao = request.getParameter("acao");

			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				idUser = request.getParameter("id");
				if(idUser != "") {
					dao.deletarUser(idUser);
					msg = "Excluido com sucesso!";	
				} else {
					msg = "ID não encontrado! Favor informar um válido para excluir registro";	
				}
				
				List<ModelLogin> usuarios = dao.buscarUsuario();
				request.setAttribute("usuarios", usuarios);
				
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				idUser = request.getParameter("id");
				if(idUser != "") {
					dao.deletarUser(idUser);
					msg = "Excluido com sucesso!";	
				} else {
					msg = "ID não encontrado! Favor informar um válido para excluir registro";	
				}
				
				response.getWriter().write(msg);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pesquisarUsuario")) {
				
				msg = "";
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJson = dao.buscarUsuarioPorNome(nomeBusca);
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJson);
				response.getWriter().write(json);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				
				String id = request.getParameter("id");
				
				ModelLogin usuario = dao.consultaUsuarioPorId(id);
				
				if(usuario == null) {
					msg = "Erro ao busca usuário. Tente novamente.";
				} else {
					msg = "Usuário em edição";
					request.setAttribute("modelLogin", usuario);
				}
				
				List<ModelLogin> usuarios = dao.buscarUsuario();
				request.setAttribute("usuarios", usuarios);
				
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
							
				List<ModelLogin> usuarios = dao.buscarUsuario();				
				
				request.setAttribute("msg", msg);
				request.setAttribute("usuarios", usuarios);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else {
				List<ModelLogin> usuarios = dao.buscarUsuario();
				request.setAttribute("usuarios", usuarios);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());

			redirecionar.forward(request, response);
		}
	}


	/*
	 * POST
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String msg = "Usuário gravado com sucesso!";

		try {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);

			if(dao.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe um usuário com o mesmo login, informe outro usuário.";
			} else {

				if(modelLogin.isNovo()) {
					modelLogin = dao.gravarUsuario(modelLogin);
				} else {
					modelLogin = dao.atualizarUsuario(modelLogin);
					msg = "Atualizado com sucesso!";
				}

			}			

			List<ModelLogin> usuarios = dao.buscarUsuario();
			request.setAttribute("usuarios", usuarios);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			/* PODE SER FEITO ASSIM TAMBÉM
		RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
		request.setAttribute("modelLogin", modelLogin);
		redireciona.forward(request, response);
			 */
		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());

			redirecionar.forward(request, response);
		}


	}	

}
