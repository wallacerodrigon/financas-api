/**
 * 
 */
package br.net.walltec.api.comum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wallace
 *
 */
@JsonIgnoreProperties({"links"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

	private int pagina;

	private int qtdPorPagina;

	private int qtdRegistros;
	
	private int qtdPaginas;

	private String[] links;

	private T resultado;

	public boolean isEmpty() {
		return qtdRegistros == 0;
	}

}
