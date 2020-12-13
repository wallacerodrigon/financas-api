package br.net.walltec.api.negocio.servicos.impl;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.enums.EnumOperadorFiltro;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoService;
import br.net.walltec.api.persistencia.dao.DeparaHistoricoBancoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

@Named
public class DeparaHistoricoBancoServicoImpl extends AbstractCrudServicePadrao<DeparaHistoricoBanco> implements DeparaHistoricoBancoService {

    @Inject
    private DeparaHistoricoBancoDao dao;
    
    private Logger log = Logger.getLogger(this.getClass().getName());

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<DeparaHistoricoBanco> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

    
	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoServico#listaPorBanco(java.lang.Integer)
	 */
	@Override
	public List<DeparaHistoricoBanco> listaPorBanco(Integer numBanco) throws NegocioException {
		try {
			FiltroConsulta filtro = new FiltroConsulta();
			filtro.setNomeCampo("numBanco");
			filtro.setOperador(EnumOperadorFiltro.EQ);
			filtro.setValor(numBanco.toString());
			return dao.pesquisar(Arrays.asList(filtro), new PageRequest(0, Integer.MAX_VALUE)).getResultado();
		} catch (PersistenciaException e) {
			throw new NegocioException(e.getMessage());
		}
	}


    
	
}