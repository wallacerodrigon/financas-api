/**
 * 
 */
package br.net.walltec.api.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.WebServiceException;
import br.net.walltec.api.importacao.estrategia.ImportadorArquivo;
import br.net.walltec.api.importacao.estrategia.ImportadorBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCSVBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCefTxt;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.comum.RetornoRestDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import br.net.walltec.api.utilitarios.UtilData;
import io.swagger.annotations.Api;


/**
 * @author Administrador
 * 
 */

//TODO: atualizar as urls para n?o ter infinitivo e sim substantivos.
//TODO: Seguir a seguinte padroniza??o: PUT - atualiza; POST - cria;
@Path("/lancamentos")
@Produces(value=MediaType.APPLICATION_JSON)
@Interceptors({RequisicaoInterceptor.class})
@Api(value="Consultas de lançamentos")
public class LancamentosRest extends RequisicaoRestPadrao<Lancamento> {

	private static Map<String, ImportadorArquivo> mapImportadores = new HashMap<String, ImportadorArquivo>();

	private static final String FILE_TYPE_CSV = "xlsx";
	
	private static final String FILE_TYPE_CSV_LINUX = "csv";
	
	private static final String FILE_TYPE_TXT = "txt";
	
	static {
		mapImportadores.put("1_"+FILE_TYPE_TXT, new ImportadorBB());
		mapImportadores.put("1_"+FILE_TYPE_CSV, new ImportadorCSVBB());
		mapImportadores.put("1_"+FILE_TYPE_CSV_LINUX, new ImportadorCSVBB());
		mapImportadores.put("104_"+FILE_TYPE_TXT, new ImportadorCefTxt());
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private LancamentoService servico;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getServico()
	 */
	@Override
	public CrudPadraoService<Lancamento> getServico() {
		// TODO Auto-generated method stub
		return servico;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getClasseEntidade()
	 */
	@Override
	protected Class<Lancamento> getClasseEntidade() {
		// TODO Auto-generated method stub
		return Lancamento.class;
	}
	
	@GET
	@Path("/filtrar/mes/{mes}/ano/{ano}")
	public RetornoRestDTO<PageResponse<List<Lancamento>>> listarLancamentos(@PathParam("mes") Integer mes, @PathParam("ano") Integer ano) {
		try {
			PageResponse<List<Lancamento>> listaLancamentos = this.servico.filtrarLancamentos(mes, ano);
			
			listaLancamentos.getResultado().stream().forEach(lanc -> lanc.setDataVencimentoString(UtilData.getDataFormatada(lanc.getDataVencimento())));
			
			return new RetornoRestDTO<PageResponse<List<Lancamento>>>().comEsteCodigo(Status.OK)
					.comEsteRetorno(listaLancamentos)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<PageResponse<List<Lancamento>>>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<PageResponse<List<Lancamento>>>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	@POST
	@Path("/excluir-lote")
	public RetornoRestDTO excluirEmLote(List<Integer> idsLancamentos) {
		try {
			this.servico.excluirParcelas(idsLancamentos);
			return new RetornoRestDTO().comEsteCodigo(Status.NO_CONTENT)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}
	
	@PUT
	@Path("/baixar-lote")
	public RetornoRestDTO baixarEmLote(List<Integer> idsLancamentos) {
		try {
			this.servico.baixarParcelas(idsLancamentos);
			return new RetornoRestDTO().comEsteCodigo(Status.OK)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}

	@POST
	@Path("/importar-arquivo")
	public void importarArquivo() {
		
	}
	
	@POST
	@Path("/upload-documento")
	public void efetuarUploadDocumento() {
		
	}
	
	@GET
	@Path("/download-documento")
	public void efetuarDownloadDocumento() {
		
	}
	
	@POST
	@Path("/gerar-lote")
	public void gerarLoteLancamentos() {
		
	}
	
	@GET
	@Path("/totalizador/resumo-anual/{ano}/ano")
	public void listarResumoPorMesEAno() {
		//retornar um dto com: ano, array de : mes - valor total
	}
	
	//estatisticas
	@GET
	@Path("/totalizador/por-mes-ano")
	public void listarLancamentosPorMesAno() {
		//retornar um objeto com todos os dados para gerar estatisticas a seguir.
		
		//TipoLancmaento
		//TipoDespesa (c ou D)
		//liquidado ou nao
		//forma de pagamento
	}
	
	
}


