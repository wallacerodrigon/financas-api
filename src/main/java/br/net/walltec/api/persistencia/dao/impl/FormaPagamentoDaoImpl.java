package br.net.walltec.api.persistencia.dao.impl;


import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.FormaPagamentoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;

@Named
public class FormaPagamentoDaoImpl extends AbstractPersistenciaPadraoDao<FormaPagamento> implements FormaPagamentoDao {


	/* (non-Javadoc)
	 * @see br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao#pesquisar(java.util.List, br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<FormaPagamento>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable)
			throws PersistenciaException {
		List<FormaPagamento> lista = this.listarComCache(pageable);
		return new PageResponse<List<FormaPagamento>>(0, lista.size(), lista.size(), 1, null, lista);
	}
}
