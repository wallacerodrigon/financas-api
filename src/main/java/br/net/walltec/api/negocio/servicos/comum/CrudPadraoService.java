package br.net.walltec.api.negocio.servicos.comum;


import java.io.Serializable;
import java.util.List;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.excecoes.NegocioException;


 /**
  * Interface padrao para os cruds do sistema
 * @author wallace
 *
 * @param <T>
 * @param <V>
 */
public interface CrudPadraoService<T> {

	 void incluir(T objeto) throws NegocioException;
	 
	 void alterar(T objeto) throws NegocioException;
	 
	 void excluir(Serializable id) throws NegocioException;
	 
	 T find(Serializable id) throws NegocioException;

	 PageResponse<List<T>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable) throws NegocioException;
	 
	 PageResponse<List<T>> listar(Pageable pageable) throws NegocioException;
	 
	 Serializable getIdObjeto(T objeto) throws Exception;
}
