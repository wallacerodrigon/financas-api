package br.net.walltec.api.rest.filters;



import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * @author wallace
 *
 */
@Provider
public class CorsResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia,Authorization,refreshtoken");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		
		String serverName = requestContext.getHeaderString("Origin");
		serverName = serverName == null ? requestContext.getHeaderString("Host") : serverName;
		
		if (serverName.contains("walltec.dev")) {
			responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "https://walltec.dev.br");
		} else {
			responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "http://localhost:4200");
		}
		responseContext.getHeaders().putSingle("Content-Security-Policy","script-src 'self';img-src 'self';style-src 'self';base-uri 'self'");
		
		System.out.println("headers: " + responseContext.getHeaders().toString());
		
		
	}

}
