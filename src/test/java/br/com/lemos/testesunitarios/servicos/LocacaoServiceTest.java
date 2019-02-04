package br.com.lemos.testesunitarios.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.lemos.testesunitarios.entidades.Filme;
import br.com.lemos.testesunitarios.entidades.Locacao;
import br.com.lemos.testesunitarios.entidades.Usuario;
import br.com.lemos.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.lemos.testesunitarios.exceptions.LocadoraException;
import br.com.lemos.testesunitarios.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		service = new LocacaoService();
	}
	
	@Test
	public void testLocacao() throws Exception {
		
		//cenario
		
		Usuario usuario = new Usuario("Rodrigo");
		Filme filme = new Filme("Filme 1", 2, 5.00);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat((DataUtils.isMesmaData(locacao.getDataLocacao(), new Date())), is(true));
		error.checkThat((DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1))),is(true));
	}
	
	/*
	 * Metodo robusto para tratar excecao
	 */
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		//cenario
		Filme filme = new Filme("Filme 1", 2, 5.00);	
		try {
			service.alugarFilme(null, filme);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	/*
	 * Metodo novo para tratar excecao
	 */
	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		//cenario
		Usuario usuario = new Usuario("Rodrigo");
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		service.alugarFilme(usuario, null);
		
	}
	
	/*
	 * Metodo elegante para tratar excecao (Funciona quando voce precisa apenas da excecao
	 */
	@Test(expected=FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rodrigo");
		Filme filme = new Filme("Filme 1", 0, 5.00);
		
		//acao
		service.alugarFilme(usuario, filme);
	}
	
//	/*
//	 * Metodo robusto para tratar excecao
//	 */
//	@Test
//	public void testeLocacao_filmeSemEstoque_2(){
//		
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Rodrigo");
//		Filme filme = new Filme("Filme 1",0, 5.00);
//		
//		//acao
//		try {
//			service.alugarFilme(usuario, filme);
//			fail("Deveria ter lançado uma exceção");
//		} catch (Exception e) {
//		assertThat(e.getMessage(), is("Filme sem estoque"));
//		}
//	}
//	
//	/*
//	 * Metodo NOVO para tratar excecao
//	 */
//	@Test
//	public void testeLocacao_filmeSemEstoque_3() throws Exception {
//		
//		//cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Rodrigo");
//		Filme filme = new Filme("Filme 1",0, 5.00);
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque");
//		
//		//acao
//		service.alugarFilme(usuario, filme);
//	}
}
