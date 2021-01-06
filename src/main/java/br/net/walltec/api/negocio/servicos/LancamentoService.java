/**
 * 
 */
package br.net.walltec.api.negocio.servicos;


import java.util.Date;
import java.util.List;
import java.util.Set;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.ConsultaLancamentosDTO;
import br.net.walltec.api.dto.FiltraParcelasDto;
import br.net.walltec.api.dto.GeracaoParcelasDto;
import br.net.walltec.api.dto.LancamentosPorRubricaDTO;
import br.net.walltec.api.dto.MapaDashboardDTO;
import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.dto.ResumoMesAnoDTO;
import br.net.walltec.api.dto.TipoContaNoMesDTO;
import br.net.walltec.api.dto.UtilizacaoLancamentoDTO;
import br.net.walltec.api.dto.UtilizacaoParcelasDto;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;
import br.net.walltec.api.rest.dto.filtro.DesfazimentoConciliacaoDTO;
import br.net.walltec.api.rest.dto.filtro.RegistroFechamentoMesDTO;

/**
 * @author Wallace
 *
 */
public interface LancamentoService extends CrudPadraoService<Lancamento> {
	
	boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException;

	boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException;
	
	PageResponse<List<Lancamento>> filtrarLancamentos(Integer mes, Integer ano) throws NegocioException;
	
	void importarArquivo(ImportadorArquivoDTO importadorDto) throws NegocioException;
}
