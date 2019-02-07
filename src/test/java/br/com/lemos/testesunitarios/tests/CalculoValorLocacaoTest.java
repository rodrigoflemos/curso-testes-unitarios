package br.com.lemos.testesunitarios.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.com.lemos.testesunitarios.entidades.Filme;
import br.com.lemos.testesunitarios.entidades.Locacao;
import br.com.lemos.testesunitarios.entidades.Usuario;
import br.com.lemos.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.lemos.testesunitarios.exceptions.LocadoraException;
import br.com.lemos.testesunitarios.servicos.LocacaoService;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	private LocacaoService service;
	
	@Before
	public void setUp() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1",3, 4.0);
	private static Filme filme2 = new Filme("Filme 2",3, 4.0);
	private static Filme filme3 = new Filme("Filme 3",3, 4.0);
	private static Filme filme4 = new Filme("Filme 4",3, 4.0);
	private static Filme filme5 = new Filme("Filme 5",3, 4.0);
	private static Filme filme6 = new Filme("Filme 6",3, 4.0);
	private static Filme filme7 = new Filme("Filme 7",3, 4.0);
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Setimo filme não tem desconto"}
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDesconto() throws FilmeSemEstoqueException, LocadoraException {
		
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
	
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4 + 4 + 3 + 2 + 1 = 14
		assertThat(resultado.getValor(), is(valorLocacao));
	}
}
