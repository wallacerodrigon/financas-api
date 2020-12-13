package br.net.walltec.api.rest.comum;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonSyntaxException;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.RolesSistema;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.WebServiceException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.dto.filtro.FiltroRelatorioDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import br.net.walltec.api.validadores.ValidadorDados;

@Produces(value = { MediaType.APPLICATION_JSON })
@Interceptors({ RequisicaoInterceptor.class })
@Dependent
public abstract class RequisicaoRestPadrao<T> implements ContratoPadraoRest<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected java.util.logging.Logger log = Logger.getLogger(this.getClass().getName());

	@Context
	private HttpHeaders headers;

	@Context
	private HttpServletResponse response;

	@Context
	private HttpServletRequest request;

	@PostConstruct
	public void init() {
		// executado antes do web service e depois do interceptor
		log.info("Init do método requisicao rest padrao");
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public abstract CrudPadraoService<T> getServico();

	protected void configurarFiltro(FiltroRelatorioDTO filtro, HttpServletRequest req, HttpServletResponse response) {
		filtro.setRequest(req);
		filtro.setResponse(response);

	}

	public void addHeader(String name, String value) {
		if (this.response != null) {
			response.addHeader(name, value);
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.walltec.api.rest.comum.ContratoPadraoRest#novo()
	 */
	@Override
	public RetornoRestDTO<T> novo() throws WebServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#excluir(java.lang.Integer)
	 */
	@Override
	public RetornoRestDTO<T> excluir(@PathParam("idChaveObjeto") Integer idChaveObjeto) throws WebServiceException {

		try {
			if (this.getServico().find(idChaveObjeto) == null) {
				return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST)
						.comEstaMensagem("Registro não encontrado com esse id informado.").construir();
			}
			this.getServico().excluir(idChaveObjeto);
			return new RetornoRestDTO<T>().comEsteCodigo(Status.NO_CONTENT).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#procurar(java.lang.Integer)
	 */
	@Override
	public RetornoRestDTO<T> procurar(@PathParam("idProcura") Integer idProcura) throws WebServiceException {
		try {
			T resultado = this.getServico().find(idProcura);
			if (resultado == null) {
				return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST)
						.comEstaMensagem("Registro não encontrado com esse id informado.").construir();
			}
			return new RetornoRestDTO<T>().comEsteCodigo(Status.OK).comEsteRetorno(resultado).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#listar(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public RetornoRestDTO<PageResponse<List<T>>> listar(@QueryParam("page") Integer page,
			@QueryParam("size") Integer size) throws WebServiceException {
		try {
			PageResponse<List<T>> retorno = getServico().listar(new PageRequest(page, size));
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.OK).comEsteRetorno(retorno)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage()).construir();
		} catch (Exception e) {
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#salvar(java.lang.Object)
	 */
	@Override
	public RetornoRestDTO<T> salvar(T objeto) throws WebServiceException {
		try {
			ValidadorDados.validarDadosEntrada(objeto);

			this.getServico().incluir(objeto);
			return new RetornoRestDTO<T>().comEsteCodigo(Status.CREATED)
					.comEstaMensagem(getServico().getIdObjeto(objeto).toString()).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#alterar(java.lang.Object)
	 */
	@Override
	public RetornoRestDTO<T> alterar(T objeto) throws WebServiceException {
		try {
			ValidadorDados.validarDadosEntrada(objeto);
			this.getServico().alterar(objeto);
			return new RetornoRestDTO<T>().comEsteCodigo(Status.OK).comEsteRetorno(objeto).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<T>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	/*
	 * (non-Javadoc) Pesquisa a entidade com os dados do filtro informado em formato
	 * JSON com os nomes dos atributos e seus devidos valores, conforme a entidade
	 * 
	 * @see
	 * br.net.walltec.api.rest.comum.ContratoPadraoRest#pesquisar(java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	// TODO: montar expressao regular com uma linguagem para poder fazer os filtros
	// com: igual, like, likeInicio, likeFim, maiorQue, menorQue, entre, contem, E,
	// OU, NAO, naoContem...
	@Override
	public RetornoRestDTO<PageResponse<List<T>>> pesquisar(@QueryParam(value = "filtroJSON") String filtro,
			@QueryParam(value = "page") Integer page, @QueryParam("size") Integer size) throws WebServiceException {
		try {
			List<FiltroConsulta> filtrosParsed = new FiltroConsulta().efetuarParse(filtro, getClasseEntidade());
			PageResponse<List<T>> retorno = getServico().pesquisar(filtrosParsed, new PageRequest(page, size));
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.OK).comEsteRetorno(retorno)
					.construir();
		} catch (JsonSyntaxException e) {
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.BAD_REQUEST)
					.comEstaMensagem("Filtro contém campos inválidos").construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage()).construir();
		} catch (Exception e) {
			return new RetornoRestDTO<PageResponse<List<T>>>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	/**
	 * @return
	 */
	protected abstract Class<T> getClasseEntidade();

	
	public boolean isLocalHost() {
		return getRequest().getServerName().contains("localhost");			
	}
}
