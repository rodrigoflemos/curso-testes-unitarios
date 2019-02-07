package br.com.lemos.testesunitarios.suites;

import org.junit.runners.Suite.SuiteClasses;

import br.com.lemos.testesunitarios.tests.CalculadoraTest;
import br.com.lemos.testesunitarios.tests.CalculoValorLocacaoTest;
import br.com.lemos.testesunitarios.tests.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})

public class SuiteExecucao {
	// remova se puder
}
