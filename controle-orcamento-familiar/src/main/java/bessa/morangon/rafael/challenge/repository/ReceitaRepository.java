package bessa.morangon.rafael.challenge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bessa.morangon.rafael.challenge.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	public List<Receita> findAllByDescricao(String descricao);
	
	public Optional<List<Receita>> findAllByDataReceitaBetween(LocalDate primeiroDia, LocalDate ultimoDia);

	public Receita findByDescricao(String descricao);

}
