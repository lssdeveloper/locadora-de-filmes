package br.rj.lssdeveloper.servicos;

import static org.junit.Assert.*;

import java.util.Date;

import org.hamcrest.core.IsSame;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;
import static br.rj.lssdeveloper.utils.DataUtils.isMesmaData;
import static br.rj.lssdeveloper.utils.DataUtils.obterDataComDiferencaDias;;

public class LocacaoServiceFinalTest {
	//Resultado final sem os comentários da LocacaoServiceTest
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void test() {
		//cenário
		LocacaoService servico = new LocacaoService();
		Usuario usuario = new Usuario("Roberval");
		Filme filme = new Filme("O cara de pau.", 2, 15.00);
		
		//ação
		Locacao locacao = servico.alugarFilme(usuario, filme);
		
		//verificação
		error.checkThat(locacao.getValor(), is(equalTo(15.00)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1) ), is(true));		
		
	}



}
