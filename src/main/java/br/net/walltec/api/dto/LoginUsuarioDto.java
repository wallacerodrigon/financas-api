package br.net.walltec.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginUsuarioDto {
	/**
	 * 
	 */
	@NotNull(message = "Favor informar o e-mail para logar")
	@Size(min=10, max = 50, message ="Email deve ter entre {min} e {max} caracteres")
	@Pattern(message = "E-mail inválido", regexp = ".+@.+\\..+")
	private String email;

	@NotNull(message="Favor informar a senha")
	@Size(min=6, max = 10, message="Senha deve ter entre {min} e {max} caracteres")
	private String senha;
	

}
