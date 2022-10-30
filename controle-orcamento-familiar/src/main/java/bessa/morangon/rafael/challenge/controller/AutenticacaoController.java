package bessa.morangon.rafael.challenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bessa.morangon.rafael.challenge.dto.TokenDto;
import bessa.morangon.rafael.challenge.form.LoginForm;
import bessa.morangon.rafael.challenge.security.GeradorTokenService;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private GeradorTokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){
		
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			Authentication authenticate = authenticationManager.authenticate(dadosLogin);
			
			String token = tokenService.gerarToken(authenticate);		
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		}
		
		catch(AuthenticationException e){
			return ResponseEntity.badRequest().build();
		}
		
	}

}
