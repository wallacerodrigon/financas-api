/**
 * 
 */
package br.net.walltec.api.entidades;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;
import lombok.ToString;

/**
 * @author wallace
 *
 */
@Data
@ToString
@Entity
@Table(name="perfil", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
public class Perfil extends EntidadeBasica<Perfil> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Integer idPerfil;
	
	@NotNull(message="Nome do perfil é obrigatorio")
	@Column(nullable=false)
	private String nomePerfil;
	
	@NotNull(message="Status é obrigatorio")
	@Column(nullable=false)
	private boolean bolAtivo;
	
}
