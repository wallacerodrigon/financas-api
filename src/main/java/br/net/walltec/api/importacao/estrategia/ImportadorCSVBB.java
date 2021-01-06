package br.net.walltec.api.importacao.estrategia;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;

import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
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
	

	/* (non-Javadoc)
	 * @see br.net.walltec.api.importacao.estrategia.ImportadorArquivo#importar(java.lang.String, byte[], java.util.List)
	 */
	@Override
	public List<Lancamento> importar(String nomeArquivo, byte[] dadosArquivo)
			throws NegocioException {
	
		List<Lancamento> lancamentosConvertidos = this.recuperarListaLancamento(dadosArquivo);
		return lancamentosConvertidos;
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
		
		bancoService =  CDI.current().select(BancoService.class).get();
		Banco banco = bancoService.find(NUM_BB);

		deparaServico =  CDI.current().select(DeparaHistoricoBancoService.class).get();
		PageResponse<List<DeparaHistoricoBanco>> response = this.deparaServico.listar(new PageRequest(0, 1000000));
		List<DeparaHistoricoBanco> listaDeparas = response.getResultado().stream().filter(depara -> depara.getBanco().getNumBanco().equals(NUM_BB)).collect(Collectors.toList());
		
		return extrairDadosParaLancamentos(dados, banco, listaDeparas);
	}

	/**
	 * @param dados
	 * @param numLinha
	 * @param banco
	 * @param formaPagamento
	 * @param tipoLancamento
	 * @param lancamentos
	 */
	private List<Lancamento> extrairDadosParaLancamentos(String[] dados, Banco banco, List<DeparaHistoricoBanco> listaDeparas) {
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
			Double valor = Double.valueOf(dadosDaLinha[5]);
			
		    DeparaHistoricoBanco deparaHistoricoBanco = this.recuperarDepara(dadosDaLinha[2], listaDeparas);
			
			Lancamento lancamento = new Lancamento();
			lancamento.setDataHoraConciliacao(new Date());
			lancamento.setBanco(banco);
			lancamento.setDataHoraPagamento(dataVencimento);
			lancamento.setDataVencimento(dataVencimento);
			lancamento.setDescLancamento(dadosDaLinha[2]);
			lancamento.setFormaPagamento(banco.getFormaPagamentoParaConciliacao());
			lancamento.setLancamentoOrigem(null);
			lancamento.setNumDocumento( deparaHistoricoBanco != null ? deparaHistoricoBanco.getNomeDestino() : dadosDaLinha[2] );
			lancamento.setTipoLancamento(new TipoLancamento());
			
			if (deparaHistoricoBanco != null) {
				lancamento.setTipoLancamento(deparaHistoricoBanco.getTipoLancamento());
			} else {
				lancamento.getTipoLancamento().setIdTipoLancamento(152);
			}
			
			
			lancamento.setValorLancamento(BigDecimal.valueOf(valor < 0 ? valor * -1 : valor ));

			System.out.println("linha avaliada: " + numLinha);
			++numLinha;
			lancamentos.add(lancamento);
		}
		return lancamentos;
	}
//
	/**
	 * @param string
	 * @param listaDeparas
	 * @return
	 */
	private DeparaHistoricoBanco recuperarDepara(String nomeExtrato, List<DeparaHistoricoBanco> listaDeparas) {
		return listaDeparas
				.stream()
				.filter(depara -> nomeExtrato.toLowerCase().contains(depara.getNomeOrigem().toLowerCase()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public boolean isExtensaoValida(String extensao) {
		return extensao.equalsIgnoreCase("csv");
	}
	
}
