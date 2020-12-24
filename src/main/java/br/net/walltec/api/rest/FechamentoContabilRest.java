/**
 * 
 */
package br.net.walltec.api.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.FechamentoMensalDTO;
import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.FechamentoContabilService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.comum.RetornoRestDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import io.swagger.annotations.Api;

/**
 * @author wallace
 *
 */
@Named
@RequestScoped

@Path("/fechamentos-contabeis")
@Produces(value={MediaType.APPLICATION_JSON})
@Consumes(value={MediaType.APPLICATION_JSON})
@Interceptors({RequisicaoInterceptor.class})
@Api(value="Webservice de fechamentos contábeis")
@SuppressWarnings("serial")
public class FechamentoContabilRest extends RequisicaoRestPadrao<FechamentoContabil> {
	
	@Inject
	private FechamentoContabilService service;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getServico()
	 */
	@Override
	public CrudPadraoService<FechamentoContabil> getServico() {
		return service;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getClasseEntidade()
	 */
	@Override
	protected Class<FechamentoContabil> getClasseEntidade() {
		// TODO Auto-generated method stub
		return FechamentoContabil.class;
	}
	
	@PermitAll
	@POST
	@Path("/fechar-mes")
	public RetornoRestDTO<FechamentoContabil> fecharMes(FechamentoMensalDTO dto) {
			FechamentoContabil fechamento = new FechamentoContabil();
			fechamento.setDataFechamento(LocalDateTime.now());
			fechamento.setNumAno(dto.getNumAno());
			fechamento.setNumMes(dto.getNumMes());
			return super.salvar(fechamento);
	}
	
	@PermitAll
	@GET
	@Path("/por-mes-ano/mes/{mes}/ano/{ano}")
	public RetornoRestDTO<FechamentoContabil> pesquisarPorMesAno(
			@PathParam("mes") Integer mes,			
			@PathParam("ano") Integer ano
			) {
		try {
			FechamentoContabil fechamento = this.service.obterPorMesAno(ano, mes);
			return new RetornoRestDTO<FechamentoContabil>().comEsteCodigo(Status.OK)
					.comEsteRetorno(fechamento)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<FechamentoContabil>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<FechamentoContabil>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}
}
