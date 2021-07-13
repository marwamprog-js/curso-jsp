package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	
	
	
	
	/*
	 * Método verifica se é um novo cadastro ou não
	 * */
	public boolean isNovo() {
		
		Boolean retorno = true;
		
		if(this.id == null) {
			retorno = true;
		} else if(this.id != null && this.id > 0) {
			retorno = false;
		}
		
		return retorno;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}