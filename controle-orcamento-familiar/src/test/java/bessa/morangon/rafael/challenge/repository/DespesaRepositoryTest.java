package bessa.morangon.rafael.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DespesaRepositoryTest {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@Test
	void deveriaDevolverTodasDespesasEntreDuasDatas() {
		
		Despesa despesa1 = new Despesa("Conta de luz", new BigDecimal("200.00"), LocalDate.of(2022, 01, 01), Categoria.MORADIA);
		Despesa despesa2 = new Despesa("Conta de água", new BigDecimal("200.00"), LocalDate.of(2022, 01, 15), Categoria.MORADIA);
		Despesa despesa3 = new Despesa("Conta de gás", new BigDecimal("200.00"), LocalDate.of(2022, 01, 30), Categoria.MORADIA);
		
		entityManager.persist(despesa1);
		entityManager.persist(despesa2);
		entityManager.persist(despesa3);
		
		Optional<List<Despesa>> lista = despesaRepository.findAllByDataDespesaBetween(LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 20));
		
		assertEquals(2, lista.get().size());
		assertNotNull(lista);
	}
	
	@Test
	void deveriaDevolverTodasDespesasComMesmaDescricao() {
		
		Despesa despesa1 = new Despesa("Conta de luz", new BigDecimal("200.00"), LocalDate.of(2022, 05, 10), Categoria.MORADIA);
		Despesa despesa2 = new Despesa("Conta de água", new BigDecimal("200.00"), LocalDate.of(2022, 06, 8), Categoria.MORADIA);
		Despesa despesa3 = new Despesa("Conta de luz", new BigDecimal("200.00"), LocalDate.of(2022, 11, 15), Categoria.MORADIA);
		
		entityManager.persist(despesa1);
		entityManager.persist(despesa2);
		entityManager.persist(despesa3);
		
		List<Despesa> lista = despesaRepository.findAllByDescricao("Conta de luz");
		
		assertEquals(2, lista.size());
		assertNotNull(lista);
		assertEquals("Conta de luz", lista.get(0).getDescricao());
		assertEquals("Conta de luz", lista.get(1).getDescricao());
	}

}
