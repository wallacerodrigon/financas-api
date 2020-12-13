package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.Perfil;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.PerfilService;
import br.net.walltec.api.persistencia.dao.PerfilDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class PerfilServiceImpl extends AbstractCrudServicePadrao<Perfil> implements PerfilService {

	@Inject
	private PerfilDao dao;
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<Perfil> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}