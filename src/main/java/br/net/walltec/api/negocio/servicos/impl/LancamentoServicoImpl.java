package br.net.walltec.api.negocio.servicos.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.CampoObrigatorioException;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.persistencia.dao.FechamentoContabilDao;
import br.net.walltec.api.persistencia.dao.FormaPagamentoDao;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;
import br.net.walltec.api.utilitarios.UtilData;
import br.net.walltec.api.utilitarios.UtilObjeto;

@Named
public class LancamentoServicoImpl extends AbstractCrudServicePadrao<Lancamento>
		implements LancamentoService {

	@Inject
	private LancamentoDao lancamentoDao;

	@Inject
	private FechamentoContabilDao fechamentoContabilDao;

	@Inject
	private FormaPagamentoDao formaPagamentoDao;

	private Logger log = Logger.getLogger(this.getClass().getName());

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao#getDao()
	 */
	@Override
	public PersistenciaPadraoDao<Lancamento> getDao() {
		return lancamentoDao;
	}

	

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#baixarParcelas(java.util.List)
	 */
	@Override
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRED)
	public boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException {
		
		this.getLancamentosPorIds(idsLancamentos)
		.stream()
		.filter(lancamento -> !lancamento.isPago())
		.forEach(lancamento -> {
			lancamento.setDataHoraPagamento(LocalDateTime.now());
			lancamento.setDataVencimentoString(UtilData.getDataFormatada(new Date()));
			try {
				this.alterar(lancamento);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		return true;
	}
	
	private List<Lancamento> getLancamentosPorIds(List<Integer> idsLancamentos) throws NegocioException {
		if (UtilObjeto.isVazio(idsLancamentos)) {
			throw new CampoObrigatorioException("Ids não informados");
		}
		
		return idsLancamentos.stream().map(id -> {
			try {
				return this.find(id);
			} catch (NegocioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		})
		.filter(lancamento -> lancamento != null)
		.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#excluirParcelas(java.util.List)
	 */
	@Override
	public boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException {
		idsLancamentos
		.forEach(id -> {
			try {
				this.excluir(id);
			} catch (NegocioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		});
		return true;
	}



	@Override
	public PageResponse<List<Lancamento>> filtrarLancamentos(Integer mes, Integer ano) throws NegocioException {
		
		if (UtilObjeto.isVazio(mes) || UtilObjeto.isVazio(ano) ) {
			throw new CampoObrigatorioException("Mës ou ano não informados");
		}
		
		Date dataInicial = UtilData.createDataSemHoras(1, mes, ano);
		Date dataFinal = UtilData.getUltimaDataMes(dataInicial);
		
		try {
			return lancamentoDao.listarParcelas(dataInicial, dataFinal);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}

	
}