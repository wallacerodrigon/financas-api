/**
 * 
 */
package br.net.walltec.api.negocio.servicos.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.net.walltec.api.dto.LoginUsuarioDto;
import br.net.walltec.api.dto.UsuarioRetornoLoginDTO;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.RegistroNaoEncontradoException;
import br.net.walltec.api.excecoes.TokenExpiradoException;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.UsuarioService;
import br.net.walltec.api.persistencia.dao.UsuarioDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;
import br.net.walltec.api.rest.dto.AlteracaoSenhaDto;
import br.net.walltec.api.rest.dto.RefreshTokenRetornoDTO;
import br.net.walltec.api.tokens.TokenManager;
import br.net.walltec.api.utilitarios.Constantes;
import br.net.walltec.api.utilitarios.UtilCriptografia;
import br.net.walltec.api.utilitarios.UtilObjeto;
import br.net.walltec.api.validadores.ValidadorDados;
import io.jsonwebtoken.Claims;

/**
 * @author wallace
 *
 */
@Named
public class UsuarioServicoImpl extends AbstractCrudServicePadrao<Usuario> implements UsuarioService {

	@Inject
	private UsuarioDao usuarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<Usuario> getDao() {
		// TODO Auto-generated method stub
		return usuarioDao;
	}

	@Override
	public UsuarioRetornoLoginDTO recuperarUsuarioPorLoginSenha(LoginUsuarioDto dto) throws NegocioException {

		try {
			ValidadorDados.validarDadosEntrada(dto);

			Usuario u = usuarioDao.recuperarUsuario(dto.getEmail(), this.montarSenhaSegura(dto.getSenha()) );

			if (! u.isBolAtivo()) {
				throw new RegistroNaoEncontradoException("Usuário não encontrado");
			}

			UsuarioRetornoLoginDTO dtoRetorno = new UsuarioRetornoLoginDTO();
			dtoRetorno.setAccessToken(new TokenManager().gerarToken(u, Constantes.INTERVALO_TOKEN));
			gerarRefreshToken(dtoRetorno);
			
			return dtoRetorno;
		} catch (NoResultException e) {
			throw new RegistroNaoEncontradoException(e);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	private void gerarRefreshToken(UsuarioRetornoLoginDTO dtoRetorno) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(Constantes.TAG_HASH_TOKEN, UtilCriptografia.criptografa(dtoRetorno.getAccessToken()) );
		dtoRetorno.setRefreshToken(new TokenManager().gerarTokenComClaims(claims, Constantes.INTERVALO_REFRESH_TOKEN));
	}

	@Override
	public String montarSenhaSegura(String senha) {
		StringBuffer buffer = new StringBuffer(senha);
		String novaStringRevertida = buffer.reverse().toString();
		try {
			novaStringRevertida = UtilCriptografia.criptografa(novaStringRevertida);
			return novaStringRevertida;
		} catch (Exception e) {
			return "[erro: algoritmo não encontrado]";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.spc.usuario.api.servico.UsuarioServico#gerarAccessToken(br.com.spc.
	 * usuario.api.rest.dto.RefreshTokenDTO)
	 */
	@Override
	public RefreshTokenRetornoDTO gerarAccessToken(String refreshToken, String accessToken, Integer idUsuario) throws NegocioException {
		TokenManager tokenManager = new TokenManager();
		boolean refreshTokenValido = tokenManager.isTokenValido(refreshToken);

		if (!refreshTokenValido) {
			throw new TokenExpiradoException("[FR-1] Sua sessão está expirada, logue novamente no sistema");
		}
		Claims claimsRefresh = new TokenManager().getClaims(refreshToken);
		String hashAccessToken = (String)claimsRefresh.get(Constantes.TAG_HASH_TOKEN);
		String tokenDoHeader = accessToken.replaceAll("Bearer ", "").trim();
		
		if (! UtilCriptografia.criptografa(tokenDoHeader).equals(hashAccessToken) ) {
			throw new NegocioException("Refresh token inválido.");
		}
		
		Usuario usuario = this.find(idUsuario);

		if (UtilObjeto.isVazio(usuario)) {
			throw new NegocioException(
					"[FR-3] Ocorreu algum problema interno no sistema (usuario não encontrado). Favor logar novamente!");
		}

		UsuarioRetornoLoginDTO dtoRetorno = new UsuarioRetornoLoginDTO();
		dtoRetorno.setAccessToken(new TokenManager().gerarToken(usuario, Constantes.INTERVALO_TOKEN));	
		this.gerarRefreshToken(dtoRetorno);
		return new RefreshTokenRetornoDTO(dtoRetorno.getAccessToken(), dtoRetorno.getRefreshToken());
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.UsuarioService#alterarSenha(br.net.walltec.api.rest.dto.AlteracaoSenhaDto)
	 */
	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@TransactionScoped()
	public void alterarSenha(AlteracaoSenhaDto dto) throws NegocioException {
		ValidadorDados.validarDadosEntrada(dto);
		
		Usuario usuario = this.find(dto.getIdUsuario());
		
		if (usuario == null) {
			throw new RegistroNaoEncontradoException("Usuário não encontrado");
		}
		
		String senhaAtualCriptografada = this.montarSenhaSegura(dto.getSenhaAtual());
		
		if (! senhaAtualCriptografada.equalsIgnoreCase(usuario.getDescSenha())) {
			throw new RegistroNaoEncontradoException("Senha atual inválida");
		}
		
		String novaSenha = this.montarSenhaSegura(dto.getNovaSenha());
		usuario.setDescSenha(novaSenha);
		usuario.setDataAlteracao(new Date());
		usuario.setDataUltimoAcesso(new Date());
		this.alterar(usuario);
		
	}
	
	public static void main(String[] args) {
		System.out.println(new UsuarioServicoImpl().montarSenhaSegura("123456"));
	}
}
