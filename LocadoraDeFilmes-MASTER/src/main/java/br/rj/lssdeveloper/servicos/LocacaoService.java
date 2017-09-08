package br.rj.lssdeveloper.servicos;

import static br.rj.lssdeveloper.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;
import br.rj.lssdeveloper.exceptions.FilmeSemEstoqueExcepetion;
import br.rj.lssdeveloper.exceptions.LocadoraException;

public class LocacaoService {
	
	@SuppressWarnings("unused")
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueExcepetion, LocadoraException {
		
		//Este exemplo é como encontramos nos projetos em geral (1º caso = Forma elegante)
		if(usuario==null)
			throw new LocadoraException("Usuário vazio!");
		

		if(filme==null)
			throw new LocadoraException("Filme vazio!");
		
		if (filme.getEstoque() == 0) 
			throw new FilmeSemEstoqueExcepetion("Filme sem estoque!");	
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		return locacao; 
	}


}