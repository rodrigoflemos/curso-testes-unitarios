package br.com.lemos.testesunitarios.servicos;

import static br.com.lemos.testesunitarios.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.lemos.testesunitarios.entidades.Filme;
import br.com.lemos.testesunitarios.entidades.Locacao;
import br.com.lemos.testesunitarios.entidades.Usuario;
import br.com.lemos.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.lemos.testesunitarios.exceptions.LocadoraException;
import br.com.lemos.testesunitarios.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		
		if(filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filme vazio");
		
		for(Filme filme: filmes) {
			if(filme.getEstoque()== 0)
				throw new FilmeSemEstoqueException();
		}
		if(usuario == null)
			throw new LocadoraException("Usuario vazio");
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double valorTotal = 0d;
		
		for(int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao(); 
			
			switch(i) {
				case 2:
					valorFilme = valorFilme * 0.75;
					break;
				case 3:
					valorFilme = valorFilme * 0.50;
					break;
				case 4:
					valorFilme = valorFilme * 0.25;
					break;
				case 5:
					valorFilme = valorFilme * 0.0;
					break;
			}
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
}