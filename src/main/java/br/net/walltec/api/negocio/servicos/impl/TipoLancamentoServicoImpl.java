/**
 * 
 */
package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.TipoLancamentoService;
import br.net.walltec.api.persistencia.dao.TipoLancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

/**
 * @author wallace
 *
 */
@Named
public class TipoLancamentoServicoImpl extends AbstractCrudServicePadrao<TipoLancamento>
		implements TipoLancamentoService {

	@Inject
	private TipoLancamentoDao dao;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<TipoLancamento> getDao() {
		return dao;
	}

	
}
