/**
 * 
 */
package br.net.walltec.api.negocio.servicos;

import br.net.walltec.api.dto.LoginUsuarioDto;
import br.net.walltec.api.dto.UsuarioRetornoLoginDTO;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.dto.AlteracaoSenhaDto;
import br.net.walltec.api.rest.dto.RefreshTokenRetornoDTO;

/**
 * @author wallace
 *
 */
public interface UsuarioService extends CrudPadraoService<Usuario> {

	UsuarioRetornoLoginDTO recuperarUsuarioPorLoginSenha(LoginUsuarioDto dto) throws NegocioException;

    String montarSenhaSegura(String senha);

    RefreshTokenRetornoDTO gerarAccessToken(String refreshToken, String accessToken, Integer idUsuario) throws NegocioException;

	/**
	 * @param dto
	 */
	void alterarSenha(AlteracaoSenhaDto dto) throws NegocioException;    
}
