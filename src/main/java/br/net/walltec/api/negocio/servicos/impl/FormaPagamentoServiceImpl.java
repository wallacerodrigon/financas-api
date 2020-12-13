package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.FormaPagamentoService;
import br.net.walltec.api.persistencia.dao.FormaPagamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class FormaPagamentoServiceImpl extends AbstractCrudServicePadrao<FormaPagamento> implements FormaPagamentoService {

	@Inject
	private FormaPagamentoDao dao;
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<FormaPagamento> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}