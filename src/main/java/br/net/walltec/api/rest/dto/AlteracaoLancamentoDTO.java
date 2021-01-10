/**
 * 
 */
package br.net.walltec.api.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.TipoLancamento;
import lombok.Data;



/**
 * @author wallace
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class AlteracaoLancamentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="ID lançamento é obrigatório")
	private Integer idLancamento;
	
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
