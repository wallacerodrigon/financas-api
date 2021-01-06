package br.net.walltec.api.utilitarios;

import java.util.Base64;

public class UtilBase64 {

	public static String codificarBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	public static byte[] decodificarBase64(String conteudo) {
		return Base64.getDecoder().decode(conteudo);
	}
}
