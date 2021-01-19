package br.net.walltec.api.persistencia.dao.comum;



import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.excecoes.PersistenciaException;


/**
 * @author wallace
 *
 * @param <T>
 */
public interface PersistenciaPadraoDao<T>  {

	 T incluir(T objeto) throws PersistenciaException;

	 void alterar(T objeto) throws PersistenciaException;
	 
	 void excluir(T objeto) throws PersistenciaException;
	 
	 T find(Serializable id) throws PersistenciaException;
	 
	 PageResponse<List<T>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable) throws PersistenciaException;	
	 
	 int contarConsultaCriteria(List<FiltroConsulta> filtros, CriteriaQuery<T> cqEntity);
				 
	 PageResponse<List<T>> pesquisar(String namedQueryName, Pageable pageable, ParametrosBuilder parametrosBuilder) throws PersistenciaException;	
	 
	 List<T> listarComCache(Pageable pageable) throws PersistenciaException;

	 PageResponse<List<T>> pesquisar(StringBuilder hql, Pageable pageable,
				ParametrosBuilder parametrosBuilder) throws PersistenciaException;
}
