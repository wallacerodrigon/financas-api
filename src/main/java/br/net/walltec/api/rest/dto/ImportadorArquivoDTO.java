package br.net.walltec.api.rest.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImportadorArquivoDTO implements Serializable {

	private Integer mes;
	
	private Integer ano;
	
	private Integer numBanco;
	
	private String dadosArquivoBase64;
	
	private String extensaoArquivo;
}
