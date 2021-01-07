package br.net.walltec.api.importacao.estrategia;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.utilitarios.UtilData;

public class ImportadorCefTxt extends AbstractImportadorArquivo  {

	/**
	 * 
	 */
	private static final int NUM_BANCO_CEF = 104;
	private static final String REGEX_DATA_DOCUMENTO = "[0-9]{4}[0-9]{2}[0-9]{2}";
	private static final String QUEBRA_LINHA = "\n";
	private static final String CHARSET_8859_1 = "ISO-8859-1";
	private static final String DOCUMENTO_DESCARTE = "000000";

	private static final int INDICE_DATA = 1;
	private static final int INDICE_DOC = 2;
	private static final int INDICE_HISTORICO = 3;
	private static final int INDICE_VALOR = 4;
	private static final int INDICE_DEB_CRED = 5;
	private static final String DATA = "yyyyMMdd";

	@Override
	protected Integer getNumeroBanco() {
		// TODO Auto-generated method stub
		return NUM_BANCO_CEF;
	}


	@Override
	protected String getCharset() {
		// TODO Auto-generated method stub
		return CHARSET_8859_1;
	}


	@Override
	protected List<Lancamento> extrairDadosParaLancamentos(String[] dados, Banco banco,
			List<DeparaHistoricoBanco> listaDeparas) {
		return Stream.of(dados).skip(1).map(linha -> {
			String[] dadosLinhas = linha.split(";");

			if (dadosLinhas[2].replaceAll("\"", "").equals(DOCUMENTO_DESCARTE)) {
				return null;
			}
			
			String descricao = dadosLinhas[INDICE_HISTORICO].replaceAll("\"", "").trim();
					
			Date dataVencimento = UtilData.getDataPorPattern(dadosLinhas[INDICE_DATA].replaceAll("\"", ""), DATA);
		    DeparaHistoricoBanco deparaHistoricoBanco = this.recuperarDepara(descricao, listaDeparas);

			Lancamento lancamento = new Lancamento();
			lancamento.setDataHoraConciliacao(new Date());
			lancamento.setBanco(banco);
			lancamento.setDataHoraPagamento(dataVencimento);
			lancamento.setDataVencimento(dataVencimento);
			lancamento.setDescLancamento(deparaHistoricoBanco != null ? deparaHistoricoBanco.getNomeDestino() : descricao );
			lancamento.setFormaPagamento(banco.getFormaPagamentoParaConciliacao());
			lancamento.setNumDocumento( dadosLinhas[INDICE_DOC].replaceAll("\"", "").trim() );
			lancamento.setTipoLancamento(new TipoLancamento());
			
			Double valor = Double.valueOf(dadosLinhas[INDICE_VALOR].replaceAll("\"", ""));
			if (deparaHistoricoBanco != null) {
				lancamento.setTipoLancamento(deparaHistoricoBanco.getTipoLancamento());
			} else {
				lancamento.getTipoLancamento().setIdTipoLancamento(dadosLinhas[INDICE_DEB_CRED].replaceAll("\"", "").equals("D") ? NUM_TIPO_LANC_DESPESA : NUM_TIPO_LANC_RECEITA);
			}
			
			
			lancamento.setValorLancamento(BigDecimal.valueOf(valor < 0 ? valor * -1 : valor ));				
			
			
			return lancamento;
		}).filter(lanc -> lanc != null)
		.collect(Collectors.toList());
	}



		
}
