/**
 * 
 */
package br.net.walltec.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
@AllArgsConstructor
public class RefreshTokenRetornoDTO {
	
	private String accessToken;
	
	private String refreshToken;

}
