/**
 * 
 */
package br.net.walltec.api.rest.dto.filtro;

import java.util.Date;

/**
 * @author tr301222
 *
 */
public class RegistroFechamentoMesDTO {

	private Integer mes;
	private Integer ano;
	private Date data;
	/**
	 * @return the mes
	 */
	public Integer getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	/**
	 * @return the ano
	 */
	public Integer getAno() {
		return ano;
	}
	/**
	 * @param ano the ano to set
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
