package bessa.morangon.rafael.challenge.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;
import bessa.morangon.rafael.challenge.repository.DespesaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test", "dev"})
class DespesaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DespesaRepository despesaRepository;

	@Test
	void deveriaDevolverBadRequestAoTentarAdicionarUmaDespesaComDescricaoIgualNoMesmoMes() throws Exception {		
		despesaRepository.deleteAll();
		Despesa despesa = new Despesa("Supermercado", new BigDecimal("1200.00"), LocalDate.now(), Categoria.ALIMENTACAO);
		despesaRepository.save(despesa)	;	
			
		URI uri = new URI("/despesas");
		String json = "{\"descricao\":\"Supermercado\", \"valor\":\"800.00\"}";
		
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
	void deveriaDevolverCreatedAoTentarAdicionarUmaDespesaComDescricaoIgualEmUmMesDiferente() throws Exception {
		
		despesaRepository.deleteAll();
		Despesa despesa = new Despesa("Supermercado", new BigDecimal("1200.00"), LocalDate.now().plusMonths(1), Categoria.ALIMENTACAO);
		despesaRepository.save(despesa)	;	
			
		URI uri = new URI("/despesas");
		String json = "{\"descricao\":\"Supermercado\", \"valor\":\"800.00\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
		.post(uri)
		.content(json)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
		.status()
		.isCreated());
	}
	
	@Test
	void deveriaDevolverTodasDespesasDoMesEspecifico() throws Exception {
		
		despesaRepository.deleteAll();
		
		Despesa despesa1 = new Despesa("Supermercado", new BigDecimal("1200.00"), LocalDate.of(2022, 10, 15), Categoria.ALIMENTACAO);
		Despesa despesa2 = new Despesa("Faculdade", new BigDecimal("1500.00"), LocalDate.of(2022, 11, 20), Categoria.EDUCACAO);
		despesaRepository.save(despesa1);	
		despesaRepository.save(despesa2);	
			
		URI uri = new URI("/despesas/2022/10");
		String conteudo = "[{\"descricao\":\"Supermercado\", \"valor\":1200.00, \"dataDespesa\":\"2022-10-15\", \"categoria\":\"ALIMENTACAO\"}]";

		mockMvc
		.perform(MockMvcRequestBuilders
		.get(uri))
		.andExpect(MockMvcResultMatchers
		.status()
		.isOk()).andExpect(MockMvcResultMatchers.content().json(conteudo));
		
	}
	
}
