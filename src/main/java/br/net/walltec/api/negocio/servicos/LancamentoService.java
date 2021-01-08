/**
 * 
 */
package br.net.walltec.api.negocio.servicos;


import java.util.List;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;

/**
 * @author Wallace
 *
 */
public interface LancamentoService extends CrudPadraoService<Lancamento> {
	
	boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException;

	boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException;
	
	PageResponse<List<Lancamento>> filtrarLancamentos(Integer mes, Integer ano) throws NegocioException;
	
	void importarArquivo(ImportadorArquivoDTO importadorDto) throws NegocioException;

	void dividirLancamento(DivisaoLancamentoDTO dto) throws NegocioException;
}
