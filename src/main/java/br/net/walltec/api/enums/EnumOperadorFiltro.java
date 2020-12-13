/**
 * 
 */
package br.net.walltec.api.enums;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author wallace
 *
 */
public enum EnumOperadorFiltro {

	//private String operadorPredicado;
	//terá um método para retornar o enum pelo operador do predicado
	EQ {
		/* (non-Javadoc)
		 * @see br.net.walltec.api.enums.EnumOperador#obterPredicado(javax.persistence.criteria.CriteriaBuilder, java.lang.String, java.lang.Object)
		 */
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			return builder. equal(root.get(campo), valor);
		}
	},

	LIKE {
		/* (non-Javadoc)
		 * @see br.net.walltec.api.enums.EnumOperador#obterPredicado(javax.persistence.criteria.CriteriaBuilder, java.lang.String, java.lang.Object)
		 */
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			String valorComPercents = ((String)valor).replaceAll("[*]", "%");
			return builder.like(root.get(campo), valorComPercents);
		}
	},
	
	
	LT {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			if (valor instanceof Number) {
				return builder.lt(root.get(campo), (Integer)valor);
			}
			return null;
		}
	},
	
	GT {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			if (valor instanceof Number) {
				return builder.gt(root.get(campo), (Integer)valor);
			}
			return null;
		}

	},
	
	LE {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			if (valor instanceof Number) {
				return builder.le(root.get(campo), (Integer)valor);
			}
			return null;
		}

	},
	
	GE {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			if (valor instanceof Number) {
				return builder.ge(root.get(campo), (Integer)valor);
			}
			return null;
		}

	},
	
	IN {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			In<String> inClause = builder.in(root.get(campo));
			List<String> listaCriterios = (List<String>)valor;
			listaCriterios.forEach(criterio -> inClause.value(criterio));
			return inClause;
		}
	},
	
	NOT {
		@Override
		public Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor) {
			return builder.not((Expression<Boolean>)valor);
		}

	};
	
	public abstract Predicate obterPredicado(CriteriaBuilder builder, Root<?> root, String campo, Object valor);
	
	
}

