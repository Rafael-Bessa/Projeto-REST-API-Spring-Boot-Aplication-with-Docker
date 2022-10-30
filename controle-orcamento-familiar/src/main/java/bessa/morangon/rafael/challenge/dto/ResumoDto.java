package bessa.morangon.rafael.challenge.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import bessa.morangon.rafael.challenge.model.Categoria;

public class ResumoDto {

	private BigDecimal totalReceitasMes;
	private BigDecimal totalDespesasMes;
	private BigDecimal saldoFinal;
	private Map<Categoria, BigDecimal> despesasPorCategoria = new HashMap<>();
	
	public ResumoDto(BigDecimal totalReceitasMes, BigDecimal totalDespesasMes, BigDecimal saldoFinal,
			Map<Categoria, BigDecimal> despesasPorCategoria) {
		this.totalReceitasMes = totalReceitasMes;
		this.totalDespesasMes = totalDespesasMes;
		this.saldoFinal = saldoFinal;
		this.despesasPorCategoria = despesasPorCategoria;
	}

	public BigDecimal getTotalReceitasMes() {
		return totalReceitasMes;
	}

	public BigDecimal getTotalDespesasMes() {
		return totalDespesasMes;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public Map<Categoria, BigDecimal> getDespesasPorCategoria() {
		return despesasPorCategoria;
	}

}
