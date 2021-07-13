package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;
	
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	/*
	 * 
	 * */
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		
		String sql = "SELECT * FROM model_login WHERE login = ? and senha = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, modelLogin.getLogin());
		stmt.setString(2, modelLogin.getSenha());
		
		ResultSet resultado = stmt.executeQuery();
		
		if(resultado.next()) {
			return true;
		}
		
		return false;
	}
	
}
