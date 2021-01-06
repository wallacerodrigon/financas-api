package br.net.walltec.api.importacao.estrategia;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.spi.CDI;

import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.BancoService;
import br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoService;
import br.net.walltec.api.negocio.servicos.TipoLancamentoService;
import br.net.walltec.api.utilitarios.UtilData;

public class ImportadorCSVBB implements ImportadorArquivo {

	public static final String DATA = "dd/MM/yyyy";	
	private static final String CHARSET_8859_1 = "ISO-8859-1";	
	private static final int NUM_BB = 1;

	private DeparaHistoricoBancoService deparaServico;
	
	private BancoService bancoService;
	
	private TipoLancamentoService tipoLancamentoService;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.importacao.estrategia.ImportadorArquivo#importar(java.lang.String, byte[], java.util.List)
	 */
	@Override
	public List<RegistroExtratoDto> importar(String nomeArquivo, byte[] dadosArquivo, List<Lancamento> listaParcelas)
			throws NegocioException {
		//deparaServico =  CDI.current().select(DeparaHistoricoBancoService.class).get();
		//List<DeparaHistoricoBanco> listaDeparas = this.deparaServico.listaPorBanco(1);
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
		bancoService =  CDI.current().select(BancoService.class).get();
		Banco banco = bancoService.find(1);
		FormaPagamento formaPagamento = banco.getFormaPagamentoParaConciliacao();
		
		tipoLancamentoService =  CDI.current().select(TipoLancamentoService.class).get();

		TipoLancamento tipoLancamento = tipoLancamentoService.find(1);
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		for(String dado : dados) {
			
			if (numLinha == 0) {
				++numLinha;
				continue;
			}
			String linha2 = dado.replaceAll("\"", "");
			String dadosDaLinha[] = linha2.split(",");
			
			Date dataVencimento = UtilData.getDataPorPattern(dadosDaLinha[0].replace("\"", ""), DATA);
			Double valor = Double.valueOf(dadosDaLinha[5]);
			
			Lancamento lancamento = new Lancamento();
			lancamento.setDataHoraConciliacao(new Date());
			lancamento.setBanco(banco);
			lancamento.setDataHoraPagamento(dataVencimento);
			lancamento.setDataVencimento(dataVencimento);
			lancamento.setDescLancamento(dadosDaLinha[2]);
			lancamento.setFormaPagamento(formaPagamento);
			lancamento.setLancamentoOrigem(null);
			lancamento.setNumDocumento(dadosDaLinha[4]);
			lancamento.setTipoLancamento(tipoLancamento);
			lancamento.setValorLancamento(BigDecimal.valueOf(valor));

			
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

	@Override
	public boolean isExtensaoValida(String extensao) {
		return extensao.equalsIgnoreCase("csv");
	}
	
}
