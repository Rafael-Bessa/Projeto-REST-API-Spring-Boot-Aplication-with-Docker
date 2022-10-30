package bessa.morangon.rafael.challenge.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;
import bessa.morangon.rafael.challenge.model.Receita;
import bessa.morangon.rafael.challenge.repository.DespesaRepository;
import bessa.morangon.rafael.challenge.repository.ReceitaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test", "dev"})
class ResumoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;

	@Test
	void deveriaDevolverResumoDeDeterminadoAnoMes() throws Exception {		
		despesaRepository.deleteAll();
		receitaRepository.deleteAll();
		
		
		Despesa despesa1 = new Despesa("Supermercado", new BigDecimal("1000.00"), LocalDate.of(2022, 10, 1), Categoria.ALIMENTACAO);
		Despesa despesa2 = new Despesa("Faculdade", new BigDecimal("500.00"), LocalDate.of(2022, 10, 10), Categoria.EDUCACAO);
		Despesa despesa3 = new Despesa("Supermercado", new BigDecimal("1000.00"), LocalDate.of(2022, 11, 1), Categoria.ALIMENTACAO);
		
		Receita receita1 = new Receita("Salario", new BigDecimal("5000.00"), LocalDate.of(2022, 10, 1));
		Receita receita2 = new Receita("Aluguel", new BigDecimal("1500.00"), LocalDate.of(2022, 10, 5));
		Receita receita3 = new Receita("Salario", new BigDecimal("5000.00"), LocalDate.of(2022, 11, 1));
		
		despesaRepository.save(despesa1);	
		despesaRepository.save(despesa2);
		despesaRepository.save(despesa3);
		receitaRepository.save(receita1);
		receitaRepository.save(receita2);
		receitaRepository.save(receita3);
			
		URI uri = new URI("/resumo/2022/10");
		String conteudo = "{\"totalReceitasMes\":6500.00, \"totalDespesasMes\":1500.00, \"saldoFinal\":5000.00, \"despesasPorCategoria\": {\"EDUCACAO\":500.00, \"ALIMENTACAO\":1000.00}}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
		.get(uri))
		.andExpect(MockMvcResultMatchers
		.status()
		.isOk()).andExpect(MockMvcResultMatchers.content().json(conteudo));
	}

}
