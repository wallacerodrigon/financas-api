/**
 * 
 */
package br.net.walltec.api.comum;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.net.walltec.api.entidades.Banco;
import br.net.walltec.api.enums.EnumOperadorFiltro;


/**
 * @author wallace
 *
 */
public class FiltroConsultaTest {

	private FiltroConsulta filtroConsulta = new FiltroConsulta();

	@Test(expected = IllegalArgumentException.class)
	public void testaFiltroNulo() {
		filtroConsulta.efetuarParse(null, Banco.class);
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaFiltroVazio() {
		filtroConsulta.efetuarParse("", Banco.class);
	}
	
	@Test
	public void testaFiltroComCampoInvalido() {
		String json = "{\"idBanco\":{}}";
		try {
			filtroConsulta.efetuarParse(json, Banco.class);
		} catch(IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("inválido para esse filtro"));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testaFiltroComOperadorInvalido() {
		String json = "{\"numBanco\":{\"igual\":10}}";
		filtroConsulta.efetuarParse(json, Banco.class);
	}
	
	@Test
	public void testaFiltroComUmCriterio() {
		String json = "{\"numBanco\":{\"eq\":\"10\"}}";
		List<FiltroConsulta> filtros = filtroConsulta.efetuarParse(json, Banco.class);
		FiltroConsulta filtro = filtros.get(0);
		Assert.assertEquals("numBanco", filtro.getNomeCampo());
		Assert.assertEquals(EnumOperadorFiltro.EQ, filtro.getOperador());
		Assert.assertEquals("10", filtro.getValor());
	}
	
	@Test
	public void testaFiltroComVariosCriterios() {
		String json = "{\"numBanco\":{\"eq\":\"10\"}, \"nomeBanco\":{\"like\":\"brb*\"} }";
		List<FiltroConsulta> filtros = filtroConsulta.efetuarParse(json, Banco.class);
		FiltroConsulta filtro = filtros.get(0);
		Assert.assertEquals("numBanco", filtro.getNomeCampo());
		Assert.assertEquals(EnumOperadorFiltro.EQ, filtro.getOperador());
		Assert.assertEquals("10", filtro.getValor());

		FiltroConsulta filtro1 = filtros.get(1);
		Assert.assertEquals("nomeBanco", filtro1.getNomeCampo());
		Assert.assertEquals(EnumOperadorFiltro.LIKE, filtro1.getOperador());
		Assert.assertEquals("brb*", filtro1.getValor());
	
	}
	

	@Test
	public void testaFiltroComClausulaIN() {
		String json = "{\"numBanco\":{\"in\":[\"10,20,30\"]}}";
		List<FiltroConsulta> filtros = filtroConsulta.efetuarParse(json, Banco.class);
		FiltroConsulta filtro = filtros.get(0);
		Assert.assertEquals("numBanco", filtro.getNomeCampo());
		Assert.assertEquals(EnumOperadorFiltro.IN, filtro.getOperador());
		Assert.assertEquals("[10,20,30]", filtro.getValor());
	
	}	
	

}
//TODO: tratar filtros com período e in
//
///processos?filter={"classe.codigo":{"in": [20,30,40]}}

///processos?filter={"data-da-distribuicao":[{"ge": "2015-01-01"}, {"le": "2015-12-31"}]}
//
