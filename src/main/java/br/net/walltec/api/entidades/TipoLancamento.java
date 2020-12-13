package br.net.walltec.api.entidades;

import javax.persistence.Cacheable;
import javax.persistence.Column;
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
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="tipolancamento", schema=Constantes.SCHEMA_FINANCAS)
@EqualsAndHashCode(callSuper = false)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoLancamento extends EntidadeBasica<TipoLancamento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idTipoLancamento;
	
	@Column(nullable=false)
	@NotNull(message="Nome do tipo de lançamento é obrigatório")
	@Size(min=3, max=50, message="Nome do tipo de lançamento deve ter entre {min} e {max} caracteres")
	private String nomeTipoLancamento;

	@NotNull(message="Status é obrigatório")
	private Boolean bolAtivo;
	
	@NotNull(message="Informação de despesa ou receita é obrigatório")
	private Boolean bolDespesa;
	
	
}
 
