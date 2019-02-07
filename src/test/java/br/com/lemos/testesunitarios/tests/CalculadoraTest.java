package br.com.lemos.testesunitarios.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.lemos.testesunitarios.exceptions.DivisaoPorZeroException;
import br.com.lemos.testesunitarios.servicos.Calculadora;

public class CalculadoraTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		
		int a = 8;
		int b = 4;
		
		int resultado = calc.somar(a,b);
		
		assertEquals(12, resultado);
		//assertThat(resultado, is(12));
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		
		int a = 8;
		int b = 4;
		
		int resultado = calc.subtrair(a,b);
		assertThat(resultado, is(4));
	}
	
	@Test
	public void deveMultiplicarDoisValores() {
		
		int a = 8;
		int b = 4;
		
		int resultado = calc.multiplicar(a,b);
		assertThat(resultado, is(32));
	}

	@Test
	public void deveDividirDoisValores() throws DivisaoPorZeroException {
		
		int a = 8;
		int b = 4;
		
		int resultado = calc.dividir(a,b);
		assertThat(resultado, is(2));
	}
	
//	@Test
//	public void deveLancarExcecaoAoDividirPorZero() throws DivisaoPorZeroException {
//		
//		
//		Calculadora cal = new Calculadora();
//		int a = 8;
//		int b = 0;
//		
//		exception.expect(DivisaoPorZeroException.class);
//		exception.expectMessage("Não pode dividir por zero");
//		cal.dividir(a,b);
//	}
	
	@Test(expected=DivisaoPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws DivisaoPorZeroException {
		
		int a = 8;
		int b = 0;
		calc.dividir(a,b);
	}
}
