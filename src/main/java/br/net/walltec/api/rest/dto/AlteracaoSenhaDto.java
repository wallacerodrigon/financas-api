/**
 * 
 */
package br.net.walltec.api.rest.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
public class AlteracaoSenhaDto {

	@NotNull
	private Integer idUsuario;
	
	@NotNull
	private String senhaAtual;
	
	@NotNull
	private String novaSenha;
}
