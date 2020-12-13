package br.net.walltec.api.negocio.servicos.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import br.net.walltec.api.dto.ConsultaLancamentosDTO;
import br.net.walltec.api.dto.FiltraParcelasDto;
import br.net.walltec.api.dto.GeracaoParcelasDto;
import br.net.walltec.api.dto.LancamentosPorRubricaDTO;
import br.net.walltec.api.dto.MapaDashboardDTO;
import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.dto.ResumoMesAnoDTO;
import br.net.walltec.api.dto.TipoContaNoMesDTO;
import br.net.walltec.api.dto.UtilizacaoLancamentoDTO;
import br.net.walltec.api.dto.UtilizacaoParcelasDto;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.AbstractCrudServicePadrao;
import br.net.walltec.api.negocio.servicos.LancamentoService;
import br.net.walltec.api.persistencia.dao.FechamentoContabilDao;
import br.net.walltec.api.persistencia.dao.FormaPagamentoDao;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;
import br.net.walltec.api.rest.dto.filtro.DesfazimentoConciliacaoDTO;
import br.net.walltec.api.rest.dto.filtro.RegistroFechamentoMesDTO;

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
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#consultaParcelasEmArvore(br.net.walltec.api.dto.FiltraParcelasDto)
	 */
	@Override
	public ConsultaLancamentosDTO consultaParcelasEmArvore(FiltraParcelasDto dtoFiltro) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#listarParcelas(br.net.walltec.api.dto.FiltraParcelasDto)
	 */
	@Override
	public List<Lancamento> listarParcelas(FiltraParcelasDto dtoFiltro) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#baixarParcelas(java.util.List)
	 */
	@Override
	public boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#excluirParcelas(java.util.List)
	 */
	@Override
	public boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#utilizarLancamento(br.net.walltec.api.dto.UtilizacaoParcelasDto)
	 */
	@Override
	public List<Lancamento> utilizarLancamento(UtilizacaoParcelasDto dtoUtilizacao) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#listarHistoricoUso(java.lang.Integer)
	 */
	@Override
	public List<UtilizacaoLancamentoDTO> listarHistoricoUso(Integer idLancamento) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#montarListaLancamentos(br.net.walltec.api.dto.GeracaoParcelasDto)
	 */
	@Override
	public List<Lancamento> montarListaLancamentos(GeracaoParcelasDto dto) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#montarDashboards(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public MapaDashboardDTO montarDashboards(Integer mes, Integer ano) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#gerarLancamentos(br.net.walltec.api.dto.GeracaoParcelasDto)
	 */
	@Override
	public List<Lancamento> gerarLancamentos(GeracaoParcelasDto dto) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#associarLancamentos(java.util.List)
	 */
	@Override
	public void associarLancamentos(List<RegistroExtratoDto> lancamentos) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#desfazerConciliacoes(br.net.walltec.api.rest.dto.filtro.DesfazimentoConciliacaoDTO)
	 */
	@Override
	public void desfazerConciliacoes(DesfazimentoConciliacaoDTO desfazimentoDTO) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#fecharMes(br.net.walltec.api.rest.dto.filtro.RegistroFechamentoMesDTO)
	 */
	@Override
	public void fecharMes(RegistroFechamentoMesDTO fechamentoDTO) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#isMesFechado(int, int)
	 */
	@Override
	public boolean isMesFechado(int mes, int ano) throws NegocioException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#getResumoPorTipoConta(java.util.List)
	 */
	@Override
	public Set<TipoContaNoMesDTO> getResumoPorTipoConta(List<Lancamento> lancamentos) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#gerarMapaAno(java.lang.Integer)
	 */
	@Override
	public List<ResumoMesAnoDTO> gerarMapaAno(Integer ano) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#listarLancamentosPorRubricaEAno(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LancamentosPorRubricaDTO> listarLancamentosPorRubricaEAno(Integer ano, Integer idRubrica)
			throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#excluirParcelasPorPeriodo(java.util.Date, java.util.Date)
	 */
	@Override
	public void excluirParcelasPorPeriodo(Date dataInicio, Date dataFim) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#excluirListaParcelas(java.util.List)
	 */
	@Override
	public void excluirListaParcelas(List<Lancamento> lancamentos) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#conciliarLancamentos(java.util.List, java.util.List, java.lang.Integer, java.util.Date)
	 */
	@Override
	public List<RegistroExtratoDto> conciliarLancamentos(List<Lancamento> lancamentosDoExtrato,
			List<DeparaHistoricoBanco> listaDeparas, Integer numBanco, Date dataBase) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#criarParcelas(br.net.walltec.api.dto.GeracaoParcelasDto)
	 */
	@Override
	public List<Lancamento> criarParcelas(GeracaoParcelasDto dto) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#gerarNumeroProxLancamento(br.net.walltec.api.entidades.Lancamento)
	 */
	@Override
	public Integer gerarNumeroProxLancamento(Lancamento lancamentoBase) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.AbstractCrudServicoPadrao#alterarVO(br.
