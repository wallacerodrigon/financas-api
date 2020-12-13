package br.net.walltec.api.persistencia.dao.impl;


import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.TipoLancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;

@Named
public class TipoLancamentoDaoImpl extends AbstractPersistenciaPadraoDao<TipoLancamento> implements TipoLancamentoDao {

	/* (non-Javadoc)
	 * @see br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao#pesquisar(java.util.List, br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<TipoLancamento>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable)
			throws PersistenciaException {
		List<TipoLancamento> lista = this.listarComCache(pageable);
		return new PageResponse<List<TipoLancamento>>(0, lista.size(), lista.size(), 1, null, lista);
	}

}
