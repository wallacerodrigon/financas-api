package br.net.walltec.api.tokens;


import java.util.Date;
import java.util.Map;

import br.net.walltec.api.dto.UsuarioLogadoDTO;
import br.net.walltec.api.entidades.Perfil;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.utilitarios.Constantes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenManager {
	

	/**
	 * 
	 */
	private static final String USER_QTD_ACESSOS = "user_qtd_acessos";
	/**
	 * 
	 */
	private static final String USER_MAIL = "user_mail";
	/**
	 * 
	 */
	private static final String ROLE_NAME = "role_name";

	private static final String ROLE_ID = "role_id";

	/**
	 * 
	 */
	private static final String USER_CPF = "user_cpf";
	/**
	 * 
	 */
	private static final String USER_ID = "user_id";
	/**
	 * 
	 */
	private static final String USER_NAME = "user_name";

	public String gerarToken(Usuario usuario, int tempoToken ){
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		JwtBuilder builder = Jwts.builder()
						.setIssuedAt(new Date())
						.claim(USER_NAME, usuario.getNomeUsuario())
						.claim(USER_ID, usuario.getIdUsuario())
						.claim(ROLE_NAME, usuario.getPerfil().getNomePerfil())
						.claim(ROLE_ID, usuario.getPerfil().getIdPerfil())
						.claim(USER_MAIL, usuario.getDescemail())
						.claim(USER_QTD_ACESSOS, usuario.getQtdAcessos())
						.setNotBefore(new Date())
						.setExpiration(new Date(System.currentTimeMillis() + tempoToken))
						.signWith(signatureAlgorithm, Constantes.FRASE_SECRETA.getBytes());
		
		return builder.compact();
	}
	
	public String gerarTokenComClaims(Map<String, Object> claims,  int tempoToken ){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		JwtBuilder builder = Jwts.builder()
						.setIssuedAt(new Date())
						.setClaims(claims)
						.setNotBefore(new Date())
						.setExpiration(new Date(System.currentTimeMillis() + tempoToken))
						.signWith(signatureAlgorithm, Constantes.FRASE_SECRETA.getBytes());
		return builder.compact();
	}	

	public Boolean isTokenValido(String token){
		try {
			Claims claims = getClaims(token);
			if ( claims.getExpiration().before(new Date()) ){
				return false;
			}
			return true;
		} catch(Exception e){		
			System.out.println("Token inv�lido");
			return false;
		}
	}
	
	/**
	 * @param token
	 * @return
	 */
	public Claims getClaims(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(Constantes.FRASE_SECRETA.getBytes())
				.parseClaimsJws(token).getBody();
		return claims;
	}
	
	public UsuarioLogadoDTO getUsuarioFromToken(String token) {
		Claims claims = getClaims(token);
		UsuarioLogadoDTO dto = new UsuarioLogadoDTO();
		dto.setBolAtivo(true);
		dto.setDescemail((String)claims.get(USER_MAIL));
		dto.setIdUsuario((Integer)claims.get(USER_ID));
		dto.setNomeUsuario((String)claims.get(USER_NAME));
		dto.setNumCpf((String)claims.get(USER_CPF));
		dto.setPerfil(new Perfil());
		dto.getPerfil().setIdPerfil((Integer)claims.get(ROLE_ID));
		dto.getPerfil().setNomePerfil((String)claims.get(ROLE_NAME));
		return dto;
		
	}	
	
	
}
