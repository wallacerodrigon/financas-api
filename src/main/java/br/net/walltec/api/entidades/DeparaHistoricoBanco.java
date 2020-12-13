/**
 * 
 */
package br.net.walltec.api.entidades;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
@Entity
@Table(name="deparahistoricobanco", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class DeparaHistoricoBanco extends EntidadeBasica<DeparaHistoricoBanco> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idDepara;
	
	@ManyToOne
	@JoinColumn(name="numbanco")
	@NotNull(message="Nome do banco é obrigatório")
	private Banco banco;
	
	@NotNull(message="Nome origem do extrato é obrigatório")
	@Size(min=3, max=50, message="Nome origem deve ter entre {min} e {max} caracteres")
	private String nomeOrigem;
	
	@NotNull(message="Nome destino é obrigatório")
	@Size(min=3, max=50, message="Nome destino deve ter entre {min} e {max} caracteres")
	private String nomeDestino;
	
	@ManyToOne
	@JoinColumn(name="idtipolancamento", nullable=false)
	@NotNull(message="Tipo lançamento é obrigatório")
	private TipoLancamento tipoLancamento;
	
	@NotNull(message="Status é obrigatório")
	private boolean bolAtivo;

}
