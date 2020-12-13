package br.net.walltec.api.negocio.servicos;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.beanutils.BeanUtils;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.negocio.servicos.comum.CrudPadraoService;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;

/**
 * Classe abstrata com os métodos padrões para os serviços
 * @author wallace
 *
 * @param <T>
 * @param <V>
 */
@Transactional(value=TxType.REQUIRED)
@SuppressWarnings({"rawtypes", "unchecked"})
@Named
@Dependent
public abstract class AbstractCrudServicePadrao<T> implements CrudPadraoService<T> {

	private PersistenciaPadraoDao<T> dao;
	
	private java.util.logging.Logger log = java.util.logging.Logger.getLogger(getClass().getName());
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Transactional(value=TxType.REQUIRES_NEW)	
	public void incluir(T objeto) throws NegocioException {
		try {
			objeto = getDao().incluir(objeto);
		} catch (PersistenciaException e) {
			throw new NegocioException(e);
		}

	}

	/**
	 * @return
	 */
	public abstract PersistenciaPadraoDao<T> getDao();

	@Override
	@Transactional(value=TxType.REQUIRES_NEW)		
	public void alterar(T objeto) throws NegocioException {
		try {
			getDao().alterar((objeto));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new NegocioException(e);
		}

	}

	@Override
	@Transactional(value=TxType.REQUIRES_NEW)		
	public void excluir(Serializable id) throws NegocioException {
		
		try {
			T objeto = getDao().find(id);
			getDao().excluir(objeto);
		} catch (PersistenciaException e) {
			throw new NegocioException(e);
		}

	}

	public Serializable getIdObjeto(T objeto) throws Exception {
		
		for(Field f : objeto.getClass().getDeclaredFields()){
			if (f.isAnnotationPresent(Id.class) || f.isAnnotationPresent(EmbeddedId.class) ){
				String retorno = BeanUtils.getProperty(objeto, f.getName());
				return f.getType() == Integer.class ? Integer.valueOf(retorno) : retorno;
			}
		}
		
		return null;
	}


	@Override
	@Transactional(value=TxType.NOT_SUPPORTED)	
	public T find(Serializable id) throws NegocioException {
        try {
            return getDao().find(id);
        } catch (PersistenciaException e) {
            throw new NegocioException(e);
        }
    }
	
	protected Logger getLogger(){
		return log;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.comum.CrudPadraoService#pesquisar(java.util.List, br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<T>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable) throws NegocioException {
		 try {
	            return getDao().pesquisar(filtros, pageable);
	        } catch (PersistenciaException e) {
	            throw new NegocioException(e);
	        }
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.negocio.servicos.comum.CrudPadraoService#listar(br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<T>> listar(Pageable pageable) throws NegocioException {
        try {
            return getDao().pesquisar(null, pageable);
        } catch (PersistenciaException e) {
            throw new NegocioException(e);
        }
	}


	
}
