package br.net.walltec.api.negocio.servicos.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.enums.EnumOperadorFiltro;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.PersistenciaException;
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

	@Override
	public FechamentoContabil obterPorMesAno(Integer ano, Integer mes, Usuario usuario) throws NegocioException {
		List<FiltroConsulta> listaFiltros = new ArrayList<FiltroConsulta>();
		
		FiltroConsulta filtroInicial = new FiltroConsulta();
		filtroInicial.setNomeCampo("numAno");
		filtroInicial.setOperador(EnumOperadorFiltro.EQ);
		filtroInicial.setValor( ano.toString() );
		
		FiltroConsulta filtroFinal = new FiltroConsulta();
		filtroFinal.setNomeCampo("numMes");
		filtroFinal.setOperador(EnumOperadorFiltro.EQ);
		filtroFinal.setValor( mes.toString() );
		
		FiltroConsulta filtroUsuario = new FiltroConsulta();
		filtroUsuario.setNomeCampo("usuario");
		filtroUsuario.setOperador(EnumOperadorFiltro.EQ);
		filtroUsuario.setValor( usuario );
		
		listaFiltros = Arrays.asList(filtroInicial, filtroFinal, filtroUsuario);
		
		Pageable pageable = new PageRequest(0, 99999);
		
        try {
			PageResponse<List<FechamentoContabil>> pesquisa = dao.pesquisar(listaFiltros, pageable);
			if (pesquisa.isEmpty()) {
				return null;
			} else {
				List<FechamentoContabil> lista = pesquisa.getResultado();
				return lista.get(0);
			}
		} catch (PersistenciaException e) {
			throw new NegocioException(e);
		}
	}

    
	
}