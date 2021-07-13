package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;


	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}



	/*
	 * Método para SALVAR Usuário
	 * */
	public ModelLogin gravarUsuario(ModelLogin usuario) throws Exception {		

		String sql = "INSERT INTO model_login (nome, email, login, senha) values (?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getEmail());
		stmt.setString(3, usuario.getLogin());
		stmt.setString(4, usuario.getSenha());

		stmt.execute();
		connection.commit();

		return this.consultaUsuarioPorLogin(usuario.getLogin());
	}


	public List<ModelLogin> buscarUsuarioPorNome(String nome){

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		try {

			String sql = "SELECT * FROM model_login WHERE nome like ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, "%"+nome+"%");
			ResultSet resultado = stmt.executeQuery();

			while (resultado.next()) {
				ModelLogin usuario = new ModelLogin();
				usuario.setId(resultado.getLong("id"));
				usuario.setNome(resultado.getString("nome"));
				usuario.setEmail(resultado.getString("email"));
				usuario.setLogin(resultado.getString("login"));

				lista.add(usuario);
			}

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;

	}


	/*
	 * Método para BUSCAR TODOS OS USUÁRIOS
	 * */
	public List<ModelLogin> buscarUsuario() throws SQLException{

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();

		while (resultado.next()) {
			ModelLogin usuario = new ModelLogin();
			usuario.setId(resultado.getLong("id"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setEmail(resultado.getString("email"));
			usuario.setLogin(resultado.getString("login"));

			lista.add(usuario);
		}

		return lista;	

	}


	/*
	 * Método para atualizar Usuário.
	 * */
	public ModelLogin atualizarUsuario(ModelLogin usuario) throws Exception {

		String sql = "UPDATE model_login SET nome=?, email=?, login=?, senha=? WHERE id = " + usuario.getId();
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getEmail());
		stmt.setString(3, usuario.getLogin());
		stmt.setString(4, usuario.getSenha());

		stmt.execute();
		connection.commit();

		return usuario;

	}



	/*
	 * Método que busca Usuário por Login
	 * */
	public ModelLogin consultaUsuarioPorLogin(String login) throws SQLException {

		ModelLogin usuario = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE login = '"+login+"'";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet resultado = stmt.executeQuery();

		while (resultado.next()) {
			usuario.setId(resultado.getLong("id"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setEmail(resultado.getString("email"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
		}

		return usuario;
	}

	/*
	 * Método que busca Usuário por ID
	 * */
	public ModelLogin consultaUsuarioPorId(String id) throws SQLException {

		ModelLogin usuario = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, Long.parseLong(id));

		ResultSet resultado = stmt.executeQuery();

		while (resultado.next()) {
			usuario.setId(resultado.getLong("id"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setEmail(resultado.getString("email"));
			usuario.setLogin(resultado.getString("login"));
			usuario.setSenha(resultado.getString("senha"));
		}

		return usuario;
	}


	/*
	 * Método para verificar se existe login
	 * */
	public boolean validaLogin(String login) throws Exception {

		String sql = "SELECT count(1) > 0 as existe FROM model_login WHERE login = '" + login + "'";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet resultado = stmt.executeQuery();

		resultado.next();
		return resultado.getBoolean("existe");
	}


	public void deletarUser(String idUser) throws Exception {

		String sql = "DELETE FROM model_login WHERE id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setLong(1, Long.parseLong(idUser));

		stmt.executeUpdate();
		connection.commit();

	}



}
