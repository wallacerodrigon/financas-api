package br.net.walltec.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DivisaoLancamentoDTO extends DtoPadrao {

	@NotNull(message = "Informe o lançamento de origem")
	private Integer idLancamentoOrigem;
	
	@NotNull(message = "Informe a data do evento no formato YYYY-MM-DD")
	@Pattern(message = "Formato de data inválido: aaaa-mm-dd", regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String dataEventoIso;
	
	@NotNull(message = "Informe o valor do uso")
	private BigDecimal valor;
	
	@NotNull(message = "Informe uma descriçao para o lançamento")
	@Size(min = 3, max= 30, message = "A descrição deve conter de {min} até {max} caracteres")
	private String descricao;
	
	@NotNull(message = "Informe o ID da forma de pagamento")
	private Integer idFormaPagamento;
}
