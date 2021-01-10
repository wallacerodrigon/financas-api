package br.net.walltec.api.rest.dto;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.dto.DtoPadrao;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.TipoLancamento;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InclusaoLancamentoDTO extends DtoPadrao {

	
	@NotNull(message="Tipo lançamento é obrigatório")
	private TipoLancamento tipoLancamento;
	 
	@NotNull(message="Valor do lançamento é obrigatório")
	private BigDecimal valorLancamento;

	@JoinColumn(name="idformapagamento", nullable=false)	
	private FormaPagamento formaPagamento;

	@NotNull(message="Descrição do lançamento é obrigatório")
	private String descLancamento;

	@NotNull(message="Data de vencimento é obrigatório")
	private String dataVencimentoString;
	
}
