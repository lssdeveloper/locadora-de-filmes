package br.rj.lssdeveloper.servicos;

import static br.rj.lssdeveloper.utils.DataUtils.isMesmaData;
import static br.rj.lssdeveloper.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;
import br.rj.lssdeveloper.exceptions.FilmeSemEstoqueExcepetion;
import br.rj.lssdeveloper.exceptions.LocadoraException;;

public class LocacaoServiceFinalTest {
	// Resultado final sem os comentários da LocacaoServiceTest

	private LocacaoService service;
	private Usuario usuario;
	private Filme filme;
	private Locacao locacao;

	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		// cenário
		filme = new Filme("O cara de pau.", 2, 15.00);
		service = new LocacaoService();
		usuario = new Usuario("Roberval");
	}

	@After
	public void tearDown() {
		service = null;
		usuario = null;
		filme = null;
	}

	@Test
	public void testLocacao() throws Exception {
		// ação
		locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(15.00)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

		/*
		 * ============Diferença entre falha e erro=============== Falhas
		 * ocorrem quando o teste é executado sem problemas, porém alguma
		 * condição que era esperada não foi atendida. No caso, representada
		 * pela forma de assertivas.
		 *
		 * Erro, quando algum problema durante a exeução do teste, impede que o
		 * mesmo seja concluído! Ou seja, quando ocorre alguma exceçao não
		 * esperada ou não tratada.
		 */

	}

	/*
	 * 1ª Forma ELEGANTE, pois é esperada uma exceção. Deixo o JUnit controlar.
	 * Esta é formamas usada encontrada nos projetos em geral porém deve ser
	 * utilizada criando a exception personalizada Ex: LocadoraException e então
	 * para cada caso uma respectiva mensagem como no exemplo usuario e filme
	 * vazio FilmeSemEstoqueException pode ser removida e substituída por
	 * LocadoraException deixei aqui por questões de exemplo
	 * 
	 * Quando usar:
	 * Quando a penas a exceção importar para você
	 */
	@Test(expected = FilmeSemEstoqueExcepetion.class)
	public void testLocacao_filmeSemEstouqe_1() throws Exception {
		filme = new Filme("O cara de pau.", 0, 15.00);
		service.alugarFilme(usuario, filme);
	}

	/*
	 * 2ª Forma ROBUSTA, oferece um maior controle e percepção, esta é a
	 * diferença entre a elegante acima. Neste caso, eu quem controlo o
	 * tratamento de exceção. Através do try / catch
	 * RECOMENDADO USAR NA MAIORIA DOS CASOS (PRINCIPALMENTE COM MOCKS)
	 * Quando usar:
	 * Quando a exceção importa e precisa checar a mensagem
	 */
	@Test
	public void testLocacao_filmeSemEstouqe_2() {
		filme = new Filme("O cara de pau.", 0, 15.00);
		try {
			service.alugarFilme(usuario, filme);
			// fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			// Além de capturar a exceção posso capturar a mensagem.
			assertThat(e.getMessage(), is("Filme sem estoque!"));
		}
	}

	/*
	 * 3ª Forma DIFRENTE DO USUAL, pois é esperada uma exceção. Usando @Role
	 *
	 * Quando usar:
	 * Quando a exceção importa e precisa checar a mensagem
	 * 
	 */
	@Test
	public void testLocacao_filmeSemEstouqe_3() throws Exception {
		// cenário
		filme = new Filme("O cara de pau.", 0, 15.00);
		// está deve ser definida dentro do cenário
		// sempre antes da execução para o JUnit entender.
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque!");
		// ação
		service.alugarFilme(usuario, filme);

	}

	// Testando a LocadoraException na 2ª forma (Robusta)
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueExcepetion {
		try {
			service.alugarFilme(null, filme);
			// service.alugarFilme(usuario, filme);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio!"));
		}
		System.out.println("Forma robusta");
	}

	// Testando a LocadoraException na 3ª forma (DIFERENTE)
	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueExcepetion, LocadoraException {
		exception.expect(Exception.class);
		exception.expectMessage("Filme vazio!");
		service.alugarFilme(usuario, null);
		
		System.out.println("Forma nova");
	}
	/*
	 * Pergunta, qual a melhor? Qual devo usar afinal?
	 * 
	 * Explicações: 1º Elegente = Só é recomendável utlizla se for especificar a
	 * exceção como no exemplo acima criando uma excepetion extendendo de
	 * exception, pois se houver muitas outras exceções, não consegue verificar
	 * a mensagem realmente. Utilize está técnina se for criar uma exception
	 * personalizada.
	 * 
	 * Usar quando você consegue garantir que a exceção será lançada apenas por um motivo!
	 */

}
