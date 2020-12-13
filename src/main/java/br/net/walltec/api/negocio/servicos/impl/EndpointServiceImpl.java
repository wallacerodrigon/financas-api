package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.Endpoint;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.EndpointService;
import br.net.walltec.api.persistencia.dao.EndpointDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class EndpointServiceImpl extends AbstractCrudServicePadrao<Endpoint> implements EndpointService {


	@Inject
	private EndpointDao dao;
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<Endpoint> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}