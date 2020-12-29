package br.net.walltec.api.rest.interceptors;



import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.NDC;

import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.comum.RolesSistema;
import br.net.walltec.api.dto.UsuarioLogadoDTO;
import br.net.walltec.api.entidades.Endpoint;
import br.net.walltec.api.excecoes.AcessoNegadoException;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.EndpointService;
import br.net.walltec.api.negocio.servicos.impl.UsuarioServicoImpl;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.tokens.TokenManager;
import br.net.walltec.api.utilitarios.Constantes;


@Named
@Interceptor

/**
 * Interceptador para todas as requisições
 * */
public class RequisicaoInterceptor {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Inject
	private EndpointService endPointService;
	
	
	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object verificarRequisicao(InvocationContext contexto) throws Exception {
		log.info("Executando interceptor no método " + contexto.getMethod().getName() + " classe: " + contexto.getClass().getName());
		
		try {
//			PageResponse<List<Endpoint>> listar = this.endPointService.listar(new PageRequest(0, 10));
//			System.out.println(listar);
			
			if (contexto.getTarget() != null){
				RequisicaoRestPadrao target = (RequisicaoRestPadrao)contexto.getTarget();
				HttpHeaders headers = target.getHeaders();
				if (! contexto.getMethod().isAnnotationPresent(PermitAll.class)) {
					String token = recuperarToken(headers);
					//validarToken(token, contexto); //valida autenticação
					//validarAutorizacao(token, contexto.getMethod()); //valida autorização
					
				}
				log.info("Finalizando interceptor");
				return contexto.proceed();
			} else {
				log.info("Finalizando interceptor");
				return null;
			}
		} catch(AcessoNegadoException e) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		} catch(Exception e) {
			throw new WebApplicationException(e.getMessage());
		}
	}

	/**
	 * @param target
	 * @param method
	 */
	private void validarAutorizacao(String token, Method metodo) throws AcessoNegadoException {
		if (! metodo.isAnnotationPresent(PermitAll.class)  ) {
			RolesAllowed rolesPermitidasParaOMetodo = metodo.getAnnotation(RolesAllowed.class);
			TokenManager tokenManager = new TokenManager();
			UsuarioLogadoDTO usuarioDto = tokenManager.getUsuarioFromToken(token);
			if (usuarioDto != null && 
				! Stream.of(rolesPermitidasParaOMetodo.value()).filter(role -> role.equalsIgnoreCase(RolesSistema.AUTENTICADO)).findAny().isPresent())
			{
				Stream.of(rolesPermitidasParaOMetodo.value()).filter(role -> role.equalsIgnoreCase(usuarioDto.getPerfil().getNomePerfil())).findAny()
				.orElseThrow(() -> new AcessoNegadoException("Você não tem acesso ao serviço."));
			}
			
		} else if (metodo.isAnnotationPresent(DenyAll.class)) {
			throw new AcessoNegadoException("Acesso negado ao serviço");
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
		
		if (!permitAll && naoHaToken(token) ){
			throw new AcessoNegadoException("Requisição sem token de acesso");
		}
		
		if (!permitAll &&  !new TokenManager().isTokenValido(token)){
			throw new AcessoNegadoException("Acesso inválido! Token vencido ou inválido!");
		}
		
	}
	
	private boolean naoHaToken(String token){
		return token == null || token.isEmpty();		
	}
	
}