//	 * net.walltec.api.vo.GerenciadorPadraoVO)
//	 */
//	@Override
//	public LancamentoVO alterarVO(LancamentoVO objeto) throws NegocioException {
////		Lancamento lancamento = this.find(objeto.getId()); // (Lancamento)
////															// getConversor().converterPojoParaEntidade(objeto);
////
////		Date dataVencimento = UtilData.getData(objeto.getDataVencimentoStr(), UtilData.SEPARADOR_PADRAO);
////
////		verificarMesFechado(dataVencimento);
////
////		lancamento.setBolPaga(objeto.isBolPaga());
////		lancamento.setValor(BigDecimal.valueOf(objeto.getValor()));
////		lancamento.setNumDocumento(objeto.getNumDocumento());
////
////		FormaPagamento formaPagamento = new FormaPagamento();
////		formaPagamento.setId(objeto.getIdFormaPagamento());
////
////		lancamento.setFormaPagamento(formaPagamento);
////		lancamento.setDescricao(objeto.getDescricao());
////		lancamento.setDataVencimento(dataVencimento);
////
////		this.alterar(lancamento);
//
//		return (LancamentoVO) getConversor().converterEntidadeParaPojo(null);
//	}
//
//	@Override
//	public ConsultaLancamentosDTO consultaParcelasEmArvore(FiltraParcelasDto dtoFiltro) throws NegocioException {
//		if (dtoFiltro == null) {
//			throw new CampoObrigatorioException("Filtro não informado");
//		}
//		if (dtoFiltro.getMes() == null && dtoFiltro.getAno() == null && dtoFiltro.getIdConta() == null
//				&& dtoFiltro.getIdParcelaOrigem() == null) {
//			throw new CampoObrigatorioException("Parâmetros dos filtros não foram informados");
//		}
//
//		List<Lancamento> listaParcelas = listarParcelas(dtoFiltro);
//		ConsultaLancamentosDTO consulta = new ConsultaLancamentosDTO();
//		consulta.setResumo(montarResumo(listaParcelas));
//		consulta.getResumo().setAno(dtoFiltro.getAno());
//		consulta.getResumo().setMes(dtoFiltro.getMes());
//
//		Map<Integer, List<Lancamento>> mapLancamentosPorOrigem = mapearLancamentosFilhos(listaParcelas);
//		List<LancamentoVO> lancamentosPais = converterLancamentosPais(listaParcelas);
//		lancamentosPais.stream().forEach(vo -> {
//			if (mapLancamentosPorOrigem.containsKey(vo.getId())) {
//				vo.setLancamentosUtilizados(
//						getConversor().converterEntidadeParaPojo(mapLancamentosPorOrigem.get(vo.getId())));
//			}
//		});
//
//		consulta.setLancamentos(lancamentosPais);
//		return consulta;
//	}
//
//	/**
//	 * @param listaParcelas
//	 * @return
//	 */
//	private ResumoDetalhadoMesAnoDTO montarResumo(List<Lancamento> listaParcelas) {
//		ResumoDetalhadoMesAnoDTO resumo = new ResumoDetalhadoMesAnoDTO();
//		listaParcelas.stream().forEach(lancamento -> {
//			resumo.addTotalDespesas(lancamento);
//			resumo.addTotalReceitas(lancamento);
//
//			resumo.addTotalPagar(lancamento);
//			resumo.addTotalReceber(lancamento);
//
//			resumo.addTotalPago(lancamento);
//			resumo.addTotalRecebido(lancamento);
//
//			resumo.addTotalConciliado(lancamento);
//			resumo.addTotalNaoConciliado(lancamento);
//		});
//
//		resumo.setSaldoFinal(resumo.getTotalReceitas().subtract(resumo.getTotalDespesas()));
//		return resumo;
//	}
//
//	/**
//	 * @param listaParcelas
//	 * @return
//	 */
//	private List<LancamentoVO> converterLancamentosPais(List<Lancamento> listaParcelas) {
//		List<LancamentoVO> lancamentos = getConversor().converterEntidadeParaPojo(listaParcelas.stream()
//				.filter(parcela -> parcela.getIdLancamentoOrigem().equals(0)).collect(Collectors.toList()));
//		return lancamentos;
//	}
//
//	/**
//	 * @param listaParcelas
//	 * @return
//	 */
//	private Map<Integer, List<Lancamento>> mapearLancamentosFilhos(List<Lancamento> listaParcelas) {
//		Map<Integer, List<Lancamento>> mapLancamentosPorOrigem = listaParcelas.stream()
//				.filter(lancamento -> !lancamento.getIdLancamentoOrigem().equals(0))
//				.collect(Collectors.groupingBy(Lancamento::getIdLancamentoOrigem));
//		return mapLancamentosPorOrigem;
//	}
//
//	public List<Lancamento> listarParcelas(FiltraParcelasDto dtoFiltro) throws NegocioException {
//		Date dataInicial = null;
//		Date dataFinal = null;
//		if (dtoFiltro.getMes() != null && dtoFiltro.getAno() != null) {
//			dataInicial = UtilData.createDataSemHoras(1, dtoFiltro.getMes(), dtoFiltro.getAno());
//			dataFinal = UtilData.asDate(UtilData.getUltimaDataMes(dtoFiltro.getMes(), dtoFiltro.getAno()));
//		}
//		try {
//			return lancamentoDao.listarParcelas(dataInicial, dataFinal, dtoFiltro.getIdConta(),
//					dtoFiltro.getIdParcelaOrigem());
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new NegocioException(e);
//		}
//	}
//
//	@Override
//	public boolean baixarParcelas(List<Integer> idsLancamentos) throws NegocioException {
//		if (idsLancamentos == null || idsLancamentos.size() == 0) {
//			throw new CampoObrigatorioException("Lista de lancamentos não informada!");
//		}
//		try {
//			for (Integer idLancamento : idsLancamentos) {
//				Lancamento lancBanco = this.find(idLancamento);
//				lancBanco.setBolPaga(true);
//				this.alterar(lancBanco);
//			}
//			return true;
//		} catch (Exception e) {
//			throw new NegocioException(e);
//		}
//
//	}
//
//	@Override
//	public boolean excluirParcelas(List<Integer> idsLancamentos) throws NegocioException {
//		if (idsLancamentos == null || idsLancamentos.size() == 0) {
//			throw new CampoObrigatorioException("Lista de lancamentos não informada!");
//		}
//		try {
//			for (Integer idLancamento : idsLancamentos) {
//				this.excluir(idLancamento);
//			}
//			return true;
//		} catch (Exception e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.AbstractCrudServicoPadrao#excluir(java.io
//	 * .Serializable)
//	 */
//	@Override
//	public void excluir(Serializable id) throws NegocioException {
//		Lancamento l = this.find(id);
//		l.setValor(BigDecimal.ZERO);
//		l.setDataVencimento(new Date(0));
//		this.alterar(l);
//	}
//
//	@Override
//	public List<LancamentoVO> utilizarLancamento(UtilizacaoParcelasDto dtoUtilizacao) throws NegocioException {
//
//		verificarMesFechado(UtilData.getData(dtoUtilizacao.getDataUtilizacaoStr(), UtilData.SEPARADOR_PADRAO));
//
//		Lancamento lancamento = null;
//		try {
//			lancamento = lancamentoDao.find(dtoUtilizacao.getIdLancamentoOrigem());
//
//			BigDecimal novoValor = lancamento.getValor().subtract(dtoUtilizacao.getValorUtilizado());
//			lancamento.setValor(novoValor);
//			lancamento.setBolConciliado(false);
//			lancamentoDao.alterar(lancamento);
//
//			// incluir uma nova lancamento utilizando a de origem
//			Lancamento lancamentoIncluido = incluirUtilizacaoParcela(lancamento, dtoUtilizacao);
//			return Arrays.asList(
//					getFabricaConversor().criarConversor(Lancamento.class, LancamentoVO.class)
//							.converterEntidadeParaPojo(lancamento),
//					getFabricaConversor().criarConversor(Lancamento.class, LancamentoVO.class)
//							.converterEntidadeParaPojo(lancamentoIncluido));
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	private Lancamento incluirUtilizacaoParcela(Lancamento lancamentoOrigem, UtilizacaoParcelasDto dtoUso)
//			throws NegocioException {
//
//		Lancamento lancamentoUtilizada = new Lancamento();
//		lancamentoUtilizada.setBolPaga(true);
//		lancamentoUtilizada.setConta(lancamentoOrigem.getConta());
//		lancamentoUtilizada
//				.setDataVencimento(UtilData.getData(dtoUso.getDataUtilizacaoStr(), UtilData.SEPARADOR_PADRAO));
//		lancamentoUtilizada.setDescricao(dtoUso.getDescricao());
//		lancamentoUtilizada.setFormaPagamento(new FormaPagamento());
//		lancamentoUtilizada.getFormaPagamento().setId(dtoUso.getIdFormaPagamento());
//
//		lancamentoUtilizada.setNumero(obterProximoNumero(lancamentoOrigem));
//		lancamentoUtilizada.setLancamentoOrigem(lancamentoOrigem);
//		lancamentoUtilizada.setValor(dtoUso.getValorUtilizado());
//		lancamentoUtilizada.setBolConciliado(false);
//		try {
//			return lancamentoDao.incluir(lancamentoUtilizada);
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	/**
//	 * @param lancamentoOrigem
//	 * @return
//	 * @throws NegocioException
//	 */
//	private Short obterProximoNumero(Lancamento lancamentoOrigem) throws NegocioException {
//		List<UtilizacaoLancamentoVO> usos = this.listarHistoricoUso(lancamentoOrigem.getId());
//		if (usos == null || usos.isEmpty()) {
//			return Short.valueOf("1");
//		} else {
//			final Comparator<UtilizacaoLancamentoVO> comp = (vo1, vo2) -> vo1.getNumeroParcela()
//					.compareTo(vo2.getNumeroParcela());
//			UtilizacaoLancamentoVO utilizacaoLancamentoVO = usos.stream().max(comp).get();
//			return Integer.valueOf(utilizacaoLancamentoVO.getNumeroParcela() + 1).shortValue();
//		}
//	}
//
//	@Override
//	public List<UtilizacaoLancamentoVO> listarHistoricoUso(Integer idLancamento) throws NegocioException {
//		try {
//			return lancamentoDao.listarUsos(idLancamento);
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//
//	}
//
//	@Transactional(value = Transactional.TxType.REQUIRES_NEW)
//	private boolean baixarLancamento(LancamentoVO lancamentoVo) throws PersistenciaException {
//		Lancamento p = lancamentoDao.find(lancamentoVo.getId());
//		p.setBolPaga(true);
//		lancamentoDao.alterar(p);
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#gerarLancamentos(br.net
//	 * .walltec.api.vo.LancamentoVO, java.util.Date)
//	 */
//	@Override
//	public List<Lancamento> montarListaLancamentos(GeracaoParcelasDto dto) throws NegocioException {
//		Date dataInicialAux = UtilData.getData(dto.getDataVencimentoStr(), "/");
//		int numParcela = 1;
//		List<Lancamento> lancamentos = new ArrayList<>();
//		FormaPagamento fp = getFormaPagamento(dto);
//
//		Conta conta = null;
//		try {
//			conta = contaDao.find(dto.getIdConta());
//		} catch (PersistenciaException e) {
//			e.printStackTrace();
//			throw new NegocioException(e);
//		}
//
//		for (int i = 0; i < dto.getQuantidade(); i++) {
//			Lancamento lancamento = new Lancamento();
//			lancamento.setConta(conta);
//
//			lancamento.setNumero(Integer.valueOf(++numParcela).shortValue());
//			lancamento.setDataVencimento(dataInicialAux);
//			lancamento.setDescricao(dto.getDescricaoParcela());
//			lancamento.setFormaPagamento(fp);
//			lancamento.setBolPaga(false);
//			lancamento.setBolConciliado(false);
//
//			if (dto.getIdParcelaOrigem() != null) {
//				lancamento.setLancamentoOrigem(new Lancamento());
//				lancamento.getLancamentoOrigem().setId(dto.getIdParcelaOrigem());
//			}
//			lancamento.setValor(dto.getValorVencimento());
//			lancamentos.add(lancamento);
//
//			dataInicialAux = UtilData.somarData(dataInicialAux, 1, ChronoUnit.MONTHS);
//		}
//		return lancamentos;
//	}
//
//	/**
//	 * @param dto
//	 */
//	private FormaPagamento getFormaPagamento(GeracaoParcelasDto dto) {
//		try {
//			return formaPagamentoDao.find(dto.getIdFormaPagamento());
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private void mapearLancamento(Map<String, List<Lancamento>> mapaCache, Lancamento lancamento) {
//		String dataFormatada = lancamento.getDataVencimento().getMonth() + ""; // , "yyyy-mm-dd");
//		mapaCache.putIfAbsent(dataFormatada, new ArrayList<>()).add(lancamento);
//		// mapaCache.computeIfAbsent(key,
//		// mappingFunction)get(dataFormatada).add(lancamento);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#montarDashboards(java.
//	 * lang.Integer, java.lang.Integer)
//	 */
//	@Override
//	public MapaDashboardDTO montarDashboards(Integer mes, Integer ano) throws NegocioException {
//		List<Lancamento> lancamentos = this.listarParcelas(new FiltraParcelasDto(mes, ano));
//		BigDecimal[] valoresEntradaSaida = getValoresEntradaSaida(lancamentos);
//
//		// montar um builder com DSL
//		MapaDashboardDTO dto = new MapaDashboardDTO().adicioneEssesTiposContasPorMes(getResumoPorTipoConta(lancamentos))
//				.comEsseValorDeEntrada(valoresEntradaSaida[0]).comEsseValorDeSaida(valoresEntradaSaida[1])
//				.comEsseValorParaPagar(this.getTotalPagar(lancamentos))
//				.comEsseValorParaReceber(this.getTotalReceber(lancamentos)).monteOMapaDeDashboard();
//
//		return dto;
//	}
//
//	/**
//	 * @param lancamentos
//	 * @return
//	 */
//	private BigDecimal[] getValoresEntradaSaida(List<Lancamento> lancamentos) {
//
//		Map<Boolean, Double> mapPorDespesa = lancamentos.stream().collect(
//				Collectors.groupingBy(Lancamento::getDespesa, Collectors.summingDouble(Lancamento::getValorEmDouble)));
//
//		return new BigDecimal[] {
//				new BigDecimal(mapPorDespesa.getOrDefault(false, 0.00)).setScale(2, RoundingMode.CEILING),
//				new BigDecimal(mapPorDespesa.getOrDefault(true, 0.00)).setScale(2, RoundingMode.CEILING) };
//
//	}
//
//	/**
//	 * @param listaMesAtual
//	 * @return
//	 */
//	private BigDecimal getTotalConciliado(List<Lancamento> listaMesAtual) {
//		Double totalConciliado = listaMesAtual.stream()
//				.filter(lancamento -> lancamento.isDespesa() && lancamento.getBolConciliado())
//				.mapToDouble(lanc -> lanc.getValorEmDouble()).sum();
//		return new BigDecimal(totalConciliado);
//	}
//
//	/**
//	 * @param listaMesAtual
//	 * @return
//	 */
//	private BigDecimal getTotalPagar(List<Lancamento> listaMesAtual) {
//		Double valorAPagar = listaMesAtual.stream()
//				.filter(lancamento -> lancamento.isDespesa() && !lancamento.getBolPaga())
//				.mapToDouble(lanc -> lanc.getValorEmDouble()).sum();
//
//		return new BigDecimal(valorAPagar);
//	}
//
//	private BigDecimal getTotalReceber(List<Lancamento> listaMesAtual) {
//		Double valorAPagar = listaMesAtual.stream()
//				.filter(lancamento -> lancamento.isReceita() && !lancamento.getBolPaga())
//				.mapToDouble(lanc -> lanc.getValorEmDouble()).sum();
//
//		return new BigDecimal(valorAPagar);
//	}
//
//	/**
//	 * @param mapaMensal
//	 * @param mesAnoAtual
//	 * @return
//	 */
//	public Set<TipoContaNoMesDTO> getResumoPorTipoConta(List<Lancamento> lancamentos) throws NegocioException {
//		Set<TipoContaNoMesDTO> lista = new HashSet<>();
//		lancamentos.stream().collect(
//				Collectors.groupingBy(Lancamento::getTipoConta, Collectors.summingDouble(Lancamento::getValorEmDouble)))
//				.forEach((tipoConta, valor) -> {
//					TipoContaNoMesDTO dto = new TipoContaNoMesDTO(tipoConta.getDescricao(), new BigDecimal(valor));
//					lista.add(dto);
//				});
//
//		return lista;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#gerarLancamentos(br.net
//	 * .walltec.api.dto.GeracaoParcelasDto)
//	 */
//	@Override
//	@Transactional(value = TxType.REQUIRES_NEW)
//	public List<LancamentoVO> gerarLancamentos(GeracaoParcelasDto dto) throws NegocioException {
//
//		if (dto == null) {
//			throw new NegocioException("Informações para geração não informadas!");
//		}
//
//		List<Lancamento> lista = new ArrayList<>();
//		for (Lancamento l : this.montarListaLancamentos(dto)) {
//			this.incluir(l);
//			lista.add(l);
//		}
//		return getConversor().converterEntidadeParaPojo(lista);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#
//	 * montarListaLancamentosVO(br.net.walltec.api.dto.GeracaoParcelasDto)
//	 */
//	@Override
//	public List<LancamentoVO> montarListaLancamentosVO(GeracaoParcelasDto dto) throws NegocioException {
//		List<Lancamento> lista = this.montarListaLancamentos(dto);
//		return getConversor().converterEntidadeParaPojo(lista);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.AbstractCrudServicoPadrao#incluirVO(br.
//	 * net.walltec.api.vo.GerenciadorPadraoVO)
//	 */
//	@Override
//	public LancamentoVO incluirVO(LancamentoVO objeto) throws NegocioException {
//		Date dataFim = objeto.getDataFimStr() != null && !objeto.getDataFimStr().isEmpty()
//				? UtilData.getData(objeto.getDataFimStr(), UtilData.SEPARADOR_PADRAO)
//				: null;
//		Date dataVencimento = UtilData.getData(objeto.getDataVencimentoStr(), UtilData.SEPARADOR_PADRAO);
//
//		if (dataFim != null && dataFim.before(dataVencimento)) {
//			throw new NegocioException("Data Fim não deve ser menor do que a data de Vencimento!");
//		}
//
//		verificarMesFechado(dataVencimento);
//
//		if (dataFim != null) {
//			this.gerarLancamentos(objeto, dataVencimento, dataFim);
//			return objeto;
//		} else {
//			return super.incluirVO(objeto); // this.verificarUtilizacao(objeto, dataVencimento);
//		}
//	}
//
//	/**
//	 * @param dataVencimento
//	 * @throws NegocioException
//	 */
//	private void verificarMesFechado(Date dataVencimento) throws NegocioException {
//		if (isMesFechado(UtilData.getMes(dataVencimento), UtilData.getAno(dataVencimento))) {
//			throw new NegocioException("Não permitido vencimento para esta data. Este mês já se encontra fechado!");
//		}
//	}
//
//	/**
//	 * Verifica se no mes de cadastro do lançamento, existe algum lançamento com a
//	 * mesma rubrica Se existir, o valor do cadastro é adicinado ao lançamento e
//	 * depois será feita uma utilização do mesmo com os dados do lançamento
//	 * informado
//	 * 
//	 * @param objeto
//	 * @return
//	 * @throws NegocioException
//	 */
//	private LancamentoVO verificarUtilizacao(LancamentoVO objeto, Date dataVencimento) throws NegocioException {
//		Optional<Lancamento> optLancamentos = this
//				.listarParcelas(new FiltraParcelasDto(UtilData.getMes(dataVencimento), UtilData.getAno(dataVencimento)))
//				.stream().filter(lancamento -> lancamento.getConta().getId().equals(objeto.getIdConta())).findFirst();
//
//		if (optLancamentos.isPresent()) {
//			Lancamento lancamento = optLancamentos.get();
//			BigDecimal valor = objeto.isDespesa() ? UtilFormatador.formatarStringComoValor(objeto.getValorDebitoStr())
//					: UtilFormatador.formatarStringComoValor(objeto.getValorCreditoStr());
//			if (valor == null) {
//				valor = new BigDecimal(objeto.getValor());
//			}
//			lancamento.setValor(valor.add(lancamento.getValor()));
//			this.alterar(lancamento);
//
//			UtilizacaoParcelasDto dto = new UtilizacaoParcelasDto();
//			dto.setDataUtilizacaoStr(objeto.getDataVencimentoStr());
//			dto.setDescricao(objeto.getDescricao());
//			dto.setIdFormaPagamento(objeto.getIdFormaPagamento());
//			dto.setIdLancamentoOrigem(lancamento.getId());
//			dto.setQtdRegistros(1);
//			dto.setValorUtilizado(valor);
//
//			this.utilizarLancamento(dto);
//
//			return objeto;
//		}
//		return super.incluirVO(objeto);
//	}
//
//	/**
//	 * @param objeto
//	 */
//	private void gerarLancamentos(LancamentoVO objeto, Date dataVencimento, Date dataFim) throws NegocioException {
//		int qtd = (UtilData.getDiasDiferenca(dataVencimento, dataFim) / 30) + 1;
//
//		GeracaoParcelasDto dto = new GeracaoParcelasDto();
//		dto.setDataVencimentoStr(objeto.getDataVencimentoStr());
//		dto.setIdConta(objeto.getIdConta());
//		dto.setQuantidade(qtd);
//		dto.setValorVencimento(objeto.isDespesa() ? UtilFormatador.formatarStringComoValor(objeto.getValorDebitoStr())
//				: UtilFormatador.formatarStringComoValor(objeto.getValorCreditoStr()));
//		dto.setIdParcelaOrigem(objeto.getIdParcelaOrigem());
//		dto.setParcial(false);
//		dto.setIdUsuario(1);
//		dto.setDescricaoParcela(objeto.getDescricao());
//		dto.setDespesa(objeto.isDespesa());
//		dto.setIdFormaPagamento(objeto.getIdFormaPagamento());
//		this.gerarLancamentos(dto);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#associarLancamentos(
//	 * java.util.List)
//	 */
//
//	@Transactional(value = TxType.REQUIRES_NEW)
//	@Override
//	public void associarLancamentos(List<RegistroExtratoDto> lancamentos) throws NegocioException {
//
//		List<RegistroExtratoDto> lancamentosAAtualizar = lancamentos.stream().filter(dto -> !hasValorDiferente(dto))
//				.filter(dto -> !dto.isConciliado()).filter(dto -> dto.isConfirmado()).collect(Collectors.toList());
//
//		if (lancamentosAAtualizar.stream().filter(dto -> hasValorDiferente(dto)).findFirst().isPresent()) {
//			throw new TotalConciliadoInvalidoException("Existem extratos com valores de lançamentos incoerentes");
//		}
//		List<LancamentoVO> lancamentosAAtualizarNaBase = new ArrayList<>();
//		lancamentosAAtualizar.forEach(dto -> {
//			dto.getLancamentos().forEach(vo -> {
//				vo.setBolConciliado(true);
//				vo.setDataVencimentoStr(dto.getDataLancamento());
//				vo.setNumDocumento(dto.getDocumento());
//				lancamentosAAtualizarNaBase.add(vo);
//			});
//		});
//
//		for (LancamentoVO vo : lancamentosAAtualizarNaBase) {
//			try {
//				lancamentoDao.associarLancamentoComExtrato(vo.getId(), vo.getNumDocumento(),
//						UtilData.getData(vo.getDataVencimentoStr(), "/"));
//			} catch (PersistenciaException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * @param dto
//	 * @return
//	 */
//	private boolean hasValorDiferente(RegistroExtratoDto dto) {
//		if (dto.isConfirmado()) {
//			BigDecimal valorExtrato = UtilFormatador.formatarStringComoValor(dto.getValor());
//			return valorExtrato.doubleValue() != dto.calcularTotalLancamentos();
//		}
//		return false;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#desfazerConciliacoes(br
//	 * .net.walltec.api.rest.dto.filtro.DesfazimentoConciliacaoDTO)
//	 */
//	@Transactional(value = TxType.REQUIRES_NEW)
//	@Override
//	public void desfazerConciliacoes(DesfazimentoConciliacaoDTO desfazimentoDTO) throws NegocioException {
//
//		// if estiver fechado, informar que já foi fechado o mês...
//
//		if (LocalDate.now().getMonth().getValue() != desfazimentoDTO.getMes().intValue()
//				&& LocalDate.now().getDayOfMonth() > 2) {
//			throw new NegocioException("Não é possível desfazer as conciliações deste mês!");
//		}
//
//		List<Lancamento> lancamentos = this
//				.listarParcelas(new FiltraParcelasDto(desfazimentoDTO.getMes(), desfazimentoDTO.getAno()));
//
//		lancamentos.stream().forEach(entidade -> {
//			entidade.setBolConciliado(false);
//			entidade.setNumDocumento(null);
//		});
//		this.alterar(lancamentos);
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#fecharMes(br.net.
//	 * walltec.api.rest.dto.filtro.RegistroFechamentoMesDTO)
//	 */
//	@Transactional(value = TxType.REQUIRES_NEW)
//	@Override
//	public void fecharMes(RegistroFechamentoMesDTO fechamentoDTO) throws NegocioException {
//
//		if (this.isMesFechado(fechamentoDTO.getMes(), fechamentoDTO.getAno())) {
//			throw new NegocioException("Este mês já foi fechado!");
//		}
//		FechamentoContabil fechamento = new FechamentoContabil();
//		fechamento.setNumAno(fechamentoDTO.getAno());
//		fechamento.setNumMes(fechamentoDTO.getMes());
//		fechamento.setDataFechamento(new Date());
//		try {
//			fechamentoContabilDao.incluir(fechamento);
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#isMesFechado(int,
//	 * int)
//	 */
//	@Transactional(value = TxType.NOT_SUPPORTED)
//	@Override
//	public boolean isMesFechado(int mes, int ano) throws NegocioException {
//		FechamentoContabil fechamento = new FechamentoContabil();
//		fechamento.setNumAno(ano);
//		fechamento.setNumMes(mes);
//
//		try {
//			FechamentoContabil fc = fechamentoContabilDao.pesquisar(fechamento);
//			return fc != null;
//		} catch (NoResultException e) {
//			return false;
//		} catch (Exception e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#gerarMapaAno(java.lang.
//	 * Integer)
//	 */
//	@Override
//	public List<ResumoMesAnoDTO> gerarMapaAno(Integer ano) throws NegocioException {
//		try {
//			return this.lancamentoDao.gerarMapaAno(ano);
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new NegocioException(e);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#
//	 * listarLancamentosPorRubricaEAno(java.lang.Integer, java.lang.Integer)
//	 */
//	@Override
//	public List<LancamentosPorRubricaDTO> listarLancamentosPorRubricaEAno(Integer ano, Integer idRubrica)
//			throws NegocioException {
//		try {
//			return lancamentoDao.listarLancamentosPorRubricaEAno(ano, idRubrica);
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see br.net.walltec.api.negocio.servicos.LancamentoServico#
//	 * excluirParcelasPorPeriodo(java.util.Date, java.util.Date)
//	 */
//	@Override
//	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = Exception.class)
//	public void excluirParcelasPorPeriodo(Date dataInicio, Date dataFim) throws NegocioException {
//		dataInicio = UtilData.getDataSemHoras(dataInicio);
//		dataFim = UtilData.getDataSemHoras(dataFim);
//		try {
//			this.lancamentoDao.excluirParcelasPagasDebitoPorPeriodo(dataInicio, dataFim);
//		} catch (PersistenciaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#excluirListaParcelas(
//	 * java.util.List)
//	 */
//	@Override
//	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
//	public void excluirListaParcelas(List<Lancamento> lancamentos) throws NegocioException {
//		try {
//			for (Lancamento l : lancamentos) {
//				this.lancamentoDao.excluir(l);
//			}
//		} catch (PersistenciaException e) {
//			throw new NegocioException(e);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * br.net.walltec.api.negocio.servicos.LancamentoServico#conciliarLancamentos(
//	 * java.util.List, java.util.List)
//	 */
//	@Override
//	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
//	public List<RegistroExtratoDto> conciliarLancamentos(List<LancamentoVO> lancamentosDoExtrato,
//			List<DeparaHistoricoBancoVO> listaDeparas, Integer numBanco, Date dataBase) throws NegocioException {
//		Date dataInicio = UtilData.getPrimeiroDiaMes(dataBase);
//
//		FiltraParcelasDto dtoFiltro = new FiltraParcelasDto(UtilData.getMes(dataInicio), UtilData.getAno(dataInicio));
//		List<Lancamento> listaParcelasSalvas = this.listarParcelas(dtoFiltro);
//
//		this.excluirParcelasDebitoNaoConciliadas(listaParcelasSalvas);
//
//		Set<String> documentosConciliados = listaParcelasSalvas.stream()
//				.filter(lancamento -> lancamento.getBolConciliado())
//				.filter(lancamento -> lancamento.getFormaPagamento().getId()
//						.equals(Constantes.ID_FORMA_PAGAMENTO_DEBITO))
//				.filter(lancamento -> lancamento.getBanco() != null
//						&& lancamento.getBanco().getNumBanco().equals(numBanco))
//				.map(lancamento -> lancamento.getNumDocumento()).collect(Collectors.toSet());
//
//		List<Lancamento> lancamentosAIncluir = lancamentosDoExtrato.stream()
//				.filter(lancamento -> !documentosConciliados.contains(lancamento.getNumDocumento())).map(lancamento -> {
//					Lancamento lanc = new Lancamento();
//					lanc.setBolConciliado(true);
//					lanc.setBolPaga(true);
//					lanc.setConta(new Conta());
//					lanc.getConta().setId(lancamento.getIdConta());
//					lanc.setDataVencimento(UtilData.getData(lancamento.getDataVencimentoStr(), "/"));
//					lanc.setDescricao(lancamento.getDescricao());
//					lanc.setFormaPagamento(new FormaPagamento());
//					lanc.getFormaPagamento().setId(lancamento.getIdFormaPagamento());
//					lanc.setNumDocumento(lancamento.getNumDocumento());
//					lanc.setNumero(lancamento.getNumero());
//					lanc.setValor(new BigDecimal(lancamento.getValor().doubleValue()));
//					lanc.setBanco(new Banco());
//					lanc.getBanco().setNumBanco(numBanco);
//
//					return lanc;
//				}).collect(Collectors.toList());
//
//		List<RegistroExtratoDto> dtos = new ArrayList<RegistroExtratoDto>();
//		for (Lancamento lancamento : lancamentosAIncluir) {
//			this.incluir(lancamento);
//			dtos.add(this.montarRegistroExtratoDTO(lancamento));
//		}
//
//		listaParcelasSalvas.stream().filter(lancamento -> lancamento.getBolConciliado()).filter(
//				lancamento -> lancamento.getFormaPagamento().getId().equals(Constantes.ID_FORMA_PAGAMENTO_DEBITO))
//				.filter(lancamento -> lancamento.getBanco().getNumBanco().equals(numBanco))
//				.forEach(lancamento -> dtos.add(this.montarRegistroExtratoDTO(lancamento)));
//
//		return dtos;
//	}
//
//	/**
//	 * @param dataInicio
//	 * @throws NegocioException
//	 */
//	void excluirParcelasDebitoNaoConciliadas(List<Lancamento> listaParcelasSalvas) throws NegocioException {
//
//		if (UtilObjeto.isNotVazio(listaParcelasSalvas)) {
//			List<Lancamento> lancamentosNaoConciliados = listaParcelasSalvas.stream()
//					.filter(lancamento -> lancamento.getFormaPagamento().getId()
//							.equals(Constantes.ID_FORMA_PAGAMENTO_DEBITO))
//					.filter(lancamento -> lancamento.getBolConciliado().booleanValue() == false).map(lancamento -> {
//						lancamento.setLancamentoOrigem(null);
//						return lancamento;
//					}).collect(Collectors.toList());
//
//			this.excluirListaParcelas(lancamentosNaoConciliados);
//		}
//
//	}
//
//	/**
//	 * @param lancamento
//	 * @return
//	 */
//	private RegistroExtratoDto montarRegistroExtratoDTO(Lancamento lancamento) {
//		RegistroExtratoDto dto = new RegistroExtratoDto();
//		dto.setArrayIds(new int[0]);
//		dto.setConciliado(true);
//		dto.setConfirmado(true);
//		dto.setCreditoDebito(lancamento.getConta().getId().equals(152) ? "D" : "C");
//		dto.setDataLancamento(UtilData.getDataFormatada(lancamento.getDataVencimento()));
//		dto.setDocumento(lancamento.getNumDocumento());
//		dto.setHistorico(lancamento.getDescricao());
//		dto.setValor(UtilFormatador.formatarDecimal(lancamento.getValorEmDouble()));
//		return dto;
//	}
//
//	 @Override
//	    public List<Lancamento> criarParcelas(GeracaoParcelasDto dto) throws NegocioException {
//	    		Integer qtd = dto.getQuantidade();
//	            Conta conta = this.find(dto.getIdConta());
//	            conta.getListaLancamentos().size();
//	 
//	            Integer numProxParcela = gerarNumeroProxLancamento(conta);
//	            Date dataVencimento = UtilData.getData(dto.getDataVencimentoStr(), UtilData.SEPARADOR_PADRAO);
//	            List<Lancamento> listaParcelasGeradas = new ArrayList<Lancamento>();
//	            for(int i = 0; i < qtd; i++){
//	                listaParcelasGeradas.add( montarLancamento(dto, conta, numProxParcela, dataVencimento) );
//	                dataVencimento = UtilData.somarData(dataVencimento, 1, ChronoUnit.MONTHS);
//	                numProxParcela++;
//	            }
//
//	            if (dto.getParcial()){
//	            	return listaParcelasGeradas;
//	            } else {
//	            	conta.getListaLancamentos().addAll(listaParcelasGeradas);
//	            	super.alterar(conta);
//	            	return conta.getListaLancamentos();
//	            } 
//
//	    }
//
//		/**
//		 * @param dto
//		 * @param conta
//		 * @param numLancamento
//		 * @param dataVencimento
//		 * @param listaParcelasGeradas
//		 */
//		private Lancamento montarLancamento(GeracaoParcelasDto dto, Conta conta, Integer numLancamento, Date dataVencimento) {
//			Lancamento p = new Lancamento();
//			p.setNumero(numLancamento.shortValue());
//			p.setConta(conta);
//			p.setDataVencimento(dataVencimento);
//			p.setValor(dto.getValorVencimento());
//			p.setDescricao(dto.getDescricaoParcela());
//			p.setBolPaga(false);
//			p.setBolConciliado(false);
//
//			p.setFormaPagamento(new FormaPagamento());
//			p.getFormaPagamento().setId(Constantes.ID_FORMA_PAGAMENTO_PADRAO);
//
//			if (dto.getIdParcelaOrigem() != null) {
//				p.setLancamentoOrigem(new Lancamento());
//				p.getLancamentoOrigem().setId(dto.getIdParcelaOrigem());
//			}
//			return p;
//		}
//
//	    @Override
//	    public Integer gerarNumeroProxLancamento(Conta conta) throws NegocioException {
//	        if (conta.getListaLancamentos() != null && conta.getListaLancamentos().size() > 0){
//	            Lancamento p = conta.getListaLancamentos().get(conta.getListaLancamentos().size()-1);
//	            return new Integer(p.getNumero())+1;
//	        }
//	        return 1;
//	    }	
}