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

@Data
@Entity
@Table(name="formapagamento", schema=Constantes.SCHEMA_FINANCAS)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormaPagamento extends EntidadeBasica<FormaPagamento> {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idFormaPagamento;
	
	@Column(nullable=false)
	@NotNull(message="Nome da forma de pagamento é obrigatório")
	@Size(min=3, max=50, message="Nome da forma de pagamento deve ter entre {min} e {max} caracteres")
	private String nomeFormaPagamento;
	 
}
 
