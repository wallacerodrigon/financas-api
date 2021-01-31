/**
 * 
 */
package br.net.walltec.api.comum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import br.net.walltec.api.enums.EnumOperadorFiltro;
import lombok.Data;

/**
 * @author wallace
 *
 */
@Data
public class FiltroConsulta {

	
	private String nomeCampo;
	
	private EnumOperadorFiltro operador;
	
	private Object valor;

	public List<FiltroConsulta> efetuarParse(String json, Class<?> classeDoFiltro) {
		if (StringUtils.isBlank(json)) {
			throw new IllegalArgumentException("Filtro não informado");
		}
		if (classeDoFiltro == null) {
			throw new IllegalArgumentException("Classe do Filtro não informada");
		}
		
		Gson gson = new Gson();
		Map<String, Object> mapa = gson.fromJson(json, Map.class);
		List<FiltroConsulta> listaFiltros = new ArrayList<FiltroConsulta>();
		
		try {
			for(Entry<String, Object> entries : mapa.entrySet()) {
				
				Stream.of(classeDoFiltro.getDeclaredFields())
					.filter(field -> field.getName().equalsIgnoreCase(entries.getKey()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Campo " + entries.getKey() + " inválido para esse filtro"));
				
				if (entries.getValue() != null) {
					String valor = entries.getValue().toString().replaceAll("[{|}]", "");
					String[] dados = valor.split("=");
					
					FiltroConsulta filtro = new FiltroConsulta();
					filtro.setNomeCampo(entries.getKey());
					filtro.setOperador(EnumOperadorFiltro.valueOf(dados[0].toUpperCase()));
					filtro.setValor(dados[1]);
					listaFiltros.add(filtro);
				}
				
			}
			return listaFiltros;
		} catch(IllegalArgumentException e) {
			throw e;
		}
		
	}
	
}
//TODO: fazer esses tratamentos abaixo
//
///processos?filter={"classe.codigo":{"in": [20,30,40]}}
//
///processos?filter={"data-da-distribuicao":[{"ge": "2015-01-01"}, {"le": "2015-12-31"}]}
