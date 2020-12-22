/**
 * 
 */
package br.net.walltec.api.negocio.servicos;


import br.net.walltec.api.entidades.FechamentoContabil;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;

/**
 * @author Wallace
 *
 */
public interface FechamentoContabilService extends CrudPadraoService<FechamentoContabil> {
	
	FechamentoContabil obterPorMesAno(Integer ano, Integer mes) throws NegocioException;
	
}
