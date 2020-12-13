/**
 *
 */
package br.net.walltec.api.persistencia.dao;

import java.util.Date;
import java.util.List;

import br.net.walltec.api.dto.LancamentosPorRubricaDTO;
import br.net.walltec.api.dto.ResumoLancamentosDTO;
import br.net.walltec.api.dto.ResumoMesAnoDTO;
import br.net.walltec.api.dto.UtilizacaoLancamentoDTO;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

/**
 * @author Wallace
 */
public interface LancamentoDao extends PersistenciaPadraoDao<Lancamento> {

	List<Lancamento> listarParcelas(Date dataInicial, Date dataFinal, Integer idConta, Integer idParcelaOrigem) throws PersistenciaException;

    ResumoLancamentosDTO obterResumoLancamentos(Integer mes, Integer ano) throws PersistenciaException;

    List<UtilizacaoLancamentoDTO> listarUsos(Integer idLancamentoOrigem) throws PersistenciaException;
    
    boolean associarLancamentoComExtrato(Integer idLancamento, String numDocumento, Date dataVencimento) throws PersistenciaException;

    List<ResumoMesAnoDTO> gerarMapaAno(Integer ano) throws PersistenciaException;
    
    List<LancamentosPorRubricaDTO> listarLancamentosPorRubricaEAno(Integer ano, Integer idRubrica) throws PersistenciaException;
    
    void excluirParcelasPagasDebitoPorPeriodo(Date dataInicio, Date dataFim) throws PersistenciaException;
}
