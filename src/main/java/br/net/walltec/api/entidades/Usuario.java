package br.net.walltec.api.entidades;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.net.walltec.api.entidades.comum.EntidadeBasica;
import br.net.walltec.api.utilitarios.Constantes;
import lombok.Data;

@Data
@Entity
@Table(name="usuario", schema=Constantes.SCHEMA_FINANCAS)
@JsonIgnoreProperties({"nomeSenha", "edit"})
public class Usuario extends EntidadeBasica<Usuario> {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@Column(nullable=false)
	@NotNull(message="Nome do usuário é obrigatório")
	private String nomeUsuario;
	
//	@Column(nullable=false, length=14)	
//	@NotNull(message="CPF é obrigatório")
//	@Size(min=11, max=14, message="CPF deve ter entre {min} e {max} dígitos")
//	private String numCpf;
	

	@Column(nullable=false, length=10)
	private String descSenha;

	@ManyToOne
	@JoinColumn(name="codperfil")
	@NotNull(message="Perfil é obrigatório")
	private Perfil perfil;
	
	@NotNull(message="E-mail é obrigatório")
	@Column(nullable=false, length=50)
	private String descemail;

	@NotNull(message="Data inclusão é obrigatório")
	@Column(nullable=false)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@NotNull(message="Qtd de acessos é obrigatório")
	@Column(nullable=false)	
	private Integer qtdAcessos = 0;

	@NotNull(message="Data último acesso é obrigatório")
	@Column(nullable=false)	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAcesso = new Date();

	@NotNull(message="Status é obrigatório")
	@Column(nullable=false)	
	private boolean bolAtivo = true;
	
}
 
