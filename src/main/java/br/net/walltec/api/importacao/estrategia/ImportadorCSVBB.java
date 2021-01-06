package br.net.walltec.api.importacao.estrategia;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.spi.CDI;

import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoService;
import br.net.walltec.api.utilitarios.UtilData;

public class ImportadorCSVBB implements ImportadorArquivo {

	public static final String DATA = "dd/MM/yyyy";	
	private static final String CHARSET_8859_1 = "ISO-8859-1";	
	private static final int NUM_BB = 1;

	private DeparaHistoricoBancoService deparaServico;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.importacao.estrategia.ImportadorArquivo#importar(java.lang.String, byte[], java.util.List)
	 */
	@Override
	public List<RegistroExtratoDto> importar(String nomeArquivo, byte[] dadosArquivo, List<Lancamento> listaParcelas)
			throws NegocioException {
		deparaServico =  CDI.current().select(DeparaHistoricoBancoService.class).get();
		List<DeparaHistoricoBanco> listaDeparas = this.deparaServico.listaPorBanco(1);
		List<Lancamento> lancamentosConvertidos = this.recuperarListaLancamento(dadosArquivo);
//		Date dataBase = listaVOs.size() > 1 ? UtilData.getData(listaVOs.get(1).getDataVencimentoStr(), "/") : new Date();
//		Date dataInicio = UtilData.getPrimeiroDiaMes(dataBase);
//		listaVOs.get(0).setDataVencimentoStr( UtilData.getDataFormatada(dataInicio, "dd/MM/yyyy") );
//		listaVOs.remove(listaVOs.size() -1);
		return null;
	}
	
	/**
	 * @return Retorna a data no formato dd/mm/yyyy.
	 */
	public static String getDataFormatoString(Date data) {
		return data==null ? null : new SimpleDateFormat(DATA).format(data);
	}
	/**
	 * @param dadosArquivo
	 * @param listaDeparas 
	 */
	private List<Lancamento> recuperarListaLancamento(byte[] dadosArquivo) throws NegocioException {
		String[] dados = new String(dadosArquivo, Charset.forName(CHARSET_8859_1)).split("\n");
		
		int numLinha = 0;
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		for(String dado : dados) {
			
			if (numLinha == 0) {
				++numLinha;
				continue;
			}
			String linha2 = dado.replaceAll("\"", "");
			String dadosDaLinha[] = linha2.split(",");
			
			Date dataVencimento = UtilData.getDataPorPattern(dadosDaLinha[0].replace("\"", ""), DATA);
			//String dataVencimentoStr = getDataFormatoString(dataVencimento, DATA);
			
			//String conta = dadosDaLinha[5].startsWith("-") ? Constantes.ID_CONTA_DEBITO.toString() : Constantes.ID_CONTA_CREDITO.toString();
			//String valor = dadosDaLinha[5].replaceAll("[-]", "");
			
			//DeparaHistoricoBancoVO depara = recuperarDepara(dadosDaLinha[2], listaDeparas);
			
//			LancamentoVO vo = new LancamentoVO();
//			vo.setBolConciliado(true);
//			vo.setBolPaga(true);
//			vo.setDataVencimentoStr( dataVencimentoStr );
//			vo.setIdFormaPagamento(Constantes.ID_FORMA_PAGAMENTO_DEBITO);
//			vo.setDescricao(UtilObjeto.isNotVazio(depara) ? depara.getNomeDestino() : dadosDaLinha[2]);
//			vo.setDescConta(vo.getDescricao());
//			vo.setDespesa(conta.equals(Constantes.ID_CONTA_DEBITO.toString()));
//			vo.setIdConta(Integer.valueOf(conta));
//			vo.setNumDocumento(dadosDaLinha[4]);
//			vo.setNumero(Short.valueOf("1"));
//			vo.setValorDebitoStr(valor);
//			vo.setValorCreditoStr(valor);
//			vo.setValor(Double.valueOf(valor));
//			
			//lancamentos.add(vo);
			
			++numLinha;
		}
		return lancamentos;
	}
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
	
}
