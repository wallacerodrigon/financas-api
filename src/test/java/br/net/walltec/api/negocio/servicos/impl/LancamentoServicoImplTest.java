package br.net.walltec.api.negocio.servicos.impl;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import br.net.walltec.api.dto.DivisaoLancamentoDTO;
import br.net.walltec.api.entidades.Lancamento;
import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.excecoes.RegistroNaoEncontradoException;
import br.net.walltec.api.persistencia.dao.LancamentoDao;
import br.net.walltec.api.utilitarios.UtilData;

public class LancamentoServicoImplTest {

	private LancamentoServicoImpl servico = new LancamentoServicoImpl();

	@Mock
	private LancamentoDao daoMock;

	@Before
	public void init() {
		daoMock = Mockito.mock(LancamentoDao.class);

		servico.setLancamentoDao(daoMock);
	}

	@Test
	public void testaDivisaoLancamentoSemParametro() throws Exception {
		try {
			servico.dividirLancamento(null);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Informe os dados"));
		}

	}

	@Test
	public void testaDivisaoLancamentoSemNenhumAtributoInformado() throws Exception {
		try {
			servico.dividirLancamento(new DivisaoLancamentoDTO());
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Informe o lançamento de origem"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComApenasLancamentoOrigem() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Informe a data do evento no formato YYYY-MM-DD"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComLancamentoEDataEventoInvalida() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("01-01-2000");
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Formato de data inválido"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComLancamentoEDataEvento() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Informe o valor do uso"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComLancamentoEDataEventoEValor() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setValor(new BigDecimal(10.0));
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Informe uma descriçao para o lançamento"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComDescricaoSemOMinimoCaracteres() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("X");
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("A descrição deve conter de"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComDescricaoSuperandoOMaximoCaracteres() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("Xlllllllll4444444444999999999999999"); // 35
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("A descrição deve conter de"));
		}

	}

	@Test
	public void testaDivisaoLancamentoSemFormapagamento() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("Xlllllllll4444444");
			servico.dividirLancamento(dto);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Informe o ID da forma de pagamento"));
		}
	}

	@Test
	public void testaDivisaoLancamentoComDadosValidos() throws Exception {
		DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
		dto.setIdLancamentoOrigem(1);
		dto.setDataEventoIso("2000-01-01");
		dto.setDescricao("Xlllllllll4444444");
		dto.setIdFormaPagamento(34);
		dto.setValor(new BigDecimal(10.0));

		Lancamento lancamento = new Lancamento();
		lancamento.setValorLancamento(new BigDecimal(20.0));
		lancamento.setDataVencimento(UtilData.createDataSemHoras(1, 1, 2000));

		Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(lancamento);

		servico.dividirLancamento(dto);
	}

	@Test
	public void testaDivisaoLancamentoInexistente() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(0);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("Xlllllllll4444444");
			dto.setIdFormaPagamento(34);
			dto.setValor(new BigDecimal(10.0));
			Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(null);

			servico.dividirLancamento(dto);
		} catch (RegistroNaoEncontradoException e) {
			assertTrue(e.getMessage().contains("Lançamento não encontrado com esse id"));
		}
	}

	@Test
	public void testaDivisaoLancamentoComValorSuperiorAOrigem() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("Xlllllllll4444444");
			dto.setIdFormaPagamento(34);
			dto.setValor(new BigDecimal(20.0));

			Lancamento lancamento = new Lancamento();
			lancamento.setValorLancamento(new BigDecimal(15.0));

			Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(lancamento);

			servico.dividirLancamento(dto);
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Valor do evento deve ser menor ou igual ao valor do lançamento"));
		}

	}

	@Test
	public void testaDivisaoLancamentoComValorIgualAOrigem() throws Exception {
		DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
		dto.setIdLancamentoOrigem(1);
		dto.setDataEventoIso("2000-01-01");
		dto.setDescricao("Xlllllllll4444444");
		dto.setIdFormaPagamento(34);
		dto.setValor(new BigDecimal(20.0));

		Lancamento lancamento = new Lancamento();
		lancamento.setValorLancamento(new BigDecimal(20.0));
		lancamento.setDataVencimento(UtilData.createDataSemHoras(1, 1, 2000));

		Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(lancamento);

		servico.dividirLancamento(dto);
		assertTrue(true);

	}
	
	@Test
	public void testaDivisaoLancamentoComLancamentoPago() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2000-01-01");
			dto.setDescricao("Xlllllllll4444444");
			dto.setIdFormaPagamento(34);
			dto.setValor(new BigDecimal(20.0));

			Lancamento lancamento = new Lancamento();
			lancamento.setValorLancamento(new BigDecimal(20.0));
			lancamento.setDataHoraPagamento(new Date());

			Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(lancamento);

			servico.dividirLancamento(dto);
		} catch(NegocioException e) {
			assertTrue(e.getMessage().contains("Lançamento pago"));

		}

	}
	
	@Test
	public void testaDivisaoLancamentoComDataMesDiferenteDoLancamento() throws Exception {
		try {
			DivisaoLancamentoDTO dto = new DivisaoLancamentoDTO();
			dto.setIdLancamentoOrigem(1);
			dto.setDataEventoIso("2020-12-01");
			dto.setDescricao("Xlllllllll4444444");
			dto.setIdFormaPagamento(34);
			dto.setValor(new BigDecimal(10.0));

			Lancamento lancamento = new Lancamento();
			lancamento.setValorLancamento(new BigDecimal(20.0));
			lancamento.setDataVencimento(new Date());

			Mockito.when(daoMock.find(Mockito.anyInt())).thenReturn(lancamento);
			
			servico.dividirLancamento(dto);
			
			
			
		} catch(NegocioException e) {
			assertTrue(e.getMessage().contains("Evento com data diferente do mês do lançamento"));

		}

	}

}
