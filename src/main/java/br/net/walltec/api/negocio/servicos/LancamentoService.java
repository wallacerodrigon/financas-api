/**
 * 
 */
package br.net.walltec.api.negocio.servicos;


import java.util.List;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.dto.GeracaoLancamentosDTO;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;
import br.net.walltec.api.rest.dto.LancamentosConsultaDTO;
import br.net.walltec.api.rest.dto.UploadDocumentoDTO;

/**
 * @author Wallace
 *
 */
public interface LancamentoService extends CrudPadraoService<Lancamento> {
	
	boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException;

	boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException;
	
	PageResponse<List<LancamentosConsultaDTO>> filtrarLancamentos(Integer mes, Integer ano, Integer idUsuario) throws NegocioException;
	
	void importarArquivo(ImportadorArquivoDTO importadorDto, Integer idUsuario) throws NegocioException;

	void dividirLancamento(DivisaoLancamentoDTO dto) throws NegocioException;
	
	void efetuarUploadArquivo(UploadDocumentoDTO dto) throws NegocioException;
	
	String efetuarDownloadArquivo(Integer idLancamento) throws NegocioException;

	void gerarLoteLancamentos(GeracaoLancamentosDTO dto) throws NegocioException;
}
