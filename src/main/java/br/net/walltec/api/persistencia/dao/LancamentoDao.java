/**
 *
 */
package br.net.walltec.api.persistencia.dao;

import java.util.Date;
import java.util.List;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

/**
 * @author Wallace
 */
public interface LancamentoDao extends PersistenciaPadraoDao<Lancamento> {

	PageResponse<List<Lancamento>> listarParcelas(Date dataInicial, Date dataFinal, TipoLancamento tipoLancamento, Integer idUsuario);

	List<Lancamento> listarLancamentosDoAno(Integer ano, Integer idUsuario);
	
	Lancamento recuperarPeloCodBarras(String numCodBarras) throws NegocioException;
}
