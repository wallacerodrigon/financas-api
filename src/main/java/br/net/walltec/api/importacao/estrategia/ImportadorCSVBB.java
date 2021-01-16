package br.net.walltec.api.importacao.estrategia;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.utilitarios.UtilData;

public class ImportadorCSVBB extends AbstractImportadorArquivo {

	public static final String DATA = "dd/MM/yyyy";	
	private static final String CHARSET_8859_1 = "ISO-8859-1";	
	private static final int NUM_BB = 1;


	
	
	/**
	 * @return Retorna a data no formato dd/mm/yyyy.
	 */
	public static String getDataFormatoString(Date data) {
		return data==null ? null : new SimpleDateFormat(DATA).format(data);
	}
	

	/**
	 * @param dados
	 * @param numLinha
	 * @param banco
	 * @param formaPagamento
	 * @param tipoLancamento
	 * @param lancamentos
	 */
	@Override
	protected List<Lancamento> extrairDadosParaLancamentos(String[] dados, Banco banco, List<DeparaHistoricoBanco> listaDeparas) {
		int numLinha = 0;
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		for(String dado : dados) {
			
			if (numLinha == 0) {
				++numLinha;
				continue;
			}
			String linha2 = dado.replaceAll("\"", "");
			String dadosDaLinha[] = linha2.split(",");
			
			if (dadosDaLinha[2].toLowerCase().indexOf("cdb") > -1 || dadosDaLinha[2].indexOf("S A L D O") > -1) {
				continue;
			}
			
			Date dataVencimento = UtilData.getDataPorPattern(dadosDaLinha[0].replace("\"", ""), DATA);
			Double valor = Double.valueOf(dadosDaLinha[5]);
			
		    DeparaHistoricoBanco deparaHistoricoBanco = this.recuperarDepara(dadosDaLinha[2], listaDeparas);
			
			Lancamento lancamento = new Lancamento();
			lancamento.setDataHoraConciliacao(new Date());
			lancamento.setBanco(banco);
			lancamento.setDataHoraPagamento(dataVencimento);
			lancamento.setDataVencimento(dataVencimento);
			lancamento.setDescLancamento(deparaHistoricoBanco != null ? deparaHistoricoBanco.getNomeDestino() : dadosDaLinha[2] );
			lancamento.setFormaPagamento(banco.getFormaPagamentoParaConciliacao());
			//lancamento.setNumDocumento( dadosDaLinha[4] );
			lancamento.setTipoLancamento(new TipoLancamento());
			
			if (deparaHistoricoBanco != null) {
				lancamento.setTipoLancamento(deparaHistoricoBanco.getTipoLancamento());
			} else {
				lancamento.getTipoLancamento().setIdTipoLancamento( valor < 0 ? NUM_TIPO_LANC_DESPESA : NUM_TIPO_LANC_RECEITA  );
			}
			
			
			lancamento.setValorLancamento(BigDecimal.valueOf(valor < 0 ? valor * -1 : valor ));

			++numLinha;
			lancamentos.add(lancamento);
		}
		
		if (lancamentos.get(0).getDataVencimento().before(lancamentos.get(1).getDataVencimento())) {
			lancamentos.get(0).setDataVencimento(lancamentos.get(1).getDataVencimento());
		}
		
		return lancamentos;
	}
//
	

	@Override
	public boolean isExtensaoValida(String extensao) {
		return extensao.equalsIgnoreCase("csv");
	}


	@Override
	protected Integer getNumeroBanco() {
		// TODO Auto-generated method stub
		return NUM_BB;
	}


	@Override
	protected String getCharset() {
		// TODO Auto-generated method stub
		return CHARSET_8859_1;
	}
	
}
