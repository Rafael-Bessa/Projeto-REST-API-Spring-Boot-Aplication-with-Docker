package bessa.morangon.rafael.challenge.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "despesas")
public class Despesa {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDate dataDespesa = LocalDate.now();	
	@Enumerated(EnumType.STRING)
	private Categoria categoria = Categoria.OUTRAS;
	
	

	public Despesa(String descricao, BigDecimal valor, LocalDate dataDespesa, Categoria categoria) {
		
		this.descricao = descricao;
		this.valor = valor;
		this.dataDespesa = dataDespesa;
		this.categoria = categoria;
	}
	
	public Despesa() {
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

	public LocalDate getDataDespesa() {
		return dataDespesa;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
}
