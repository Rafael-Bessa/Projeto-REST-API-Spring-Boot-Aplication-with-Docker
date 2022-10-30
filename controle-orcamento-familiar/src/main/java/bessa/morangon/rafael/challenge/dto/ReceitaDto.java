package bessa.morangon.rafael.challenge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import bessa.morangon.rafael.challenge.model.Receita;

public class ReceitaDto {
	
	private String descricao;
	private BigDecimal valor;
	private LocalDate dataReceita;
	
	public ReceitaDto(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.dataReceita = receita.getDataReceita();
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public LocalDate getDataReceita() {
		return dataReceita;
	}
	
	
	public static List<ReceitaDto> converteReceitaEmReceitaDto(List<Receita> receita) {
		return receita.stream().map(ReceitaDto::new).collect(Collectors.toList());
	}

}
