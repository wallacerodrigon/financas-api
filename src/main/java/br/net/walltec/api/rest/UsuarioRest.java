/**
 * 
 */
package br.net.walltec.api.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import br.net.walltec.api.dto.LoginUsuarioDto;
import br.net.walltec.api.dto.UsuarioRetornoLoginDTO;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.TokenExpiradoException;
import br.net.walltec.api.excecoes.WebServiceException;
import br.net.walltec.api.negocio.servicos.UsuarioService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.comum.RetornoRestDTO;
import br.net.walltec.api.rest.dto.AlteracaoSenhaDto;
import br.net.walltec.api.rest.dto.RefreshTokenRetornoDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import br.net.walltec.api.utilitarios.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author wallace
 *
 */
@Named
@RequestScoped

@Path("/usuarios")
@Produces(value = { MediaType.APPLICATION_JSON })
@Consumes(value = { MediaType.APPLICATION_JSON })
@Interceptors({ RequisicaoInterceptor.class })

@Api(value = "Webservice de usuarios")

@SuppressWarnings("serial")
public class UsuarioRest extends RequisicaoRestPadrao<Usuario> {
	@Inject
	private UsuarioService servico;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.walltec.api.rest.RequisicaoRestPadrao#getServico()
	 */
	@Override
	public CrudPadraoService<Usuario> getServico() {
		return servico;
	}

	@ApiOperation("Efetua o login de um usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorno bem sucedido"),
			@ApiResponse(code = 500, message = "Erro interno") })

	@POST
	@Path("/efetuar-login")
	@PermitAll
	public RetornoRestDTO<UsuarioRetornoLoginDTO> efetuarLogin(LoginUsuarioDto dto, @Context HttpServletRequest request)
			throws WebServiceException {
		try {
			UsuarioRetornoLoginDTO recuperaUsuarioPorLoginSenha = servico.recuperarUsuarioPorLoginSenha(dto);

			if (!this.isLocalHost()) {
				this.gerarCookieRefreshToken(recuperaUsuarioPorLoginSenha.getRefreshToken());
				recuperaUsuarioPorLoginSenha.setRefreshToken(null);
			}
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.OK)
					.comEsteRetorno(recuperaUsuarioPorLoginSenha).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem(e.getMessage()).construir();
		} catch (Exception e) {
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR)
					.comEstaMensagem(e.getMessage()).construir();
		}

	}

//	
//
	@ApiOperation("Serviço para refreshToken")
	@POST
	@Path("/refresh-token/{idUsuario}")
	@RolesAllowed(value = { Constantes.USUARIO_AUTENTICADO } )	
	public RetornoRestDTO<RefreshTokenRetornoDTO> refreshToken(@PathParam("idUsuario") Integer idUsuario,
			@CookieParam("refreshToken") String cookieRefresh) throws WebServiceException {
		try {
			String accessToken = this.getRequest().getHeader(Constantes.TAG_TOKEN);

			RetornoRestDTO<RefreshTokenRetornoDTO> retornoValidaRefreshToken = validarRefreshToken(cookieRefresh, accessToken);

			if ( retornoValidaRefreshToken != null ) {
				return retornoValidaRefreshToken;
			}
			
			String refreshToken = this.isLocalHost() ? this.getRequest().getHeader("refreshToken") : cookieRefresh;
			RefreshTokenRetornoDTO geraAccessToken = this.servico.gerarAccessToken(refreshToken, accessToken,
					idUsuario);
			if (!this.isLocalHost()) {
				this.gerarCookieRefreshToken(geraAccessToken.getRefreshToken());
				geraAccessToken.setRefreshToken(null);
			}
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.OK).comEsteRetorno(geraAccessToken)
					.construir();
		} catch (TokenExpiradoException e) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		} catch (NegocioException e) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem(e.getMessage()).construir();
		} catch (Exception e) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR)
					.comEstaMensagem(e.getMessage()).construir();
		}
	}

	private RetornoRestDTO<RefreshTokenRetornoDTO> validarRefreshToken(String cookieRefresh, String accessToken) {
		if (accessToken == null || accessToken.isEmpty()) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem("É necessário estar autenticado para atualizar o token").construir();
		}

		if (!this.isLocalHost() && this.getRequest().getHeader("refreshToken") != null) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem("Refresh token enviado em local inválido").construir();
		}

		if (!this.isLocalHost() && cookieRefresh == null) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.FORBIDDEN)
					.comEstaMensagem("Refresh token não enviado").construir();
		}
		if (this.isLocalHost() && this.getRequest().getHeader("refreshToken") == null) {
			return new RetornoRestDTO<RefreshTokenRetornoDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem("Refresh token não recebido").construir();
		}
		
		return null;
	}

	@ApiOperation("Serviço para recuperar senha")
	@POST
	@Path("/recuperar-senha")
	@PermitAll
	public RetornoRestDTO recuperarSenha() throws WebServiceException {
		return null;

	}

	@ApiOperation("Serviço para alterar senha")
	@PUT
	@Path("/alterar-senha")
	public RetornoRestDTO<Void> alterarSenha(AlteracaoSenhaDto dto) throws WebServiceException {
		try {
			this.servico.alterarSenha(dto);
			return new RetornoRestDTO<Void>().comEsteCodigo(Status.OK).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<Void>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (IllegalArgumentException e) {
			return new RetornoRestDTO<Void>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<Void>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR)
					.comEstaMensagem(e.getMessage()).construir();
		}
	}

	/**
	 * @param refreshToken
	 * @return
	 */
	private Cookie gerarCookieRefreshToken(String refreshToken) {
		String path = "/";
		String domain = getRequest().getServerName();
		boolean ehHttps = this.isLocalHost() ? false : true;
		Cookie c = new Cookie("refreshToken", refreshToken);
		c.setDomain(domain);
		c.setHttpOnly(true);
		c.setMaxAge(2592000);
		c.setSecure(ehHttps);
		c.setPath(path);
		this.getResponse().addCookie(c);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getClasseEntidade()
	 */
	@Override
	protected Class<Usuario> getClasseEntidade() {
		return Usuario.class;
	}

	@POST
	@Path("/efetuar-logout/{idUsuario}")
	@RolesAllowed(value = { Constantes.USUARIO_AUTENTICADO } )
	public RetornoRestDTO<UsuarioRetornoLoginDTO> efetuarLogout(@PathParam("idUsuario") Integer idUsuario)
			throws WebServiceException {
		try {
			Usuario usuario = this.servico.find(idUsuario);
			if (usuario == null) {
				return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.BAD_REQUEST)
						.comEstaMensagem("Usuário não encontrado com o id informado").construir();
			}
			this.gerarCookieRefreshToken(null);
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.OK).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem(e.getMessage()).construir();
		} catch (Exception e) {
			return new RetornoRestDTO<UsuarioRetornoLoginDTO>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR)
					.comEstaMensagem(e.getMessage()).construir();
		}

	}

}
