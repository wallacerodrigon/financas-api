package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.FechamentoContabilService;
import br.net.walltec.api.persistencia.dao.FechamentoContabilDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class FechamentoContabilServiceImpl extends AbstractCrudServicePadrao<FechamentoContabil> implements FechamentoContabilService {

	@Inject
	private FechamentoContabilDao dao;
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<FechamentoContabil> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

    
	
}