package bessa.morangon.rafael.challenge.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import bessa.morangon.rafael.challenge.dto.ReceitaDto;
import bessa.morangon.rafael.challenge.form.ReceitaForm;
import bessa.morangon.rafael.challenge.model.Receita;
import bessa.morangon.rafael.challenge.repository.ReceitaRepository;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	@Autowired
	private ReceitaRepository receitaRepository;

	@GetMapping
	public List<ReceitaDto> listaReceitas(String descricao) {

		if (descricao == null) {
			return ReceitaDto.converteReceitaEmReceitaDto(receitaRepository.findAll());
		}

		return ReceitaDto.converteReceitaEmReceitaDto(receitaRepository.findAllByDescricao(descricao));

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> mostraReceita(@PathVariable Long id) {

		Optional<Receita> receita = receitaRepository.findById(id);

		if (receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDto(receita.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ReceitaDto> cadastraReceita(@RequestBody @Valid ReceitaForm form,
			UriComponentsBuilder buider) {

		Receita r = receitaRepository.findByDescricao(form.getDescricao());
		
		if(r == null || r.getDataReceita().getMonthValue() != LocalDate.now().getMonthValue()) {
		
			Receita receita = form.converteReceitaFormEmReceita();
			receitaRepository.save(receita);
	
			URI uri = buider.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
	
			return ResponseEntity.created(uri).body(new ReceitaDto(receita));
		}
		
		return ResponseEntity.badRequest().build();
		
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizaReceita(@PathVariable Long id, @RequestBody @Valid ReceitaForm form) {

		if (form.atualizaReceita(id, receitaRepository) == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new ReceitaDto(form.atualizaReceita(id, receitaRepository)));

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletaReceita(@PathVariable Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);

		if (receita.isPresent()) {
			receitaRepository.delete(receita.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<?> receitaMes(@PathVariable int ano, @PathVariable int mes) {
		
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, mes);
	
		LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
		LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Optional<List<Receita>> receitas = receitaRepository.findAllByDataReceitaBetween(primeiroDia, ultimoDia);
		
		if(receitas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.ok(ReceitaDto.converteReceitaEmReceitaDto(receitas.get()));
			
	}

}
