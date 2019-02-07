package br.com.lemos.testesunitarios.tests;

import static br.com.lemos.testesunitarios.matchers.MyMatchers.caiNumaSegunda;
import static br.com.lemos.testesunitarios.matchers.MyMatchers.ehHoje;
import static br.com.lemos.testesunitarios.matchers.MyMatchers.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.lemos.testesunitarios.entidades.Filme;
import br.com.lemos.testesunitarios.entidades.Locacao;
import br.com.lemos.testesunitarios.entidades.Usuario;
import br.com.lemos.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.lemos.testesunitarios.exceptions.LocadoraException;
import br.com.lemos.testesunitarios.servicos.LocacaoService;
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
	public void deveAlugarFilme() throws Exception {
		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//cenario
		
		Usuario usuario = new Usuario("Rodrigo");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.00));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));

	}
	
	/*
	 * Metodo robusto para tratar excecao
	 */
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		//cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.00));
		
		try {
			service.alugarFilme(null, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	/*
	 * Metodo novo para tratar excecao
	 */
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
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
	public void NaoDeveAlugarFilmeSemEstoque() throws Exception {
		
		//cenario
		Usuario usuario = new Usuario("Rodrigo");
		Filme filme1 = new Filme("Filme 1", 2, 5.00);
		Filme filme2 = new Filme("Filme 2", 0, 9.00);
		
		List<Filme> filmes = Arrays.asList(filme1,filme2);
		
		//acao
		service.alugarFilme(usuario, filmes);
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
	
	@Test
	public void deveDevolverNaSegundaSeAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1",3, 4.0), new Filme("Filme 2",2, 4.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
}