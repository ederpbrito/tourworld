package br.com.bexs.tourworld.infraestrutura.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import br.com.bexs.tourworld.domain.model.Rota;

public interface Repository {

	public void addRota(Rota rota) throws IllegalAccessException;
	public List<Rota> getRotas() throws IllegalAccessException, FileNotFoundException, IOException;
}
