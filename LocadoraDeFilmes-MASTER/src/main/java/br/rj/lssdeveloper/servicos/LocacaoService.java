package br.rj.lssdeveloper.servicos;

import static br.rj.lssdeveloper.utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.rj.lssdeveloper.entidades.Filme;
import br.rj.lssdeveloper.entidades.Locacao;
import br.rj.lssdeveloper.entidades.Usuario;
import br.rj.lssdeveloper.utils.DataUtils;

public class LocacaoService {
	
	public String vPublica;
	protected String vProtegida;
	private String vPrivada;
	String vDefault;

	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao; 
	}


}