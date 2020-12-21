package br.net.walltec.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FechamentoMensalDTO extends DtoPadrao {
	
	@NotNull(message="Ano é obrigatório")
	private Integer numAno;
	
	@NotNull(message="Mês é obrigatório")
	private Integer numMes;
}
