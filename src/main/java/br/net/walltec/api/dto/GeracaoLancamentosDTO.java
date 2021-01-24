package br.net.walltec.api.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.rest.dto.InclusaoLancamentoDTO;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeracaoLancamentosDTO extends InclusaoLancamentoDTO {

	@NotNull(message="Favor informar a qtd de meses a repetir")
	private Integer qtdRepeticoes;
	
}
