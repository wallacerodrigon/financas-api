package br.net.walltec.api.persistencia.dao;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import br.net.walltec.api.entidades.Usuario;
import br.net.walltec.api.excecoes.PersistenciaException;
import br.net.walltec.api.persistencia.dao.comum.PersistenciaPadraoDao;


@MappedSuperclass
@NamedQueries({
	@NamedQuery(name = "Usuario.porLoginSenha", query =
			"from Usuario where descemail = :email and descSenha = :senha order by nomeUsuario")
})
public interface UsuarioDao extends PersistenciaPadraoDao<Usuario> {

	Usuario recuperarUsuario(String email, String senha) throws PersistenciaException;
	
	Usuario recuperarUsuarioPorEmail(String email) throws PersistenciaException;

}
