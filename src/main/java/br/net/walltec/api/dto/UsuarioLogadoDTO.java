/**
 * 
 */
package br.net.walltec.api.dto;

import br.net.walltec.api.entidades.Perfil;
import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
public class UsuarioLogadoDTO {

	private Integer idUsuario;
	
	private String nomeUsuario;
	
	private String numCpf;
	
	private Perfil perfil;
	
	private String descemail;

	private boolean bolAtivo;
	
}
