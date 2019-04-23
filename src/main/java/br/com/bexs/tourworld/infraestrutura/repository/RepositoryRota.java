package br.com.bexs.tourworld.infraestrutura.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.infraestrutura.io.Arquivo;

public class RepositoryRota implements Repository {
	
	private Arquivo bd;	
	
	public RepositoryRota(String nomeArquivo) throws FileNotFoundException {
		bd = new Arquivo(nomeArquivo);
	}

	@Override
	public void addRota(Rota rota) throws IllegalAccessException  {
		bd.addRota(rota);
	}

	@Override
	public List<Rota> getRotas() throws IllegalAccessException, FileNotFoundException, IOException {		
		return bd.getRegistros();
	}	
}
