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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

@Data
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
	@NotNull(message="Tipo lançamento é obrigatório")
	private TipoLancamento tipoLancamento;
	 
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@NotNull(message="Data de vencimento é obrigatório")
	private Date dataVencimento;
	 
	@Column(precision=2, nullable=false)
	@NotNull(message="Valor do lançamento é obrigatório")
	private BigDecimal valorLancamento;

	@NotNull(message="Forma de pagamento é obrigatório")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idformapagamento", nullable=false)	
	private FormaPagamento formaPagamento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idlancamentoorigem")	
	private Lancamento lancamentoOrigem;
	
	@NotNull(message="Descrição do lançamento é obrigatório")
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
	
	@Transient
	@NotNull(message="Data de vencimento é obrigatório")
	private String dataVencimentoString;
	
	@Transient
	private String dataHoraPagamentoString;

	public boolean isPago() {
		return this.dataHoraPagamento != null;
	}

}
 
