package br.com.lemos.testesunitarios.servicos;

import static br.com.lemos.testesunitarios.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.com.lemos.testesunitarios.entidades.Filme;
import br.com.lemos.testesunitarios.entidades.Locacao;
import br.com.lemos.testesunitarios.entidades.Usuario;
import br.com.lemos.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.lemos.testesunitarios.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {
		
		if(filme == null)
			throw new LocadoraException("Filme vazio");
		
		if(filme.getEstoque()== 0)
			throw new FilmeSemEstoqueException();
		
		if(usuario == null)
			throw new LocadoraException("Usuario vazio");
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}