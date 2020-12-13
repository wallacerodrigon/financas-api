/**
 * 
 */
package br.net.walltec.api.rest;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.importacao.estrategia.ImportadorArquivo;
import br.net.walltec.api.importacao.estrategia.ImportadorBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCSVBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCefTxt;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
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

	@POST
	@Path("/excluir-lote")
	public void excluirEmLote() {
		
	}
	
	@PUT
	@Path("/baixar-lote")
	public void baixarEmLote() {
		
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
	@Path("/totalizador/resumo-anual")
	public void listarResumoPorMesEAno() {
		
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


