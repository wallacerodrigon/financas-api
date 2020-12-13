/**
 * 
 */
package br.net.walltec.api.rest.comum;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
public class RetornoRestDTO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer code;
	
	private String message;
	
	private Object retorno;
	
	public RetornoRestDTO<T> comEsteCodigo(Status code) {
		this.code = code.getStatusCode();
		return this;
	}

	public RetornoRestDTO<T> comEstaMensagem(String mensagem) {
		this.message = mensagem;
		return this;
	}

	public RetornoRestDTO<T> comEsteRetorno(Object retorno) {
		this.retorno = retorno;
		return this;
	}

	public Response construirResponse() {
		if (code == null) {
			this.code = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		}
		
		return Response.status(Status.fromStatusCode(this.code)).entity(this).build();
		
	}
	

	public RetornoRestDTO<T>  construir() {
		if (code == null) {
			this.code = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		}
		
		return this;
		
	}
}
