package bessa.morangon.rafael.challenge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import bessa.morangon.rafael.challenge.model.Categoria;
import bessa.morangon.rafael.challenge.model.Despesa;

public class DespesaDto {
	
	private String descricao;
	private BigDecimal valor;
	private LocalDate dataDespesa;
	private Categoria categoria;
	
	public DespesaDto(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.dataDespesa = despesa.getDataDespesa();
		this.categoria = despesa.getCategoria();
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public LocalDate getDataDespesa() {
		return dataDespesa;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	
	public static List<DespesaDto> converteDespesaEmDespesaDto(List<Despesa> despesa) {
		return despesa.stream().map(DespesaDto::new).collect(Collectors.toList());
	}

	
}
