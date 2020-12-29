/**
 * 
 */
package br.net.walltec.api.entidades;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

/**
 * @author macbookpro
 *
 */

@Data
@Entity
@Table(name="endpoint", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@JsonIgnoreProperties(value = {"edit"})
public class Endpoint extends EntidadeBasica<Endpoint> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idEndpoint;
	
	@NotNull(message="Nome do serviço é obrigatório")
	@Size(min=3, max=50, message="Nome do serviço deve ter entre {min} e {max} caracteres")
	private String nomeRecurso;
	
	
	@NotNull(message="Nome do endpoint é obrigatório")
	@Size(min=3, max=50, message="Nome do endpoint deve ter entre {min} e {max} caracteres")
	private String nomeEndpoint;
	
	@NotNull(message="Status é obrigatório")
	private Boolean bolAtivo;
	

}
