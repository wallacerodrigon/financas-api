package br.net.walltec.api.persistencia.dao.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.enums.EnumOperadorFiltro;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;
import br.net.walltec.api.utilitarios.UtilData;

@Named
public class LancamentoDaoImpl extends AbstractPersistenciaPadraoDao<Lancamento> implements LancamentoDao {

	public PageResponse<List<Lancamento>> listarParcelas(Date dataInicial, Date dataFinal) {
		
		List<FiltroConsulta> listaFiltros = new ArrayList<FiltroConsulta>();
		
		FiltroConsulta filtroInicial = new FiltroConsulta();
		filtroInicial.setNomeCampo("dataVencimento");
		filtroInicial.setOperador(EnumOperadorFiltro.DATA_INICIAL_MAIOR_QUE);
		filtroInicial.setValor( UtilData.getDataFormatada(dataInicial, UtilData.PATTERN_DATA_ISO) );
		
		FiltroConsulta filtroFinal = new FiltroConsulta();
		filtroFinal.setNomeCampo("dataVencimento");
		filtroFinal.setOperador(EnumOperadorFiltro.DATA_FINAL_MENOR_QUE);
		filtroFinal.setValor( UtilData.getDataFormatada(dataFinal,  UtilData.PATTERN_DATA_ISO) );
		listaFiltros = Arrays.asList(filtroInicial, filtroFinal);
		
		Pageable pageable = new PageRequest(0, 99999);
		
        try {
			return this.pesquisar(listaFiltros, pageable);
		} catch (PersistenciaException e) {
			throw new IllegalArgumentException(e);
		} 
	}

	

	
}
