package bessa.morangon.rafael.challenge.form;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import bessa.morangon.rafael.challenge.model.Receita;
import bessa.morangon.rafael.challenge.repository.ReceitaRepository;

public class ReceitaForm {

	@NotEmpty @NotNull @Size(min = 5, max = 100)
	private String descricao;
	
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2)
	private BigDecimal valor;
	
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
	
	public Receita converteReceitaFormEmReceita() {
		Receita receita = new Receita();
		receita.setDescricao(descricao);
		receita.setValor(valor);
		return receita;
	}
	
	
	public Receita atualizaReceita(Long id, ReceitaRepository receitaRepository) {
		Optional<Receita> receita = receitaRepository.findById(id);
		
		if(receita.isPresent()) {
			receita.get().setDescricao(this.descricao);
			receita.get().setValor(this.valor);
			return receita.get();
		}
		
		return null;
		
	}
	
}
