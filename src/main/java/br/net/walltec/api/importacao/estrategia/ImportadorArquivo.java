package br.net.walltec.api.importacao.estrategia;

import java.util.List;

import br.net.walltec.api.dto.RegistroExtratoDto;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;

public interface ImportadorArquivo {

    List<RegistroExtratoDto> importar(String nomeArquivo, byte[] dadosArquivo, List<Lancamento> listaParcelas) throws NegocioException;
    
    boolean isExtensaoValida(String extensao);
}
