package br.net.walltec.api.persistencia.dao.comum;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.net.walltec.api.comum.FiltroConsulta;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.excecoes.PersistenciaException;

@SuppressWarnings({ "unchecked", "rawtypes" })

public abstract class AbstractPersistenciaPadraoDao<T> implements PersistenciaPadraoDao<T> {

	private Class<T> classeDoObjeto;

	private CriteriaQuery<T> criteriaQueryTemp;

	@PersistenceContext
	private EntityManager em;

	public AbstractPersistenciaPadraoDao() {
		classeDoObjeto = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		// this.em = em;
	}

	@Override
	public T incluir(T objeto) throws PersistenciaException {
		em.persist(objeto);
		return objeto;
	}

	@Override
	public void alterar(T objeto) throws PersistenciaException {
		em.merge(objeto);

	}

	@Override
	public void excluir(T objeto) throws PersistenciaException {
		em.remove(objeto);
	}

	@Override
	public T find(Serializable id) throws PersistenciaException {
		return em.find(classeDoObjeto, id);
	}

	private TypedQuery<T> montarConsultaCriteria(List<FiltroConsulta> filtros, int pagina, int qtdRegistrosPagina) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		criteriaQueryTemp = (CriteriaQuery<T>) criteriaBuilder.createQuery(classeDoObjeto);
		Root<T> from = criteriaQueryTemp.from(classeDoObjeto);
		CriteriaQuery<T> select = criteriaQueryTemp.select(from);
		montarClausulasWhere(filtros, criteriaBuilder, from, select);
		return pagina != -1 ? 
				em.createQuery(criteriaQueryTemp)
				.setMaxResults(qtdRegistrosPagina)
				.setFirstResult(pagina * qtdRegistrosPagina) 
				: 
				em.createQuery(criteriaQueryTemp);
	}

	/**
	 * @param parametros
	 * @param query
	 */
	private void atribuirParametros(Map<String, Object> parametros, Query query) {
		if (parametros != null && !parametros.isEmpty()) {
			for (Map.Entry<String, Object> param : parametros.entrySet()) {
				if (param.getValue() != null) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
		}
	}

	public int contarConsultaCriteria(List<FiltroConsulta> filtros, CriteriaQuery<T> cqEntity) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
		Root<T> entityRoot = cqCount.from(cqEntity.getResultType());
		cqCount.select(builder.count(entityRoot));
		montarClausulasWhere(filtros, builder, entityRoot, cqCount);
		return em.createQuery(cqCount).getSingleResult().intValue();
	}

	private void montarClausulasWhere(List<FiltroConsulta> filtrosConsultas, CriteriaBuilder criteriaBuilder,
			Root<T> from, CriteriaQuery<?> select) {
		List<Predicate> predicados = new ArrayList<Predicate>();

		if (filtrosConsultas != null) {
			predicados = filtrosConsultas.stream().map(filtroConsulta -> montarPredicado(criteriaBuilder, from, filtroConsulta))
					.collect(Collectors.toList());

		}

		if (!predicados.isEmpty()) {
			select.where(predicados.toArray(new Predicate[predicados.size()]));
		}
	}

	/**
	 * Monta um objeto predicado do criteria builder com base nos dados do filtro
	 * 
	 * @param criteriaBuilder
	 * @param from
	 * @param filtro
	 * @return
	 */
	private Predicate montarPredicado(CriteriaBuilder criteriaBuilder, Root<T> from, FiltroConsulta filtro) {
		return criteriaBuilder.and(
				filtro.getOperador().obterPredicado(criteriaBuilder, from, filtro.getNomeCampo(), filtro.getValor()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao#pesquisar(
	 * java.util.List, br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public PageResponse<List<T>> pesquisar(List<FiltroConsulta> filtros, Pageable pageable)
			throws PersistenciaException {
		List<T> resultado = montarConsultaCriteria(filtros, pageable.getPage(), pageable.getSizePerPage())
				.getResultList();
		int qtdRegistros = contarConsultaCriteria(filtros, criteriaQueryTemp);
		return new PageResponse<List<T>>(pageable.getPage(), pageable.getSizePerPage(), qtdRegistros,
				Double.valueOf(qtdRegistros / pageable.getSizePerPage()).intValue(), new String[] {}, resultado);
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao#pesquisar(java.lang.String, br.net.walltec.api.comum.Pageable, br.net.walltec.api.persistencia.dao.comum.ParametrosBuilder)
	 */
	@Override
	public PageResponse<List<T>> pesquisar(String namedQueryName, Pageable pageable,
			ParametrosBuilder parametrosBuilder) throws PersistenciaException {
		Query query = this.em.createNamedQuery(namedQueryName);
		if (parametrosBuilder != null) {
			parametrosBuilder.construirMapaParametros().forEach( (key,value)-> {
				query.setParameter(key, value);
			});
			
		}
		List<T> resultado = query.getResultList();
		int qtdPaginas = Double.valueOf(resultado.size() / pageable.getSizePerPage()).intValue();
		
		if (resultado.size() % pageable.getSizePerPage() != 0) {
			qtdPaginas += 1;
		}
		
		return new PageResponse<List<T>>(pageable.getPage(), pageable.getSizePerPage(), 
				resultado.size(), qtdPaginas, null, resultado);
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao#listarComCache(br.net.walltec.api.comum.Pageable)
	 */
	@Override
	public List<T> listarComCache(Pageable pageable) throws PersistenciaException {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		criteriaQueryTemp = (CriteriaQuery<T>) criteriaBuilder.createQuery(classeDoObjeto);
		Root<T> from = criteriaQueryTemp.from(classeDoObjeto);
		CriteriaQuery<T> select = criteriaQueryTemp.select(from);		
		return em.createQuery(criteriaQueryTemp).setMaxResults(pageable.getSizePerPage())
				.setFirstResult(pageable.getPage() * pageable.getSizePerPage())
				.setHint("org.hibernate.cacheable", true)
				.getResultList();
	}

	
	
	
	
}
