package br.rj.lssdeveloper.servicos;
import java.util.Date;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;
import br.rj.lssdeveloper.servicos.LocacaoService;
import br.rj.lssdeveloper.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void testeLocacao() {
		
		//cenario
		LocacaoService servico = new LocacaoService();
		Usuario usuario = new Usuario("Roberval");
		Filme filme = new Filme("O cara de pau.", 2, 15.00);
		
		//ação
		Locacao locacao;
		try {
			locacao = servico.alugarFilme(usuario, filme);

		
		//verificação
		//No caso abaixo apesar de funcionar por melhor prática usar o assertEquals
		assertTrue(locacao.getValor()==15.0);
		//usando o assertEquals (melhor prática)
		assertEquals(locacao.getValor(), 15.0, 0.01);
		//atenção para ordem dos teste apesasr de funcionar gera uma confusão
		//segue a ordem correta (sempre o que passei será esperado como valor 15.00) 
		//but was será o método de execução (locacao.getValor())!
		
		//Segue a melhor prática final então!
		assertEquals(15.0, locacao.getValor(),0.01);
		assertTrue(DataUtils.isMesmaData(
				locacao.getDataLocacao(), new Date()));
		
		assertTrue(DataUtils.isMesmaData(
				locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));	
		
		//usando o assertThat
			// note que os valosres se invertem comparado com assertEquals
		//dando um sentido a frase como : Verifique se o valor da locação é 15.
		assertThat(locacao.getValor(), is(15.0));
		assertThat(locacao.getValor(), is(equalTo(15.0)));
		//se fosse uma negação
		assertThat(locacao.getValor(), is(not(16.0)));

		assertThat(DataUtils.isMesmaData(
				locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(
				locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));	
		
		//Alguns autores mais radicais sugerem que o teste seja isolado
		//ou seja no caso acima seria interessante 3 métodos isolados:
		// um @Before para o cenário respectivo teste isolado para:
		// - que verificasse o valor
		// - que verificasse a data da locação
		// - que verificasse a data de retorno
		
		// mas realilzar o teste como está acima é recomendado utilizar @Rule
		
		//ao invés de usar o assertThat
		error.checkThat(locacao.getValor(), is(not(16.0)));

		error.checkThat(DataUtils.isMesmaData(
				locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(
				locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		//caso ocorra erros  SINTAXE ACIMA retornaria cada Failures e exibiria no Failure Trace
		//como na simulação abaixo
		
			//error.checkThat(locacao.getValor(), is(16.0));

			//error.checkThat(DataUtils.isMesmaData(
						//locacao.getDataLocacao(), new Date()), is(true));
			//error.checkThat(DataUtils.isMesmaData(
						//locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(false));
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}


}
