package br.net.walltec.api.persistencia.dao.comum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParametrosBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeParametro;
	private Object valorParametro;
	private List<ParametrosBuilder> parametros = new ArrayList<>();

	public ParametrosBuilder() {}
	
	/**
	 * @param nomeParametro
	 * @param valorParametro
	 */
	private ParametrosBuilder(String nomeParametro, Object valorParametro) {
		super();
		this.nomeParametro = nomeParametro;
		this.valorParametro = valorParametro;
	}



	public ParametrosBuilder addParametro(String nome, Object valor) {
		ParametrosBuilder parametrosBuilder = new ParametrosBuilder(nome, valor == null ? "" : valor);
		parametros.add(parametrosBuilder);
		return this;
	}
	
	public Map<String, Object> construirMapaParametros() {
		return parametros.stream()
					.collect(Collectors.toMap(ParametrosBuilder::getNomeParametro, ParametrosBuilder::getValorParametro));
	}

	protected String getNomeParametro() {
		return nomeParametro;
	}

	protected void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	protected Object getValorParametro() {
		return valorParametro;
	}

	protected void setValorParametro(Object valorParametro) {
		this.valorParametro = valorParametro;
	}

}
