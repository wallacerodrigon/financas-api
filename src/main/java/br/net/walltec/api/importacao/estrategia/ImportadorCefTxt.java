package br.net.walltec.api.importacao.estrategia;

import java.util.List;

import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoService;
import br.net.walltec.api.negocio.servicos.LancamentoService;

public class ImportadorCefTxt implements ImportadorArquivo {

	/**
	 * 
	 */
	private static final int NUM_BANCO_CEF = 104;
	private static final String FLAG_DEBITO = "D";
	private static final String FLAG_CREDITO = "C";
	private static final String REGEX_DATA_DOCUMENTO = "[0-9]{4}[0-9]{2}[0-9]{2}";
	private static final String QUEBRA_LINHA = "\n";
	private static final String CHARSET_8859_1 = "ISO-8859-1";
	private static final String DOCUMENTO_DESCARTE = "000000";

	private static final int INDICE_DATA = 1;
	private static final int INDICE_DOC = 2;
	private static final int INDICE_HISTORICO = 3;
	private static final int INDICE_VALOR = 4;
	private static final int INDICE_FLAG = 5;
	private static final String DATA = "yyyyMMdd";

	private LancamentoService servico;
	
	private DeparaHistoricoBancoService deparaServico;
//
//	@Override
//	public List<RegistroExtratoDto> importar(String nomeArquivo, byte[] dadosArquivo, List<LancamentoVO> listaParcelas)
//			throws NegocioException {
//		deparaServico =  CDI.current().select(DeparaHistoricoBancoServico.class).get();
//
//		List<DeparaHistoricoBancoVO> listaDeparas = this.deparaServico.listaPorBanco(NUM_BANCO_CEF);
//		
//		List<LancamentoVO> listaVOs = montarListaExtrato(
//				new String(dadosArquivo, Charset.forName(CHARSET_8859_1)).split(QUEBRA_LINHA), listaDeparas);
//		servico = CDI.current().select(LancamentoServico.class).get();
//
//		if (!listaVOs.isEmpty()) {
//
//			Date dataBase = UtilData.getData(listaVOs.get(0).getDataVencimentoStr(), "/");
//			return servico.conciliarLancamentos(listaVOs, listaDeparas, NUM_BANCO_CEF, dataBase);
//		} else {
//			return new ArrayList<RegistroExtratoDto>();
//		}
//
//	}
//
//	/**
//	 * @param linhas
//	 * @return
//	 */
//	private List<LancamentoVO> montarListaExtrato(String[] linhas, List<DeparaHistoricoBancoVO> listaDeParas) {
//		List<LancamentoVO> dtos = Stream.of(linhas).skip(1).map(linha -> {
//			String[] dados = linha.split(";");
//
//			if (!dados[2].replaceAll("\"", "").equals(DOCUMENTO_DESCARTE)) {
//				LancamentoVO vo = new LancamentoVO();
//				boolean despesa = dados[INDICE_FLAG].replaceAll("\"", "").trim().equals(FLAG_DEBITO);
//				String descricao = dados[INDICE_HISTORICO].replaceAll("\"", "").trim();
//						
//				Date dataVencimento = UtilData.getDataPorPattern(dados[INDICE_DATA].replaceAll("\"", ""), DATA);
//				DeparaHistoricoBancoVO depara = recuperarDepara(descricao, listaDeParas);
//
//				vo.setBolConciliado(true);
//				vo.setBolPaga(true);
//				vo.setDataVencimentoStr(UtilData.getDataFormatada(dataVencimento));
//				vo.setIdFormaPagamento(Constantes.ID_FORMA_PAGAMENTO_DEBITO);
//				vo.setDescricao(UtilObjeto.isNotVazio(depara) ? depara.getNomeDestino() : descricao);
//				vo.setDescConta(vo.getDescricao());
//				vo.setDespesa(despesa);
//				vo.setIdConta(despesa ? Constantes.ID_CONTA_DEBITO : Constantes.ID_CONTA_CREDITO);
//				vo.setNumDocumento(dados[INDICE_DOC].replaceAll("\"", "").trim());
//				vo.setNumero(Short.valueOf("1"));
//				vo.setValorDebitoStr(dados[INDICE_VALOR].replaceAll("\"", ""));
//				vo.setValorCreditoStr(dados[INDICE_VALOR].replaceAll("\"", ""));
//				vo.setValor(Double.valueOf(dados[INDICE_VALOR].replaceAll("\"", "")));
//				return vo;
//			}
//
//			return null;
//		}).filter(lanc -> lanc != null).collect(Collectors.toList());
//		return dtos;
//	}
//
//	/**
//	 * @param string
//	 * @param listaDeparas
//	 * @return
//	 */
//	private DeparaHistoricoBancoVO recuperarDepara(String nomeExtrato, List<DeparaHistoricoBancoVO> listaDeparas) {
//		return listaDeparas
//				.stream()
//				.filter(depara -> nomeExtrato.toLowerCase().contains(depara.getNomeOrigem().toLowerCase()))
//				.findFirst()
//				.orElse(null);
//	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.importacao.estrategia.ImportadorArquivo#importar(java.lang.String, byte[], java.util.List)
	 */
	@Override
	public List<RegistroExtratoDto> importar(String nomeArquivo, byte[] dadosArquivo, List<Lancamento> listaParcelas)
			throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}
		
}
