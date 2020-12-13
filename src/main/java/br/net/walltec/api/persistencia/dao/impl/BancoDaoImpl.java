package br.net.walltec.api.persistencia.dao.impl;


import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.BancoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;

@Named
public class BancoDaoImpl extends AbstractPersistenciaPadraoDao<Banco> implements BancoDao {

	/* (non-Javadoc)
	 * @see br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao#pesquisar(java.util.List, br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<Banco>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable)
			throws PersistenciaException {
		List<Banco> bancos = this.listarComCache(pageable);
		return new PageResponse<List<Banco>>(0, bancos.size(), bancos.size(), 1, null, bancos);
	}

}
