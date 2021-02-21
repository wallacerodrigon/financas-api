package br.net.walltec.api.negocio.servicos.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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

import org.apache.commons.beanutils.BeanUtils;

import br.net.walltec.api.comum.IntegracaoIntegrator;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.dto.GeracaoLancamentosDTO;
import br.net.walltec.api.dto.ResumoLancamentosMesAnoDTO;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.CampoObrigatorioException;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.excecoes.RegistroNaoEncontradoException;
import br.net.walltec.api.importacao.estrategia.ImportadorArquivo;
import br.net.walltec.api.importacao.estrategia.ImportadorCSVBB;
import br.net.walltec.api.importacao.estrategia.ImportadorCefTxt;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.FechamentoContabilService;
import br.net.walltec.api.negocio.servicos.FormaPagamentoService;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;
import br.net.walltec.api.rest.dto.ImportadorArquivoDTO;
import br.net.walltec.api.rest.dto.LancamentosConsultaDTO;
import br.net.walltec.api.rest.dto.UploadDocumentoDTO;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import br.net.walltec.api.utilitarios.Constantes;
import br.net.walltec.api.utilitarios.UtilBase64;
import br.net.walltec.api.utilitarios.UtilData;
import br.net.walltec.api.utilitarios.UtilObjeto;
import br.net.walltec.api.validadores.ValidadorDados;

