package bessa.morangon.rafael.challenge.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bessa.morangon.rafael.challenge.dto.ResumoDto;
import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;
import bessa.morangon.rafael.challenge.model.Receita;
import bessa.morangon.rafael.challenge.repository.DespesaRepository;
import bessa.morangon.rafael.challenge.repository.ReceitaRepository;

@RestController
@RequestMapping("/resumo")
public class ResumoController {

	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	private DespesaRepository despesaRepository;

	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<?> resumoMensal(@PathVariable int ano, @PathVariable int mes) {

		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, mes - 1);

		LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
		LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));

		Optional<List<Receita>> receitas = receitaRepository.findAllByDataReceitaBetween(primeiroDia, ultimoDia);
		Optional<List<Despesa>> despesas = despesaRepository.findAllByDataDespesaBetween(primeiroDia, ultimoDia);

		if (receitas.isPresent() && despesas.isPresent()) {

			BigDecimal totalReceita = receitas.get().stream().map(Receita::getValor).reduce(BigDecimal.ZERO,
					BigDecimal::add);
			BigDecimal totalDespesa = despesas.get().stream().map(Despesa::getValor).reduce(BigDecimal.ZERO,
					BigDecimal::add);

			Map<Categoria, BigDecimal> mapa = new HashMap<>();

			for (Despesa d : despesas.get()) {

				if (mapa.containsKey(d.getCategoria())) {

					mapa.put(d.getCategoria(), mapa.get(d.getCategoria()).add(d.getValor()));
				}

				else {
					mapa.put(d.getCategoria(), d.getValor());
				}
			}

			return ResponseEntity
					.ok(new ResumoDto(totalReceita, totalDespesa, totalReceita.subtract(totalDespesa), mapa));

		}

		return ResponseEntity.notFound().build();

	}
}
