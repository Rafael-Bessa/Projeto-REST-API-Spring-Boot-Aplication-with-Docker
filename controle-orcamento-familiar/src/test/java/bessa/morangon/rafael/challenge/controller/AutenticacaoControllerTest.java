package bessa.morangon.rafael.challenge.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import bessa.morangon.rafael.challenge.model.Usuario;
import bessa.morangon.rafael.challenge.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test", "prod"})
class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Test
	void deveriaDevolverBadRequestParaUsuarioNãoAutenticado() throws Exception {
		
		usuarioRepository.deleteAll();
		
		URI uri = new URI("/auth");
		String json = "{\"username\":\"rafaelb\", \"password\":\"123\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
		.post(uri)
		.content(json)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
		.status()
		.isBadRequest());	
	}
	
	@Test
	void deveriaDevolverOkParaUsuarioExistenteNoBancoDeDados() throws Exception {
			
		usuarioRepository.deleteAll();
		
		String senha = new BCryptPasswordEncoder().encode("123");
		Usuario usuario = new Usuario();
		usuario.setUsername("rafaelb");
		usuario.setPassword(senha);
		usuarioRepository.save(usuario);
		
		URI uri = new URI("/auth");
		String json = "{\"username\":\"rafaelb\", \"password\":\"123\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
		.post(uri)
		.content(json)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
		.status()
		.isOk());	
		
	}

}
