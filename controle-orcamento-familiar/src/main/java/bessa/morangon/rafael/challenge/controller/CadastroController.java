package bessa.morangon.rafael.challenge.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bessa.morangon.rafael.challenge.dto.UsuarioDto;
import bessa.morangon.rafael.challenge.form.UsuarioForm;
import bessa.morangon.rafael.challenge.model.Usuario;
import bessa.morangon.rafael.challenge.repository.UsuarioRepository;

@RestController
@RequestMapping("/register")
public class CadastroController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> cadastraUsuario(@RequestBody @Valid UsuarioForm form) {
		
		Optional<Usuario> u = usuarioRepository.findByUsername(form.getUsername());
		
			if(u.isPresent()) {
				 return ResponseEntity.badRequest().build();
			}
		
			String username = form.getUsername();
			String senha = new BCryptPasswordEncoder().encode(form.getPassword());
			
			Usuario usuario = new Usuario();
			usuario.setPassword(senha);
			usuario.setUsername(username);
			usuarioRepository.save(usuario);
	
			return ResponseEntity.ok().build();
	
	}
	
	
}
