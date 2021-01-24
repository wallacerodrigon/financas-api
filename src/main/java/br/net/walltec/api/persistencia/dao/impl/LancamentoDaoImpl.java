package br.net.walltec.api.persistencia.dao.impl;


import java.util.Date;
import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;
import br.net.walltec.api.persistencia.dao.comum.ParametrosBuilder;

@Named
public class LancamentoDaoImpl extends AbstractPersistenciaPadraoDao<Lancamento> implements LancamentoDao {

	public PageResponse<List<Lancamento>> listarParcelas(Date dataInicial, Date dataFinal, Integer idUsuario) {
		
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
			return this.pesquisar(builder, pageable, parametros);
		} catch (PersistenciaException e) {
			throw new IllegalArgumentException(e);
		} 
	}

	
}
