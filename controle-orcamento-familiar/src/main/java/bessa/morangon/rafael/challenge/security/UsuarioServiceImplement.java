package bessa.morangon.rafael.challenge.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bessa.morangon.rafael.challenge.model.Usuario;
import bessa.morangon.rafael.challenge.repository.UsuarioRepository;

@Service
public class UsuarioServiceImplement implements UserDetailsService {

	final UsuarioRepository usuarioRepository;

	public UsuarioServiceImplement(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		return usuario;
	}

}