@Named
public class LancamentoServicoImpl extends AbstractCrudServicePadrao<Lancamento>
		implements LancamentoService {

	@Inject
	private LancamentoDao lancamentoDao;

	@Inject
	private FechamentoContabilService fechamentoContabilService;
	
	@Inject
	private FormaPagamentoService formaPagamentoService;

	
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
			lancamento.setDataHoraPagamento(new Date()); 
			try {
				this.lancamentoDao.alterar(lancamento);
				
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
	public PageResponse<List<LancamentosConsultaDTO>> filtrarLancamentos(Date dataInicial, Date dataFinal, TipoLancamento tipoLancamento, Integer idUsuario) throws NegocioException {
		
		if (UtilObjeto.isVazio(dataInicial) || UtilObjeto.isVazio(dataFinal) ) {
			throw new CampoObrigatorioException("Período não informado");
		}
		
		try {
			PageResponse<List<Lancamento>> parcelas = lancamentoDao.listarParcelas(dataInicial, dataFinal, tipoLancamento, idUsuario);

			Map<Lancamento, List<Lancamento>> mapLancamentosPorOrigem = 
						parcelas.getResultado()
						.stream()
						.filter(lanc -> lanc.getLancamentoOrigem() != null)
						.collect(Collectors.groupingBy(Lancamento::getLancamentoOrigem));
			
			List<Integer> idsLancamentosOriginarios = mapLancamentosPorOrigem
						.keySet()
						.stream()
						.map(lanc -> lanc.getIdLancamento())
						.collect(Collectors.toList());
			
			List<LancamentosConsultaDTO> listaDtos = parcelas.getResultado()
					.stream()
					.filter(lanc -> lanc.getLancamentoOrigem() == null)
					.filter(lanc -> !idsLancamentosOriginarios.contains(lanc.getIdLancamento())  )
					.map(lancamento -> {
						return new LancamentosConsultaDTO(lancamento, null);
					})
					.collect(Collectors.toList());
			
			mapLancamentosPorOrigem.keySet()
			.stream()
			.map(lancamentoKey ->  new LancamentosConsultaDTO(lancamentoKey, mapLancamentosPorOrigem.get(lancamentoKey) ))
			.forEach(dto -> listaDtos.add(dto));
			
			
			return new PageResponse<List<LancamentosConsultaDTO>>(parcelas.getPagina(), parcelas.getQtdPaginas(), 
					listaDtos.size(), parcelas.getPagina(), null, listaDtos);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}
	
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRED)
	@Override
	public void alterar(Lancamento objeto) throws NegocioException {
		Lancamento l = this.find(objeto.getIdLancamento());
		
		
		if (l.isPago()) {
			throw new NegocioException("Lançamento pago, não pode ser alterado.");
		}
		
		try {
			l.setDataVencimento(objeto.getDataVencimento());
			l.setFormaPagamento(objeto.getFormaPagamento());
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
		try {
			this.lancamentoDao.incluir(objeto);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}



	@Override
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRES_NEW )
	public void importarArquivo(ImportadorArquivoDTO importadorDto, Integer idUsuario) throws NegocioException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		
	    FechamentoContabil fechamentoContabil = fechamentoContabilService.obterPorMesAno(importadorDto.getAno(),  importadorDto.getMes(), usuario);
		
	    if (fechamentoContabil != null) {
	    	throw new NegocioException("Você está com este mês já encerrado, não poderá ter importação.");
	    }
	    
		List<Lancamento> lancamentos = importarConteudoArquivo(importadorDto);
		
		Date dataBase = UtilData.createDataSemHoras(1, importadorDto.getMes(), importadorDto.getAno());
		Date dataInicialDoMes = UtilData.getPrimeiroDiaMes(dataBase);
		
		if (lancamentos.get(1).getDataVencimento().before(dataInicialDoMes)) {
			Date dataVencimento = lancamentos.get(1).getDataVencimento();
			throw new NegocioException("Arquivo inválido para este mês. Os vencimentos do arquivo são de " + UtilData.getMes(dataVencimento) + "/" + UtilData.getAno(dataVencimento));
		}
		
		Banco banco = lancamentos.get(0).getBanco();
		
		Date dataInicial = UtilData.createDataSemHoras(1, importadorDto.getMes(), importadorDto.getAno());
		Date dataFinal = UtilData.getUltimaDataMes(dataInicial);
		
		excluirLancamentosSalvos(banco, dataInicial, dataFinal, idUsuario);
		
		//agrupar pela descriçao do lançamento e fazer um agrupamento de origem desta forma
		Map<String, List<Lancamento>> mapLancamentosPorDescricao = lancamentos
				.stream()
				.collect(Collectors.groupingBy(Lancamento::getChavePorDescFormaPagto));
		
		
		mapLancamentosPorDescricao.keySet()
			.stream()
			.forEach(chave -> {
			 	List<Lancamento> lancamentosAIncluir = mapLancamentosPorDescricao.get(chave);
			 	
			 	if (lancamentosAIncluir.size() > 1) {
			 		Lancamento lancamentoInicialDaChave = lancamentosAIncluir.get(0);
					
					Lancamento lancamentoOrigem = new Lancamento();
					try {
						BeanUtils.copyProperties(lancamentoOrigem, lancamentoInicialDaChave);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lancamentoOrigem.setNumDocumento(null);
					lancamentoOrigem.setIdLancamento(null);
					lancamentoOrigem.setUsuario(new Usuario());
					lancamentoOrigem.getUsuario().setIdUsuario(idUsuario);
					
					BigDecimal totalDaChave = BigDecimal.ZERO;
					for(Lancamento l : lancamentosAIncluir) {
						 totalDaChave = totalDaChave.add( l.getValorLancamento() );
					}
					
					lancamentoOrigem.setValorLancamento( totalDaChave );
					
					lancamentosAIncluir.stream().forEach(lanc -> {
						lanc.setLancamentoOrigem(lancamentoOrigem);
						lanc.setUsuario(lancamentoOrigem.getUsuario());
					});
			 	}
				incluirListaLancamentos(lancamentosAIncluir);
			});
		
		
	}



	/**
	 * @param lancamentos
	 */
	private void incluirListaLancamentos(List<Lancamento> lancamentos) {
		lancamentos.forEach(lancamento -> {
			try {
				if (lancamento.getLancamentoOrigem() != null) {
					this.incluir(lancamento.getLancamentoOrigem());
				}
				
				
				this.incluir(lancamento);
			} catch (NegocioException e) {
				e.printStackTrace();
			}
		});
	}



	/**
	 * @param banco
	 * @param dataInicial
	 * @param dataFinal
	 */
	private void excluirLancamentosSalvos(Banco banco, Date dataInicial, Date dataFinal, Integer idUsuario) {
		PageResponse<List<Lancamento>> response = lancamentoDao.listarParcelas(dataInicial, dataFinal, null, idUsuario);

		List<Lancamento> lancamentosComDependencia = response.getResultado().stream()
				.filter(lanc -> lanc.getLancamentoOrigem() != null)
				.map(lanc -> lanc.getLancamentoOrigem())
				.distinct()
				.collect(Collectors.toList());
		
		excluirLancamentosPorFormaPagamento( response.getResultado(), 
				banco.getFormaPagamentoParaConciliacao(), true,
				lancamentosComDependencia);
		
		response = lancamentoDao.listarParcelas(dataInicial, dataFinal, null, idUsuario);
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
	}



	/**
	 * @param importadorDto
	 * @return
	 * @throws NegocioException
	 */
	private List<Lancamento> importarConteudoArquivo(ImportadorArquivoDTO importadorDto) throws NegocioException {
		String chaveImportador = importadorDto.getNumBanco() +"_" + importadorDto.getExtensaoArquivo();
		byte[] conteudoArquivo = UtilBase64.decodificarBase64(importadorDto.getDadosArquivoBase64());
		ImportadorArquivo importador = mapImportadores.get(chaveImportador);
		
		if (! importador.isExtensaoValida(importadorDto.getExtensaoArquivo())) {
			throw new NegocioException("Extensão inválida para esse banco.");
		}
		List<Lancamento> lancamentos = importador.importar(importadorDto.getNomeArquivo(), conteudoArquivo);
		return lancamentos;
	}



	private void excluirLancamentosPorFormaPagamento(List<Lancamento> lancamentos, FormaPagamento formaPagamento, boolean somentePagos, List<Lancamento> lancamentosAExcluir) {
		lancamentos.stream()
		.filter(lanc -> lanc.isPago() == somentePagos )
		.filter(lanc -> lanc.getFormaPagamento().equals(formaPagamento))
		.forEach(lancamento -> {
			try {
				System.out.println("excluindo: " + lancamento.getIdLancamento());
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

	private void validarDivisaoLancamento(Lancamento lancamento, DivisaoLancamentoDTO dto) throws NegocioException {
		if (lancamento.isPago()) {
			throw new NegocioException("Lançamento pago, não poderá ser dividido");
		}
		
		
		if (dto.getValor().compareTo(lancamento.getValorLancamento()) == 1) {
			throw new IllegalArgumentException("Valor do evento deve ser menor ou igual ao valor do lançamento");
		}
		
		Date dataEvento = UtilData.getDataPorPattern(dto.getDataEventoIso(), UtilData.PATTERN_DATA_ISO);
		if (UtilData.getMes(dataEvento) != UtilData.getMes(lancamento.getDataVencimento())) {
			throw new NegocioException("Evento com data diferente do mês do lançamento");
		}
		
		this.formaPagamentoService.findByOptional(dto.getIdFormaPagamento()).orElseThrow(() -> 
			new IllegalArgumentException("Forma de pagamento inexistente"));
	}


	@Override
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRES_NEW )

	public void dividirLancamento(DivisaoLancamentoDTO dto) throws NegocioException {
		if (dto == null) {
			throw new IllegalArgumentException("Informe os dados para efetuar a divisão do lançamento");
		}
		ValidadorDados.validarDadosEntrada(dto);
		
		Lancamento lancamento = this.findByOptional(dto.getIdLancamentoOrigem()).orElseThrow(() -> new RegistroNaoEncontradoException("Lançamento não encontrado com esse id"));
		
		this.validarDivisaoLancamento(lancamento, dto);

		BigDecimal novoValor = lancamento.getValorLancamento().subtract(dto.getValor());
		
		lancamento.setValorLancamento(novoValor);
		
		Date dataEvento = UtilData.getDataPorPattern(dto.getDataEventoIso(), UtilData.PATTERN_DATA_ISO);

		Lancamento novoLancamento = new Lancamento();
		novoLancamento.setDataHoraPagamento(dataEvento);
		novoLancamento.setDataVencimento(dataEvento);
		novoLancamento.setDescLancamento(dto.getDescricao());
		novoLancamento.setFormaPagamento(this.formaPagamentoService.findByOptional(dto.getIdFormaPagamento()).get());
		novoLancamento.setLancamentoOrigem(lancamento);
		novoLancamento.setTipoLancamento(lancamento.getTipoLancamento());
		novoLancamento.setValorLancamento(dto.getValor());
		novoLancamento.setUsuario(lancamento.getUsuario());
		try {
			this.lancamentoDao.alterar(novoLancamento);
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException(e.getMessage());
		}
	}



	/**
	 * @param lancamentoDao the lancamentoDao to set
	 */
	public void setLancamentoDao(LancamentoDao lancamentoDao) {
		this.lancamentoDao = lancamentoDao;
	}



	/**
	 * @param formaPagamentoService the formaPagamentoService to set
	 */
	public void setFormaPagamentoService(FormaPagamentoService formaPagamentoService) {
		this.formaPagamentoService = formaPagamentoService;
	}



	@Override
	@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRES_NEW )
	public void efetuarUploadArquivo(UploadDocumentoDTO dto) throws NegocioException {
		String idGerado = IntegracaoIntegrator.salvarArquivo(dto.getConteudoEmBase64(), dto.getNomeArquivo(), dto.getMimeType());
		
		try {
			Lancamento lancamento = this.find(dto.getIdLancamento());
			lancamento.setNumDocumento(dto.getMimeType() + "@" + idGerado);	
			this.lancamentoDao.alterar(lancamento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			IntegracaoIntegrator.apagarArquivo(idGerado);
			e.printStackTrace();
			throw new NegocioException(e.getMessage());

		}
	}



	@Override
	public String efetuarDownloadArquivo(Integer idLancamento) throws NegocioException {
		
		Lancamento lancamento = this.find(idLancamento);
		if (lancamento.getNumDocumento() != null) {
			try {
				String dadosDocumento[] = lancamento.getNumDocumento().split("@");
				String mimetype = dadosDocumento[0];
				return mimetype + "@" + UtilBase64.codificarBase64(IntegracaoIntegrator.recuperarArquivo( dadosDocumento[1] ));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new NegocioException(e.getMessage());

			}
		}

		throw new NegocioException("Documento não registrado neste lançamento.");
		
	}



	@Override
	//@Transactional(rollbackOn = Exception.class, value = TxType.REQUIRED )
	public void gerarLoteLancamentos(GeracaoLancamentosDTO dto) throws NegocioException {

		Date dataBase = UtilData.getDataPorPattern(dto.getDataVencimentoString(), UtilData.PATTERN_DATA_ISO);

		for(int i = 0; i < dto.getQtdRepeticoes(); i++) {

			Lancamento lancamento = new Lancamento();
			lancamento.setDataVencimento( dataBase );
			lancamento.setDescLancamento(dto.getDescLancamento());
			lancamento.setTipoLancamento(dto.getTipoLancamento());
			lancamento.setFormaPagamento(dto.getFormaPagamento());
			lancamento.setUsuario(RequisicaoInterceptor.getUsuarioLogadoSoComId());
			lancamento.setValorLancamento(dto.getValorLancamento());
			
			this.incluir(lancamento);
			dataBase = UtilData.somarData(dataBase, 1, ChronoUnit.MONTHS);

		}
		
		
		
	}



	@Override
	public List<ResumoLancamentosMesAnoDTO> listarResumoLancamentosDoAno(Integer ano, Integer idUsuario)
			throws NegocioException {

		List<Lancamento> lancamentosFiltrados = this.lancamentoDao.listarLancamentosDoAno(ano, idUsuario);
		
		List<ResumoLancamentosMesAnoDTO> retorno = this.formatarResultado(ano, lancamentosFiltrados);
		
		retorno.sort(new Comparator<ResumoLancamentosMesAnoDTO>() {
			@Override
			public int compare(ResumoLancamentosMesAnoDTO o1, ResumoLancamentosMesAnoDTO o2) {
				return o1.getNumMes().compareTo(o2.getNumMes());
			}
		});
		return retorno;
	}

	private List<ResumoLancamentosMesAnoDTO> formatarResultado(Integer ano, List<Lancamento> lancamentos) {
		if (lancamentos.isEmpty()) {
			return null;
		} else {
			Map<Integer, List<ResumoLancamentosMesAnoDTO>> mapResumo = lancamentos
						.stream()
						.map(lancamento-> {
							ResumoLancamentosMesAnoDTO dto = new ResumoLancamentosMesAnoDTO();
							dto.setAno(ano);
							dto.setNumMes( UtilData.getMes(lancamento.getDataVencimento()) );
							dto.setTotalCreditos(lancamento.isDespesa() ? BigDecimal.ZERO :  lancamento.getValorLancamento());
							dto.setTotalDebitos(lancamento.isDespesa() ? lancamento.getValorLancamento() : BigDecimal.ZERO);
							return dto;
						})
						.collect(Collectors.groupingBy(ResumoLancamentosMesAnoDTO::getNumMes));
			
			return mapResumo.keySet().stream()
				.map(chave -> {
					ResumoLancamentosMesAnoDTO dto = new ResumoLancamentosMesAnoDTO();
					dto.setAno(ano);
					dto.setNumMes(chave);
					dto.setNomeMes( Constantes.MESES_DO_ANO[chave -1] );

					dto.setTotalCreditos(
						BigDecimal.valueOf(
							mapResumo.get(chave).stream()
						.filter(item -> item.getTotalCreditos().compareTo(BigDecimal.ZERO) == 1 )
						.map(item -> item.getTotalCreditos().doubleValue() )
						.reduce(0.0, (elem1, elem2) -> elem1 + elem2))
					);
					
					dto.setTotalDebitos(
							BigDecimal.valueOf(
								mapResumo.get(chave).stream()
							.filter(item -> item.getTotalDebitos().compareTo(BigDecimal.ZERO) == 1 )
							.map(item -> item.getTotalDebitos().doubleValue() )
							.reduce(0.0, (elem1, elem2) -> elem1 + elem2))
						);
					
					dto.setSaldoFinal(dto.getTotalCreditos().subtract(dto.getTotalDebitos()));
					
					
					return dto;
				})
				.collect(Collectors.toList());
						
		}
	}
	
}