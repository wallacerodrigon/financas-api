package br.net.walltec.api.persistencia.dao.impl;


import java.util.List;

import javax.inject.Named;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import br.net.walltec.api.comum.EmptyPageable;
import br.net.walltec.api.comum.PageRequest;
import br.net.walltec.api.comum.PageResponse;
import br.net.walltec.api.comum.Pageable;
import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.UsuarioDao;
import br.net.walltec.api.persistencia.dao.comum.AbstractPersistenciaPadraoDao;
import br.net.walltec.api.persistencia.dao.comum.ParametrosBuilder;

@Named

public class UsuarioDaoImpl  extends AbstractPersistenciaPadraoDao<Usuario> implements UsuarioDao {

	
	public Usuario recuperarUsuario(String email, String senha) throws PersistenciaException {
		PageResponse<List<Usuario>> pesquisa = this.pesquisar("Usuario.porLoginSenha", EmptyPageable.getInstance(), 
				new ParametrosBuilder()
				.addParametro("email", email)
				.addParametro("senha", senha)
				);
		if (pesquisa.isEmpty()) {
			throw new IllegalArgumentException("Usuário ou senha inválidos");
		}

		if (pesquisa.getQtdRegistros() > 1) {
			throw new IllegalArgumentException("Usuário ou senha inválidos");
		}
		
		return pesquisa.getResultado().get(0);
	}

	@Override
	public Usuario recuperarUsuarioPorEmail(String email) throws PersistenciaException {
		ParametrosBuilder parametros = new ParametrosBuilder()
				.addParametro("email", email.toLowerCase());
		
		
		StringBuilder builder = new StringBuilder("from Usuario where descemail = :email ");
		Pageable pageable = new PageRequest(0, 99999);
    	PageResponse<List<Usuario>> pesquisa = this.pesquisar(builder, pageable, parametros);
	
		
		if (pesquisa.isEmpty()) {
			throw new IllegalArgumentException("Usuário inexistente com esse e-mail");
		}

		if (pesquisa.getQtdRegistros() > 1) {
			throw new IllegalArgumentException("Existem vários usuários com essa conta social");
		}
		
		return pesquisa.getResultado().get(0);
	}

	
}
