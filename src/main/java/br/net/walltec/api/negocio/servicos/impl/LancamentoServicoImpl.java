package br.net.walltec.api.negocio.servicos.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.CampoObrigatorioException;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.importacao.estrategia.ImportadorArquivo;
import br.net.walltec.api.importacao.estrategia.ImportadorCSVBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCefTxt;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.FechamentoContabilService;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;
import br.net.walltec.api.utilitarios.UtilBase64;
import br.net.walltec.api.utilitarios.UtilData;
import br.net.walltec.api.utilitarios.UtilObjeto;

@Named
public class LancamentoServicoImpl extends AbstractCrudServicePadrao<Lancamento>
		implements LancamentoService {

	@Inject
	private LancamentoDao lancamentoDao;

	@Inject
	private FechamentoContabilService fechamentoContabilService;

	
	private static Map<String, ImportadorArquivo> mapImportadores = new HashMap<String, ImportadorArquivo>();

	private static final String FILE_TYPE_CSV = "csv";
	private static final String FILE_TYPE_TXT = "txt";
	
	static {
		mapImportadores.put("1_"+FILE_TYPE_CSV, new ImportadorCSVBB());
		mapImportadores.put("104_"+FILE_TYPE_TXT, new ImportadorCefTxt());
	}

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
		.forEach(lancamento -> {
			lancamento.setDataHoraPagamentoString(UtilData.getDataFormatada(new Date(), UtilData.PATTERN_DATA_ISO )); 
			lancamento.setDataVencimentoString(UtilData.getDataFormatada(lancamento.getDataVencimento(), UtilData.PATTERN_DATA_ISO));
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
	
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRED)
	@Override
	public void alterar(Lancamento objeto) throws NegocioException {
		// TODO Auto-generated method stub
		Lancamento l = this.find(objeto.getIdLancamento());
		l.setDataVencimento(UtilData.getDataPorPattern(objeto.getDataVencimentoString(), UtilData.PATTERN_DATA_ISO));
		
		
		if (l.isPago()) {
			throw new NegocioException("Lançamento pago, não pode ser alterado.");
		}
		
		if (objeto.getDataHoraPagamentoString() != null) {
			l.setDataHoraPagamento(
					UtilData.getDataPorPattern(objeto.getDataHoraPagamentoString(), UtilData.PATTERN_DATA_ISO));
		}
		
		try {
			l.setFormaPagamento(objeto.getFormaPagamento());
			l.setDataVencimentoString(objeto.getDataVencimentoString());
			l.setDescLancamento(objeto.getDescLancamento());
			l.setValorLancamento(objeto.getValorLancamento());
			this.lancamentoDao.alterar(l);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}
	
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRED)
	@Override
	public void incluir(Lancamento objeto) throws NegocioException {
		// TODO Auto-generated method stub
		objeto.setDataVencimento(UtilData.getDataPorPattern(objeto.getDataVencimentoString(), UtilData.PATTERN_DATA_ISO));
		if (objeto.getDataHoraPagamentoString() != null) {
			objeto.setDataHoraPagamento(
					UtilData.getDataPorPattern(objeto.getDataHoraPagamentoString(), UtilData.PATTERN_DATA_ISO));
		}
		try {
			this.lancamentoDao.incluir(objeto);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}



	@Override
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRES_NEW )
	public void importarArquivo(ImportadorArquivoDTO importadorDto) throws NegocioException {
	    FechamentoContabil fechamentoContabil = fechamentoContabilService.obterPorMesAno(importadorDto.getAno(),  importadorDto.getMes());
		
	    if (fechamentoContabil != null) {
	    	throw new NegocioException("Mês fechado, não poderá ter importação.");
	    }
	    
		String chaveImportador = importadorDto.getNumBanco() +"_" + importadorDto.getExtensaoArquivo();
		byte[] conteudoArquivo = UtilBase64.decodificarBase64(importadorDto.getDadosArquivoBase64());
		ImportadorArquivo importador = mapImportadores.get(chaveImportador);
		
		if (! importador.isExtensaoValida(importadorDto.getExtensaoArquivo())) {
			throw new NegocioException("Extensão inválida para esse banco.");
		}
		List<Lancamento> lancamentos = importador.importar(importadorDto.getNomeArquivo(), conteudoArquivo);
		
		Date dataBase = UtilData.createDataSemHoras(1, importadorDto.getMes(), importadorDto.getAno());
		Date dataInicialDoMes = UtilData.getPrimeiroDiaMes(dataBase);
		
		if (lancamentos.get(1).getDataVencimento().before(dataInicialDoMes)) {
			Date dataVencimento = lancamentos.get(1).getDataVencimento();
			throw new NegocioException("Arquivo inválido para este mês. Os vencimentos do arquivo são de " + UtilData.getMes(dataVencimento) + "/" + UtilData.getAno(dataVencimento));
		}
		
		
		Banco banco = lancamentos.get(0).getBanco();
		
		PageResponse<List<Lancamento>> response = this.filtrarLancamentos(importadorDto.getMes(), importadorDto.getAno());
		
		//listar os lancamentos que tem dependencias e manter na listagem somente os que nao tem
		List<Lancamento> lancamentosComDependencia = response.getResultado().stream()
				.filter(lanc -> lanc.getLancamentoOrigem() != null)
				.map(lanc -> lanc.getLancamentoOrigem())
				.distinct()
				.collect(Collectors.toList());
		
		excluirLancamentosPorFormaPagamento( response.getResultado(), 
				banco.getFormaPagamentoParaConciliacao(), true,
				lancamentosComDependencia);
		
		response = this.filtrarLancamentos(importadorDto.getMes(), importadorDto.getAno());
		List<Lancamento> lancamentosComDependenciaPosExclusao = response.getResultado().stream()
				.filter(lanc -> lanc.getLancamentoOrigem() != null)
				.map(lanc -> lanc.getLancamentoOrigem())
				.distinct()
				.collect(Collectors.toList());

		lancamentosComDependencia = lancamentosComDependencia
					.stream()
					.filter(lanc -> ! lancamentosComDependenciaPosExclusao.stream() 
										.anyMatch(lancDepExc -> lancDepExc.getIdLancamento().equals(lanc.getIdLancamento()))
							
							)
					.collect(Collectors.toList());
					
		
		excluirLancamentosPorFormaPagamento( lancamentosComDependencia, 
				banco.getFormaPagamentoParaConciliacao(), true,
				new ArrayList<Lancamento>());
		
		//lancmaentos com origem. Armazenar essas origens em separado e excluir depois se eles náo tiverem mais nenhuma referencia
		List<Lancamento> lancamentosOrigem = lancamentos.stream()
				.filter(lanc -> lanc.getLancamentoOrigem() != null)
				.map(lanc -> lanc.getLancamentoOrigem())
				.collect(Collectors.toList());
		
		lancamentos.forEach(lancamento -> {
			try {
				lancamento.setDataHoraPagamentoString(UtilData.getDataFormatada(lancamento.getDataHoraPagamento(), UtilData.PATTERN_DATA_ISO )); 
				lancamento.setDataVencimentoString(UtilData.getDataFormatada(lancamento.getDataVencimento(), UtilData.PATTERN_DATA_ISO));
				
				this.incluir(lancamento);
			} catch (NegocioException e) {
				e.printStackTrace();
			}
		});
	}



	private void excluirLancamentosPorFormaPagamento(List<Lancamento> lancamentos, FormaPagamento formaPagamento, boolean somentePagos, List<Lancamento> lancamentosAExcluir) {
		lancamentos.stream()
		.filter(lanc -> lanc.isPago() == somentePagos )
		.filter(lanc -> lanc.getFormaPagamento().equals(formaPagamento))
		.forEach(lancamento -> {
			try {
				System.out.println("excluindo: " + lancamento.getIdLancamento());
				lancamento.setDataHoraPagamentoString(UtilData.getDataFormatada(new Date(), UtilData.PATTERN_DATA_ISO )); 
				lancamento.setDataVencimentoString(UtilData.getDataFormatada(lancamento.getDataVencimento(), UtilData.PATTERN_DATA_ISO));
				
				if (! lancamentosAExcluir.stream().anyMatch(lanc -> lanc.getIdLancamento().equals(lancamento.getIdLancamento()))) {
					this.excluir(lancamento.getIdLancamento());
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IllegalArgumentException(e);
			}
		});
		

	}



	@Override
	public void dividirLancamento(DivisaoLancamentoDTO dto) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	
}