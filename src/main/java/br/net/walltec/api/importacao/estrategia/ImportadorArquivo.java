package br.net.walltec.api.importacao.estrategia;

import java.util.List;

import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;

public interface ImportadorArquivo {

    List<Lancamento> importar(String nomeArquivo, byte[] dadosArquivo) throws NegocioException;
    
    boolean isExtensaoValida(String extensao);
}
