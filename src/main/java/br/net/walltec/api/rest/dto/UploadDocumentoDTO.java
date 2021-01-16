package br.net.walltec.api.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.net.walltec.api.dto.DtoPadrao;
import lombok.Data;

@Data
public class UploadDocumentoDTO extends DtoPadrao {

	@NotNull(message="Favor informar o conteúdo do arquivo em base64")
	@Size(min = 64, message="Tamanho do conteúdo inválido")
	private String conteudoEmBase64;
	
	@NotNull(message="Favor informar o nome do arquivo")
	private String nomeArquivo;
	
	@NotNull(message="Favor informar o ID do lançamento")
	private Integer idLancamento;
}
