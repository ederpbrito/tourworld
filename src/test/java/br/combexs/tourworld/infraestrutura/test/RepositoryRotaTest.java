package br.combexs.tourworld.infraestrutura.test;

import java.util.ArrayList;
import java.util.List;

import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.infraestrutura.repository.Repository;

public class RepositoryRotaTest implements Repository {

	private List<Rota> rotas = new ArrayList<>();
	
	@Override
	public void addRota(Rota rota) throws IllegalAccessException {
		rotas.add(rota);		
	}

	@Override
	public List<Rota> getRotas() throws IllegalAccessException {
		return rotas;
	}	
}
