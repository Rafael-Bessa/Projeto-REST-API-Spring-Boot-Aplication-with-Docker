package bessa.morangon.rafael.challenge.dto;

import bessa.morangon.rafael.challenge.model.Usuario;

public class UsuarioDto {
	
	private String username;
	private String password;
	
	
	public UsuarioDto(Usuario usuario) {		
		this.username = usuario.getUsername();
		this.password = usuario.getPassword();
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
