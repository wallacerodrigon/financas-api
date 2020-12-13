/**
 * 
 */
package br.net.walltec.api.dto;

import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
public class UsuarioRetornoLoginDTO {

	private String accessToken;
	
	private String refreshToken; //somente será retornado em caso de testes
	

}
