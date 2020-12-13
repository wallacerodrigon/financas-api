package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.BancoService;
import br.net.walltec.api.persistencia.dao.BancoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class BancoServiceImpl extends AbstractCrudServicePadrao<Banco> implements BancoService {


	@Inject
	private BancoDao dao;
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<Banco> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}