package br.net.walltec.api.negocio.servicos.impl;

import javax.inject.Inject;

import org.junit.Test;

import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.negocio.servicos.LancamentoService;

public class LancamentoServicoImplTest {

	@Inject
	private LancamentoService servico;
	
	
	@Test
	public void testaDivisaoLancamento() throws Exception {
		DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
		
		servico.dividirLancamento(dto);
		
	}

}
