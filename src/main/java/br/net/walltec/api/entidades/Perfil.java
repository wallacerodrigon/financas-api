/**
 * 
 */
package br.net.walltec.api.entidades;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	
	@NotNull(message="Informação se admin é obrigatorio")
	@Column(nullable=false)
	private boolean bolAdmin;	
	
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name=Constantes.SCHEMA_FINANCAS+".perfilendpoint", 
    	joinColumns=
		{@JoinColumn(name="idperfil")}, 
		inverseJoinColumns=
		{@JoinColumn(name="idendpoint")})
    @ToString.Exclude
	private Set<Endpoint> listaEndpoints;
}
