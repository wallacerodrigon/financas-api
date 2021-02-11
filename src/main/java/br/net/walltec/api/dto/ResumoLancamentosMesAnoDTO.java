package br.net.walltec.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ResumoLancamentosMesAnoDTO {

	private Integer ano;
	
	private String nomeMes;
	
	private Integer numMes;
	
	private BigDecimal totalCreditos;
	
	private BigDecimal totalDebitos;
	
	private BigDecimal saldoFinal;
	
	
}
