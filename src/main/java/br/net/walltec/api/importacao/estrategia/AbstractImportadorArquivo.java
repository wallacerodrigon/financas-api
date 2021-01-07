package br.net.walltec.api.importacao.estrategia;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;

import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.BancoService;
import br.net.walltec.api.negocio.servicos.DeparaHistoricoBancoService;

public abstract class AbstractImportadorArquivo implements ImportadorArquivo {

	private DeparaHistoricoBancoService deparaServico;
	
	private BancoService bancoService;
	
	protected Integer NUM_TIPO_LANC_RECEITA = 736;
	
	protected Integer NUM_TIPO_LANC_DESPESA = 152;
	
	/**
	 * @param string
	 * @param listaDeparas
	 * @return
	 */
	protected DeparaHistoricoBanco recuperarDepara(String nomeExtrato, List<DeparaHistoricoBanco> listaDeparas) {
		return listaDeparas
				.stream()
				.filter(depara -> nomeExtrato.toLowerCase().contains(depara.getNomeOrigem().toLowerCase()))
				.findFirst()
				.orElse(null);
	}
	
	public boolean isExtensaoValida(String extensao) {
		return extensao.equalsIgnoreCase("txt");
	}
	
	/**
	 * @param dadosArquivo
	 * @param listaDeparas 
	 */
	private List<Lancamento> recuperarListaLancamento(byte[] dadosArquivo) throws NegocioException {
		String[] dados = new String(dadosArquivo, Charset.forName(getCharset())).split("\n");
		
		bancoService =  CDI.current().select(BancoService.class).get();
		Banco banco = bancoService.find(getNumeroBanco());

		deparaServico =  CDI.current().select(DeparaHistoricoBancoService.class).get();
		PageResponse<List<DeparaHistoricoBanco>> response = this.deparaServico.listar(new PageRequest(0, 1000000));
		List<DeparaHistoricoBanco> listaDeparas = response.getResultado().stream().filter(depara -> depara.getBanco().getNumBanco().equals(getNumeroBanco())).collect(Collectors.toList());
		
		return extrairDadosParaLancamentos(dados, banco, listaDeparas);
	}
	
	protected abstract Integer getNumeroBanco();

	protected abstract String getCharset();
	
	protected abstract List<Lancamento> extrairDadosParaLancamentos(String[] dados, Banco banco, List<DeparaHistoricoBanco> listaDeparas);

	@Override
	public List<Lancamento> importar(String nomeArquivo, byte[] dadosArquivo) throws NegocioException {
		return this.recuperarListaLancamento(dadosArquivo);	
	}
	
}
