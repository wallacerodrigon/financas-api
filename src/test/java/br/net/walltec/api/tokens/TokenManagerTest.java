/**
 * 
 */
package br.net.walltec.api.tokens;

import org.junit.Assert;
import org.junit.Test;

import br.net.walltec.api.dto.UsuarioLogadoDTO;
import br.net.walltec.api.entidades.Perfil;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.negocio.servicos.impl.UsuarioServicoImpl;
import br.net.walltec.api.utilitarios.Constantes;

/**
 * @author wallace
 *
 */
public class TokenManagerTest {

	@Test
	public void deveRetornarTokenInvalido() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1OTgyMTcyOTcsInVzZXJfbmFtZSI6IndhbGxhY2Ugc291c2EiLCJ1c2VyX2lkIjoxLCJ1c2VyX2NwZiI6IjcwNTg5MzgwMTY4Iiwicm9sZV9uYW1lIjoic3RyaW5nIiwicm9sZV9pZCI6MSwidXNlcl9tYWlsIjoid2FsbGFjZXJvZHJpZ29uQGdtYWlsLmNvbSIsInVzZXJfcXRkX2FjZXNzb3MiOjAsIm5iZiI6MTU5ODIxNzI5NywiZXhwIjoxNTk4MjE3MzU3fQ.v4MA2pMfI2CoQR9mVdw3-FgG4nZMWZPGeGBhzyO96NL4E1AyfL_YLxJRcebZKEDaio7q4y8qKBZfejnst4rKKA";
		Assert.assertTrue(new TokenManager().isTokenValido(token) == false);
	}
	
	@Test
	public void deveGerarTokenComSucesso() {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(100);
		usuario.setDescemail("teste@teste.com");
		usuario.setPerfil(new Perfil());
		usuario.getPerfil().setIdPerfil(1);
		new TokenManager().gerarToken(usuario, Constantes.INTERVALO_TOKEN);
	}
	
	@Test
	public void deveRetornarDadosDoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(100);
		usuario.setDescemail("teste@teste.com");
		usuario.setPerfil(new Perfil());
		usuario.getPerfil().setIdPerfil(1);
		String tokenGerado = new TokenManager().gerarToken(usuario, Constantes.INTERVALO_TOKEN);
		
		UsuarioLogadoDTO usuarioToken = new TokenManager().getUsuarioFromToken(tokenGerado);
		
		Assert.assertNotNull(usuarioToken);
		Assert.assertEquals(usuario.getIdUsuario(), usuarioToken.getIdUsuario());
		Assert.assertEquals(usuario.getDescemail(), usuarioToken.getDescemail());
		Assert.assertEquals(usuario.getPerfil(), usuarioToken.getPerfil());
	}
	
	

}
