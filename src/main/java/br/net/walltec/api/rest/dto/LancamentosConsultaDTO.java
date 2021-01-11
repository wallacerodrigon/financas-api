package br.net.walltec.api.rest.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.net.walltec.api.dto.DtoPadrao;
import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.entidades.FormaPagamento;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.entidades.TipoLancamento;
import br.net.walltec.api.utilitarios.UtilData;


public class LancamentosConsultaDTO extends DtoPadrao {
	
	private Lancamento lancamentoProxy;

	private String dataVencimentoString;
	
	private String dataHoraPagamentoString;
	
	private List<LancamentosConsultaDTO> lancamentosDependentes;
	
	public LancamentosConsultaDTO(Lancamento lancamento, List<Lancamento> lancamentosDependentes) {
		this.lancamentoProxy = lancamento;
		this.dataVencimentoString = UtilData.getDataFormatada(lancamento.getDataVencimento());
		this.dataHoraPagamentoString = UtilData.getDataFormatada(lancamento.getDataHoraPagamento());
		this.lancamentosDependentes = new ArrayList<LancamentosConsultaDTO>();
		
		if (lancamentosDependentes != null) {
			this.lancamentosDependentes = lancamentosDependentes.stream()
											.map(lanc -> new LancamentosConsultaDTO(lanc, null))
											.collect(Collectors.toList());
		}
		
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getIdLancamento()
	 */
	public Integer getIdLancamento() {
		return lancamentoProxy.getIdLancamento();
	}

	/**
	 * @param idLancamento
	 * @see br.net.walltec.api.entidades.Lancamento#setIdLancamento(java.lang.Integer)
	 */
	public void setIdLancamento(Integer idLancamento) {
		lancamentoProxy.setIdLancamento(idLancamento);
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getTipoLancamento()
	 */
	public TipoLancamento getTipoLancamento() {
		return lancamentoProxy.getTipoLancamento();
	}

	/**
	 * @param tipoLancamento
	 * @see br.net.walltec.api.entidades.Lancamento#setTipoLancamento(br.net.walltec.api.entidades.TipoLancamento)
	 */
	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		lancamentoProxy.setTipoLancamento(tipoLancamento);
	}



	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getValorLancamento()
	 */
	public BigDecimal getValorLancamento() {
		return lancamentoProxy.getValorLancamento();
	}

	/**
	 * @param valorLancamento
	 * @see br.net.walltec.api.entidades.Lancamento#setValorLancamento(java.math.BigDecimal)
	 */
	public void setValorLancamento(BigDecimal valorLancamento) {
		lancamentoProxy.setValorLancamento(valorLancamento);
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getFormaPagamento()
	 */
	public FormaPagamento getFormaPagamento() {
		return lancamentoProxy.getFormaPagamento();
	}

	/**
	 * @param formaPagamento
	 * @see br.net.walltec.api.entidades.Lancamento#setFormaPagamento(br.net.walltec.api.entidades.FormaPagamento)
	 */
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		lancamentoProxy.setFormaPagamento(formaPagamento);
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getDescLancamento()
	 */
	public String getDescLancamento() {
		return lancamentoProxy.getDescLancamento();
	}

	/**
	 * @param descLancamento
	 * @see br.net.walltec.api.entidades.Lancamento#setDescLancamento(java.lang.String)
	 */
	public void setDescLancamento(String descLancamento) {
		lancamentoProxy.setDescLancamento(descLancamento);
	}

		/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getBanco()
	 */
	public Banco getBanco() {
		return lancamentoProxy.getBanco();
	}

	/**
	 * @param banco
	 * @see br.net.walltec.api.entidades.Lancamento#setBanco(br.net.walltec.api.entidades.Banco)
	 */
	public void setBanco(Banco banco) {
		lancamentoProxy.setBanco(banco);
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getNumDocumento()
	 */
	public String getNumDocumento() {
		return lancamentoProxy.getNumDocumento();
	}

	/**
	 * @param numDocumento
	 * @see br.net.walltec.api.entidades.Lancamento#setNumDocumento(java.lang.String)
	 */
	public void setNumDocumento(String numDocumento) {
		lancamentoProxy.setNumDocumento(numDocumento);
	}

	/**
	 * @return
	 * @see br.net.walltec.api.entidades.Lancamento#getDataHoraConciliacao()
	 */
	public Date getDataHoraConciliacao() {
		return lancamentoProxy.getDataHoraConciliacao();
	}

	/**
	 * @param dataHoraConciliacao
	 * @see br.net.walltec.api.entidades.Lancamento#setDataHoraConciliacao(java.util.Date)
	 */
	public void setDataHoraConciliacao(Date dataHoraConciliacao) {
		lancamentoProxy.setDataHoraConciliacao(dataHoraConciliacao);
	}

	/**
	 * @return the lancamentosDependentes
	 */
	public List<LancamentosConsultaDTO> getLancamentosDependentes() {
		return lancamentosDependentes;
	}

	/**
	 * @param lancamentosDependentes the lancamentosDependentes to set
	 */
	public void setLancamentosDependentes(List<LancamentosConsultaDTO> lancamentosDependentes) {
		this.lancamentosDependentes = lancamentosDependentes;
	}

	/**
	 * @return the dataVencimentoString
	 */
	public String getDataVencimentoString() {
		return dataVencimentoString;
	}

	/**
	 * @param dataVencimentoString the dataVencimentoString to set
	 */
	public void setDataVencimentoString(String dataVencimentoString) {
		this.dataVencimentoString = dataVencimentoString;
	}

	/**
	 * @return the dataHoraPagamentoString
	 */
	public String getDataHoraPagamentoString() {
		return dataHoraPagamentoString;
	}

	/**
	 * @param dataHoraPagamentoString the dataHoraPagamentoString to set
	 */
	public void setDataHoraPagamentoString(String dataHoraPagamentoString) {
		this.dataHoraPagamentoString = dataHoraPagamentoString;
	}
	

	
}
