package bessa.morangon.rafael.challenge.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDate dataReceita = LocalDate.now();
	
	public Receita(String descricao, BigDecimal valor, LocalDate dataReceita) {
		this.descricao = descricao;
		this.valor = valor;
		this.dataReceita = dataReceita;
	}
	
	public Receita() {
	}

	public Long getId() {
		return id;
	}

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

	public LocalDate getDataReceita() {
		return dataReceita;
	}

	
}
