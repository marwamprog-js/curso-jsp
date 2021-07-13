package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "root";
	private static Connection connection = null;
	
	
	public static Connection getConnection() {
		return connection;
	}
	
	
	/*
	 * Chamar a classe direto também efetua uma nova conexao
	 * */
	static {
		conectar();
	}
	
	/*
	 * Toda vez que tiver uma instancia vai conectar
	 * */
	public SingleConnectionBanco() {
		conectar();
	}
	
	
	/*
	 * Método para conexão
	 * 
	 * */
	private static void conectar() {
		
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver"); //Carrega driver de conexão
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); // Para não efetuar alterações no banco sem nosso comando
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
