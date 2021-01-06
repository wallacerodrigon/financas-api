package br.net.walltec.api.rest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ImportadorArquivoDTO implements Serializable {

	@NotNull(message = "Informe o mês")
	private Integer mes;
	
	@NotNull(message = "Informe o ano")
	private Integer ano;

	@NotNull(message = "Informe o banco")
	private Integer numBanco;

	@NotNull(message = "Informe o conteúdo do arquivo")
	private String dadosArquivoBase64;

	@NotNull(message = "Informe a extensão do arquivo")
	private String extensaoArquivo;
	
	@NotNull(message = "Informe o nome do arquivo")
	private String nomeArquivo;
}
