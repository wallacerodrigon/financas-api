package br.net.walltec.api.entidades;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

//@Data
@Entity
@Table(name="lancamento", schema=Constantes.SCHEMA_FINANCAS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lancamento extends EntidadeBasica<Lancamento> {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Integer idLancamento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idtipolancamento", nullable=false)	
	private TipoLancamento tipoLancamento;
	 
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dataVencimento;
	 
	@Column(precision=2, nullable=false)
	private BigDecimal valorLancamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idformapagamento", nullable=false)	
	private FormaPagamento formaPagamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idlancamentoorigem")	
	private Lancamento lancamentoOrigem;
	
	@Column(nullable=false)
	private String descLancamento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraPagamento;

	@ManyToOne
	@JoinColumn(name="numbanco")
	private Banco banco;
	
	private String numDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraConciliacao;
	
	public boolean isPago() {
		return this.dataHoraPagamento != null;
	}

	/**
	 * @return the idLancamento
	 */
	public Integer getIdLancamento() {
		return idLancamento;
	}

	/**
	 * @param idLancamento the idLancamento to set
	 */
	public void setIdLancamento(Integer idLancamento) {
		this.idLancamento = idLancamento;
	}

	/**
	 * @return the tipoLancamento
	 */
	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	/**
	 * @param tipoLancamento the tipoLancamento to set
	 */
	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	/**
	 * @return the dataVencimento
	 */
	public Date getDataVencimento() {
		return dataVencimento;
	}

	/**
	 * @param dataVencimento the dataVencimento to set
	 */
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	/**
	 * @return the valorLancamento
	 */
	public BigDecimal getValorLancamento() {
		return valorLancamento;
	}

	/**
	 * @param valorLancamento the valorLancamento to set
	 */
	public void setValorLancamento(BigDecimal valorLancamento) {
		this.valorLancamento = valorLancamento;
	}

	/**
	 * @return the formaPagamento
	 */
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	/**
	 * @return the lancamentoOrigem
	 */
	public Lancamento getLancamentoOrigem() {
		return lancamentoOrigem;
	}

	/**
	 * @param lancamentoOrigem the lancamentoOrigem to set
	 */
	public void setLancamentoOrigem(Lancamento lancamentoOrigem) {
		this.lancamentoOrigem = lancamentoOrigem;
	}

	/**
	 * @return the descLancamento
	 */
	public String getDescLancamento() {
		return descLancamento;
	}

	/**
	 * @param descLancamento the descLancamento to set
	 */
	public void setDescLancamento(String descLancamento) {
		this.descLancamento = descLancamento;
	}

	/**
	 * @return the dataHoraPagamento
	 */
	public Date getDataHoraPagamento() {
		return dataHoraPagamento;
	}

	/**
	 * @param dataHoraPagamento the dataHoraPagamento to set
	 */
	public void setDataHoraPagamento(Date dataHoraPagamento) {
		this.dataHoraPagamento = dataHoraPagamento;
	}

	/**
	 * @return the banco
	 */
	public Banco getBanco() {
		return banco;
	}

	/**
	 * @param banco the banco to set
	 */
	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	/**
	 * @return the numDocumento
	 */
	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * @param numDocumento the numDocumento to set
	 */
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	/**
	 * @return the dataHoraConciliacao
	 */
	public Date getDataHoraConciliacao() {
		return dataHoraConciliacao;
	}

	/**
	 * @param dataHoraConciliacao the dataHoraConciliacao to set
	 */
	public void setDataHoraConciliacao(Date dataHoraConciliacao) {
		this.dataHoraConciliacao = dataHoraConciliacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idLancamento == null) ? 0 : idLancamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (idLancamento == null) {
			if (other.idLancamento != null)
				return false;
		} else if (!idLancamento.equals(other.idLancamento))
			return false;
		return true;
	}

	
	
}
 
