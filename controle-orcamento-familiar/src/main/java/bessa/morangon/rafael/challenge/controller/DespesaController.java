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

import bessa.morangon.rafael.challenge.dto.DespesaDto;
import bessa.morangon.rafael.challenge.form.DespesaForm;
import bessa.morangon.rafael.challenge.model.Despesa;
import bessa.morangon.rafael.challenge.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;

	@GetMapping
	public List<DespesaDto> listaDespesas(String descricao) {

		if (descricao == null) {
			return DespesaDto.converteDespesaEmDespesaDto(despesaRepository.findAll());
		}
		return DespesaDto.converteDespesaEmDespesaDto(despesaRepository.findAllByDescricao(descricao));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> mostraDespesa(@PathVariable Long id) {

		Optional<Despesa> despesa = despesaRepository.findById(id);

		if (despesa.isPresent()) {
			return ResponseEntity.ok(new DespesaDto(despesa.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DespesaDto> cadastraDespesa(@RequestBody @Valid DespesaForm form,
			UriComponentsBuilder buider) {
		
		Despesa d = despesaRepository.findByDescricao(form.getDescricao());
		
		if(d == null || d.getDataDespesa().getMonthValue() != LocalDate.now().getMonthValue()) {
			
			Despesa despesa = form.converteDespesaFormEmDespesa();
			despesaRepository.save(despesa);

			URI uri = buider.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();

			return ResponseEntity.created(uri).body(new DespesaDto(despesa));
		}
		
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizaDespesa(@PathVariable Long id, @RequestBody @Valid DespesaForm form) {

		if (form.atualizaDespesa(id, despesaRepository) == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new DespesaDto(form.atualizaDespesa(id, despesaRepository)));

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletaDespesa(@PathVariable Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);

		if (despesa.isPresent()) {
			despesaRepository.delete(despesa.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<?> despesasMes(@PathVariable int ano, @PathVariable int mes) {
		
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.MONTH, mes);
	
		LocalDate primeiroDia = LocalDate.of(ano, mes, 1);
		LocalDate ultimoDia = LocalDate.of(ano, mes, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Optional<List<Despesa>> despesas = despesaRepository.findAllByDataDespesaBetween(primeiroDia, ultimoDia);
		
		if(despesas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.ok(DespesaDto.converteDespesaEmDespesaDto(despesas.get()));
			
	}
	
	

}
