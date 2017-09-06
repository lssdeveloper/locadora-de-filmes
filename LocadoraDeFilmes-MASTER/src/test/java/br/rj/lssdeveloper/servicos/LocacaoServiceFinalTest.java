package br.rj.lssdeveloper.servicos;

import static br.rj.lssdeveloper.utils.DataUtils.isMesmaData;
import static br.rj.lssdeveloper.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;;

public class LocacaoServiceFinalTest {
	// Resultado final sem os comentários da LocacaoServiceTest
	
	private LocacaoService service;
	private Usuario usuario;
	private Filme filme;
	private Locacao locacao;

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception =ExpectedException.none();
	
	@Before
	public void setUp(){
		// cenário
		service = new LocacaoService();
		usuario = new Usuario("Roberval");
	}
	@After
	public void tearDown(){
		service = null;
		usuario = null;
		filme = null;
	}

	@Test
	public void testLocacao() throws Exception {
		
		filme = new Filme("O cara de pau.", 2, 15.00);

		// ação
		locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(15.00)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		
		/*============Diferença entre falha e erro===============
		 *Falhas ocorrem quando o teste é executado sem problemas, 
		 *porém alguma condição que era esperada não foi atendida. 
		 *No caso, representada pela forma de assertivas.
		 *
		 *Erro, quando algum problema durante a exeução do teste,
		 *impede que o mesmo seja concluído!
		 *Ou seja, quando ocorre alguma exceçao não esperada
		 *ou não tratada.
		 */

	}
	/*
	 * 1ª Forma ELEGANTE, pois é esperada uma exceção.
	 * Deixo o JUnit controlar.
	 */
	@Test(expected = Exception.class) 
	public void testLocacao_filmeSemEstouqe_1() throws Exception{
		filme = new Filme("O cara de pau.", 0, 15.00);
		service.alugarFilme(usuario, filme);
	}
	/*
	 * 2ª Forma ROBUSTA, oferece um maior controle e percepção,
	 * esta é a diferença entre a elegante acima.
	 * Neste caso, eu quem controlo o tratamento de exceção.
	 * Através do try / catch
	 */
	@Test 
	public void testLocacao_filmeSemEstouqe_2(){
		filme = new Filme("O cara de pau.", 0, 15.00);
		try {
			service.alugarFilme(usuario, filme);
			//fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			// Além de capturar a exceção posso capturar a mensagem.
			assertThat(e.getMessage(), is("Filme sem estoque!"));
		}
	}
	/*
	 * 3ª Forma DIFRENTE DO USUAL, pois é esperada uma exceção.
	 * Usando @Role
	 */
	@Test 
	public void testLocacao_filmeSemEstouqe_3() throws Exception{
		//cenário
		filme = new Filme("O cara de pau.", 0, 15.00);
		//está deve ser definida dentro do cenário
		//sempre antes da execução para o JUnit entender.
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque!");
		//ação
		service.alugarFilme(usuario, filme);
		

	}	
	/*
	 * Pergunta, qual a melhor?
	 * Qual devo usar afinal? 
	 * 
	 * Explicações:
	 * 1º Elegente = Não recomendável, pois não consegue verificar a mensagem.
	 * Utilize está técnina se for criar uma exception personalizada.
	 */
	
	
}
