/**
 * 
 */
package br.net.walltec.api.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

public class RegistroCodBarrasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="ID lançamento é obrigatório")
	private Integer idLancamento;
	
	@NotNull(message="Número do código de barras é obrigatório")
	@Size(min = 44, max=48, message = "Quantidade de dígitos deve estar entre {min} e {max} digitos  " )
	private String numCodBarras;
	
}
