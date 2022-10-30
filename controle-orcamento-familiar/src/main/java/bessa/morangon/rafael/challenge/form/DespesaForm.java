package bessa.morangon.rafael.challenge.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;
import bessa.morangon.rafael.challenge.repository.DespesaRepository;

public class DespesaForm {

	@NotEmpty @NotNull @Size(min = 5, max = 100)
	private String descricao;
	
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2)
	private BigDecimal valor;
	
	private Categoria categoria = Categoria.OUTRAS;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Despesa converteDespesaFormEmDespesa() {
		Despesa despesa = new Despesa(descricao, valor, LocalDate.now(), categoria);
//		despesa.setDescricao(descricao);
//		despesa.setValor(valor);
//		despesa.setCategoria(categoria);	
		return despesa;
	}
	
	
	public Despesa atualizaDespesa(Long id, DespesaRepository despesaRepository) {
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			despesa.get().setDescricao(this.descricao);
			despesa.get().setValor(this.valor);
			return despesa.get();
		}
		
		return null;
		
	}
	
}
