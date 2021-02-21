package br.net.walltec.api.persistencia.dao.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;
import br.net.walltec.api.persistencia.dao.comum.ParametrosBuilder;
import br.net.walltec.api.utilitarios.UtilData;
import br.net.walltec.api.utilitarios.UtilObjeto;

@Named
public class LancamentoDaoImpl extends AbstractPersistenciaPadraoDao<Lancamento> implements LancamentoDao {

	public PageResponse<List<Lancamento>> listarParcelas(Date dataInicial, Date dataFinal,  TipoLancamento tipoLancamento, Integer idUsuario) {
		
		ParametrosBuilder parametros = new ParametrosBuilder()
				.addParametro("dataInicial", dataInicial)
				.addParametro("dataFinal", dataFinal)
				.addParametro("idUsuario", idUsuario);
		
		
		StringBuilder builder = new StringBuilder("from Lancamento ");
		
		builder.append("where dataVencimento between :dataInicial and :dataFinal");
		builder.append("  and usuario.idUsuario = :idUsuario ");
		
		if (UtilObjeto.isNotVazio(tipoLancamento)) {
			builder.append(" and tipoLancamento = :tipoLancamento ");
			parametros.addParametro("tipoLancamento", tipoLancamento);
		}
		
		builder.append("order by dataVencimento");
		
		Pageable pageable = new PageRequest(0, 99999);
        try {
			return this.pesquisar(builder, pageable, parametros);
		} catch (PersistenciaException e) {
			throw new IllegalArgumentException(e);
		} 
	}

	@Override
	public List<Lancamento> listarLancamentosDoAno(Integer ano, Integer idUsuario) {
		
		Date dataInicial = UtilData.createDataSemHoras(1, 1, ano);
		Date dataFinal = UtilData.createDataSemHoras(31, 12, ano);
		
		StringBuilder builder = new StringBuilder("from Lancamento ");
		
		builder.append("where dataVencimento between :dataInicial and :dataFinal");
		builder.append("  and usuario.idUsuario = :idUsuario ");
		builder.append("order by dataVencimento");
		
		Pageable pageable = new PageRequest(0, 99999);
		
		ParametrosBuilder parametros = new ParametrosBuilder()
				.addParametro("dataInicial", dataInicial)
				.addParametro("dataFinal", dataFinal)
				.addParametro("idUsuario", idUsuario);
		
        try {
        	PageResponse<List<Lancamento>> response = this.pesquisar(builder, pageable, parametros);
        	
        	return response.getResultado();
        	
		} catch (PersistenciaException e) {
			throw new IllegalArgumentException(e);
		}	}

	/**
	 * @param ano
	 * @param response
	 * @return
	 */
	

	
}
