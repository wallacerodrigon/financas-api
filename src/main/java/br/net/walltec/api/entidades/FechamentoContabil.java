/**
 * 
 */
package br.net.walltec.api.entidades;

import java.time.LocalDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

/**
 * @author tr301222
 *
 */
@Data
@Entity
@Table(name="fechamentocontabil", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)

public class FechamentoContabil  extends EntidadeBasica<FechamentoContabil> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idFechamento;

	@NotNull(message="Data do fechamento é obrigatório")
	private LocalDateTime dataFechamento;
	
	@NotNull(message="Ano é obrigatório")
	private Integer numAno;
	
	@NotNull(message="Mês é obrigatório")
	private Integer numMes;

	
}
