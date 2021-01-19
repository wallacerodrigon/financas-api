package br.net.walltec.api.rest.interceptors;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import br.net.walltec.api.dto.UsuarioLogadoDTO;
import br.net.walltec.api.entidades.Perfil;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.AcessoNegadoException;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.PerfilService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.tokens.TokenManager;
import br.net.walltec.api.utilitarios.Constantes;

@Named
@Interceptor

/**
 * Interceptador para todas as requisições
 */
public class RequisicaoInterceptor {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@Inject
	private PerfilService perfilService;
	
	public static UsuarioLogadoDTO usuarioLogado;

	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object verificarRequisicao(InvocationContext contexto) throws Exception {
		log.info("Executando interceptor no método " + contexto.getMethod().getName() + " classe: "
				+ contexto.getClass().getName());

		try {

			if (contexto.getTarget() != null) {
				RequisicaoRestPadrao target = (RequisicaoRestPadrao) contexto.getTarget();
				HttpHeaders headers = target.getHeaders();
				if (!contexto.getMethod().isAnnotationPresent(PermitAll.class)) {
					String token = recuperarToken(headers);
					validarToken(token, contexto); // valida autenticação
					
					RequisicaoInterceptor.usuarioLogado = new TokenManager().getUsuarioFromToken(token);
					
					//validarAutorizacao(token, target, contexto.getMethod()); // valida autorização
					
				}
				log.info("Finalizando interceptor");
				return contexto.proceed();
			} else {
				log.info("Finalizando interceptor");
				return null;
			}
		} catch (AcessoNegadoException e) {
			throw new WebApplicationException(e.getMessage(), Status.UNAUTHORIZED);
		} catch (Exception e) {
			throw new WebApplicationException(e.getMessage());
		}
	}

	/**
	 * @param target
	 * @param method
	 */
	/**
	 * @param token
	 * @param target
	 * @throws NegocioException
	 */
	private void validarAutorizacao(String token, RequisicaoRestPadrao target, Method metodoClasseRest) throws NegocioException {
			TokenManager tokenManager = new TokenManager();
			
			Perfil perfilDoUsuario = perfilService.find(RequisicaoInterceptor.usuarioLogado.getPerfil().getIdPerfil());
			
			if (!perfilDoUsuario.isBolAdmin()) {
				String recurso = target.getRequest().getRequestURI().split("rest")[1].split("\\/")[1];
				String nomeEndPoint = metodoClasseRest.getName();
				String metodo = target.getRequest().getMethod();
				
				perfilDoUsuario.getListaEndpoints()
						.stream()
						.filter(endpoint -> endpoint.getBolAtivo())
						.filter(endpoint -> endpoint.getNomeRecurso().equalsIgnoreCase(recurso) )
						.filter(endpoint -> endpoint.getNomeEndpoint().equalsIgnoreCase(nomeEndPoint))
						.filter(endpoint -> endpoint.getNomeMetodo().equalsIgnoreCase(metodo))
						.findAny()
						.orElseThrow(() -> new AcessoNegadoException("Você não tem acesso ao serviço."));					
			}
			
	}

	/**
	 * @param headers
	 * @return
	 * @throws NegocioException
	 */
	private String recuperarToken(HttpHeaders headers) throws NegocioException {
		String token = headers.getHeaderString(Constantes.TAG_TOKEN);

		if (token == null) {
			throw new NegocioException("Requisição sem token");
		}

		if (!token.startsWith("Bearer")) {
			throw new NegocioException("Requisição sem token no header apropriado");
		}

		if (token.split(" ").length != 2) {
			throw new NegocioException("Requisição sem token no header correto");
		}
		return token.split(" ")[1];
	}

	/**
	 * @param token
	 * @param contexto
	 * @param ehLogin
	 */
	private void validarToken(String token, InvocationContext contexto) throws AcessoNegadoException {
		boolean permitAll = contexto.getMethod().isAnnotationPresent(PermitAll.class);

		if (!permitAll && naoHaToken(token)) {
			throw new AcessoNegadoException("Requisição sem token de acesso");
		}

		if (!permitAll && !new TokenManager().isTokenValido(token)) {
			throw new AcessoNegadoException("Acesso inválido! Token vencido ou inválido!");
		}

	}

	private boolean naoHaToken(String token) {
		return token == null || token.isEmpty();
	}
	
	public static final Usuario getUsuarioLogadoSoComId() {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(usuarioLogado.getIdUsuario());
		return usuario;
	}

}
