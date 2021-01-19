/**
 * 
 */
package br.net.walltec.api.rest;

import java.util.List;

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

import org.apache.commons.beanutils.BeanUtils;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.WebServiceException;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.comum.RetornoRestDTO;
import br.net.walltec.api.rest.dto.AlteracaoLancamentoDTO;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;
import br.net.walltec.api.rest.dto.InclusaoLancamentoDTO;
import br.net.walltec.api.rest.dto.LancamentosConsultaDTO;
import br.net.walltec.api.rest.dto.UploadDocumentoDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import br.net.walltec.api.utilitarios.UtilData;
import br.net.walltec.api.validadores.ValidadorDados;
import io.swagger.annotations.Api;


/**
 * @author Administrador
 * 
 */

@Path("/lancamentos")
@Produces(value=MediaType.APPLICATION_JSON)
@Interceptors({RequisicaoInterceptor.class})
@Api(value="Consultas de lançamentos")
public class LancamentosRest extends RequisicaoRestPadrao<Lancamento> {


	
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
	public RetornoRestDTO<PageResponse<List<LancamentosConsultaDTO>>> listarLancamentos(@PathParam("mes") Integer mes, @PathParam("ano") Integer ano) {
		try {
			PageResponse<List<LancamentosConsultaDTO>> listaLancamentos = this.servico.filtrarLancamentos(mes, ano, RequisicaoInterceptor.usuarioLogado.getIdUsuario());
			
			return new RetornoRestDTO<PageResponse<List<LancamentosConsultaDTO>>>().comEsteCodigo(Status.OK)
					.comEsteRetorno(listaLancamentos)
					.construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO<PageResponse<List<LancamentosConsultaDTO>>>().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO<PageResponse<List<LancamentosConsultaDTO>>>().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
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
	@Path("/importar-arquivo-bancario")
	public RetornoRestDTO importarArquivo(ImportadorArquivoDTO importador) {
		try {
			ValidadorDados.validarDadosEntrada(importador);
			this.servico.importarArquivo(importador, RequisicaoInterceptor.usuarioLogado.getIdUsuario());
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
	@Path("/upload-documento")
	public RetornoRestDTO efetuarUploadDocumento(UploadDocumentoDTO dto) {
		try {
			ValidadorDados.validarDadosEntrada(dto);
			this.servico.efetuarUploadArquivo(dto);
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
	
	@GET
	@Path("/download-documento/{idLancamento}")
	public RetornoRestDTO efetuarDownloadDocumento(@PathParam("idLancamento") Integer idLancamento) {
		try {
			String retorno = this.servico.efetuarDownloadArquivo(idLancamento);
			return new RetornoRestDTO().comEsteCodigo(Status.OK).comEsteRetorno(retorno)
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

	@POST
	@Path("/dividir-lancamento")
	public RetornoRestDTO dividirLancamento(DivisaoLancamentoDTO dto) {
		try {
			this.servico.dividirLancamento(dto);
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
	
	@Override
	public RetornoRestDTO<Lancamento> salvar(Lancamento objeto) throws WebServiceException {
		return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem("serviço não habilitado")
				.construir();
	}
	
	@Override
	public RetornoRestDTO<Lancamento> alterar(Lancamento objeto) throws WebServiceException {
		return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem("serviço não habilitado")
				.construir();
	}
	
	@POST
	@Path("/incluir")
	public RetornoRestDTO<Lancamento> incluir(InclusaoLancamentoDTO objeto) throws WebServiceException {
		try {
			ValidadorDados.validarDadosEntrada(objeto);

			Lancamento lancamento = new Lancamento();
			BeanUtils.copyProperties(lancamento, objeto);
			lancamento.setDataVencimento(UtilData.getDataPorPattern(objeto.getDataVencimentoString(), UtilData.PATTERN_DATA_ISO));
			lancamento.setUsuario(RequisicaoInterceptor.getUsuarioLogadoSoComId());

			this.getServico().incluir(lancamento);
			return new RetornoRestDTO<Lancamento>().comEsteCodigo(Status.CREATED)
					.comEstaMensagem(getServico().getIdObjeto(lancamento).toString()).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}
	
	@PUT
	@Path("/alterar")	
	public RetornoRestDTO<Lancamento> alterar(AlteracaoLancamentoDTO objeto) throws WebServiceException {
		try {
			ValidadorDados.validarDadosEntrada(objeto);
			Lancamento lancamento = new Lancamento();
			BeanUtils.copyProperties(lancamento, objeto);
			lancamento.setDataVencimento(UtilData.getDataPorPattern(objeto.getDataVencimentoString(), UtilData.PATTERN_DATA_ISO));

			this.getServico().alterar(lancamento);
			return new RetornoRestDTO<Lancamento>().comEsteCodigo(Status.OK).comEsteRetorno(objeto).construir();
		} catch (NegocioException e) {
			return new RetornoRestDTO().comEsteCodigo(Status.BAD_REQUEST).comEstaMensagem(e.getMessage())
					.construir();
		} catch (Exception e) {
			return new RetornoRestDTO().comEsteCodigo(Status.INTERNAL_SERVER_ERROR).comEstaMensagem(e.getMessage())
					.construir();
		}
	}
	
}


