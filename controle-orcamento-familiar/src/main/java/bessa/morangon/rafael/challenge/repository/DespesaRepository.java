package bessa.morangon.rafael.challenge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bessa.morangon.rafael.challenge.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	public List<Despesa> findAllByDescricao(String descricao);
	
	public Optional<List<Despesa>> findAllByDataDespesaBetween(LocalDate primeiroDia, LocalDate ultimoDia);
	
	public Despesa findByDescricao(String descricao);
}
