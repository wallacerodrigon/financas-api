/**
 * 
 */
package br.net.walltec.api.negocio.servicos;


import java.util.List;

import br.net.walltec.api.entidades.DeparaHistoricoBanco;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;

/**
 * @author Wallace
 *
 */
public interface DeparaHistoricoBancoService extends CrudPadraoService<DeparaHistoricoBanco> {
	
	List<DeparaHistoricoBanco> listaPorBanco(Integer numBanco) throws NegocioException;
}
