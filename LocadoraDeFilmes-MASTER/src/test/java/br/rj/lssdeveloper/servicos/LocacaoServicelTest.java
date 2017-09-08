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

public class LocacaoServicelTest {

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
	}

	@Test(expected = FilmeSemEstoqueExcepetion.class)
	public void testLocacao_filmeSemEstouqe_1() throws Exception {
		filme = new Filme("O cara de pau.", 0, 15.00);
		service.alugarFilme(usuario, filme);
	}

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

	@Test
	public void testLocacao_filmeSemEstouqe_3() throws Exception {
		// cenário
		filme = new Filme("O cara de pau.", 0, 15.00);
		// está deve ser definida dentro do cenário
		//e antes de executar a ação
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

}
