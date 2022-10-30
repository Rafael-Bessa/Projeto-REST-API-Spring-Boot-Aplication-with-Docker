package bessa.morangon.rafael.challenge.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import bessa.morangon.rafael.challenge.model.Usuario;

public class UsuarioForm {

	@NotEmpty
	@NotNull
	@Size(min = 5, max = 20)
	private String username;

	@NotEmpty
	@NotNull
	@Size(min = 5, max = 20)
	private String password;

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
	
	public Usuario converteUsuarioFormEmUsuario() {
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		usuario.setPassword(password);
		return usuario;
	}

}
