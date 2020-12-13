/**
 * 
 */
package br.net.walltec.api.rest.comum;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.excecoes.WebServiceException;

/**
 * @author wallace
 *
 */
public interface ContratoPadraoRest<T> extends Serializable {

	 @GET
	 @Path("/ping")
	 RetornoRestDTO<T> novo() throws WebServiceException;
	 
	 @DELETE
	 @Path("/{idChaveObjeto}")
	 RetornoRestDTO<T> excluir(@PathParam("idChaveObjeto") Integer idChaveObjeto) throws WebServiceException;
	 
	 @GET
	 @Path("/{idProcura}")
	 RetornoRestDTO<T> procurar(@PathParam("idProcura") Integer idProcura) throws WebServiceException;
	 
	 @GET
	 RetornoRestDTO<PageResponse<List<T>>> listar(@QueryParam(value="page") Integer page, @QueryParam("size") Integer size) throws WebServiceException;
	 
	 @POST
	 RetornoRestDTO<T> salvar(T objeto) throws WebServiceException;
	 
	 @PUT
	 RetornoRestDTO<T> alterar(T objeto) throws WebServiceException;
	 
	 @GET
	 @Path(value = "/pesquisaSimples")
	 RetornoRestDTO<PageResponse<List<T>>> pesquisar(@QueryParam(value="filtroJSON") String filtro, @QueryParam(value="page") Integer page, @QueryParam("size") Integer size) throws WebServiceException;
	 
}
