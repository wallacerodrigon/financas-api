/**
 * 
 */
package br.net.walltec.api.entidades;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="banco", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Banco extends EntidadeBasica<Banco> {

	@Id
	@NotNull(message="Número do banco é obrigatório")
	private Integer numBanco;
	
	@NotNull(message="Nome do banco é obrigatório")
	@Size(min=3, max=50, message="Nome do banco deve ter entre {min} e {max} caracteres")
	private String nomeBanco;
	
	@NotNull(message="Sigla do banco é obrigatório")
	@Size(min=3, max=50, message="Sigla do banco deve ter entre {min} e {max} caracteres")
	private String nomeSigla;

}
