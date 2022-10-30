package bessa.morangon.rafael.challenge.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import bessa.morangon.rafael.challenge.model.Usuario;
import bessa.morangon.rafael.challenge.repository.UsuarioRepository;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

	private UsuarioRepository usuarioRepository;
	private GeradorTokenService geradorTokenService;

	public AutenticacaoTokenFilter(GeradorTokenService geradorTokenService, UsuarioRepository repository) {

		this.geradorTokenService = geradorTokenService;
		this.usuarioRepository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);

		boolean valido = geradorTokenService.validacao(token);

		if (valido) {
			autenticarCliente(token);
		}

		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {

		Long idUsuario = geradorTokenService.pegaIdUsuario(token);

		Usuario usuario = usuarioRepository.findById(idUsuario).get();

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null,
				usuario.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

	}

	private String recuperarToken(HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());

	}

}
