package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/principal/*"}) /*Intercepta todas as requisições que vierem do projeto ou mapeamento*/
public class FilterAutenticacao implements Filter {


	private static Connection connection;

	public FilterAutenticacao() {

	}

	/*
	 * Encerra os processos quando o servidor é parado
	 * Exemplo.: Matar os processos de conexao com o banco
	 * */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/*
	 * Intercepta as requisições e as respostas no sistema (Tudo que fizer no sistema passa por este metodo)
	 * Exemplo.: Validação de autenticação
	 * Dar commit e rollback de transações de páginas
	 * Validar e fazer redirecionaento de páginas
	 * */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		try {

			//Pegando a sessão
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath(); //URL que está sendo acessada

			/*
			 * Validar se esta sendo logado
			 * */
			if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login");
				redireciona.forward(request, response);
				return; //Parar a execução e redireciona para o login

			} else {
				chain.doFilter(request, response);
			}
			
			connection.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());

			redirecionar.forward(request, response);

			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}


	/*
	 * Inicia os processos ou recursos quando o servidor sob o projeto
	 * Exemplo: Iniciar a conexão com o banco
	 * */
	public void init(FilterConfig fConfig) throws ServletException {
		//Já retorna a conexao com o banco
		connection = SingleConnectionBanco.getConnection();
	}

}
