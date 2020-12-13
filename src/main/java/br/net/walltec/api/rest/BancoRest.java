/**
 * 
 */
package br.net.walltec.api.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.negocio.servicos.BancoService;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.rest.comum.RequisicaoRestPadrao;
import br.net.walltec.api.rest.interceptors.RequisicaoInterceptor;
import io.swagger.annotations.Api;

/**
 * @author wallace
 *
 */
@Named
@RequestScoped

@Path("/bancos")
@Produces(value={MediaType.APPLICATION_JSON})
@Consumes(value={MediaType.APPLICATION_JSON})
@Interceptors({RequisicaoInterceptor.class})

@Api(value="Webservice de bancos")

@SuppressWarnings("serial")
public class BancoRest extends RequisicaoRestPadrao<Banco> {
	
	@Inject
	private BancoService service;

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getServico()
	 */
	@Override
	public CrudPadraoService<Banco> getServico() {
		return service;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.rest.comum.RequisicaoRestPadrao#getClasseEntidade()
	 */
	@Override
	protected Class<Banco> getClasseEntidade() {
		// TODO Auto-generated method stub
		return Banco.class;
	}
	
}
