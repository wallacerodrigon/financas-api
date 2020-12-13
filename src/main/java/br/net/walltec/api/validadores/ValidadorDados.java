/**
 * 
 */
package br.net.walltec.api.validadores;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.net.walltec.api.excecoes.NegocioException;

/**
 * Valida os dados de entrada usando o bean validator da especificação
 * @author wallace
 *
 */
public final class ValidadorDados {
	
	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	/**
	 * @param dto
	 * @throws NegocioException
	 */
	public static void validarDadosEntrada(Object dto) throws NegocioException {
		Set<ConstraintViolation<Object>> validacoes = validator.validate(dto);
		StringBuilder builder = new StringBuilder();
		builder.append( validacoes.stream().map(validador -> validador.getMessage()).collect(Collectors.joining(";")) );
		if (!builder.toString().isEmpty()) {
			throw new NegocioException(builder.toString());
		}
	}
	
}
