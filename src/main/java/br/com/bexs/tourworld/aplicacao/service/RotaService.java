package br.com.bexs.tourworld.aplicacao.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.bexs.tourworld.domain.model.Aeroporto;
import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.domain.model.exception.TourException;
import br.com.bexs.tourworld.domain.model.service.MalhaAerea;

public class RotaService {

	private MalhaAerea malhaAerea;
	
	public RotaService(String nomeArquivo) throws FileNotFoundException {
		malhaAerea = new MalhaAerea(nomeArquivo);
	}

	public RotaService(String rota, String nomeArquivo) throws IllegalAccessException {

		if (!Util.isEntradaValida(rota))
			throw new IllegalArgumentException("Entrada de dados invalida. Ex,: ABC-DGB");

		String[] aeroportos = rota.split("-");
		
		String partida = aeroportos[0];
		String chegada = aeroportos[1];

		try {			
			malhaAerea = new MalhaAerea(partida, chegada, nomeArquivo);
		} catch (IOException e) {
			throw new TourException("Erro na leitura do arquivo: " + e.getMessage());
		}		
	}

	public String getMelhorRota() {

		
		List<Aeroporto> rotas = malhaAerea.getMelhorRota();
		
		StringBuilder str = new StringBuilder();

		str.append(rotas.stream().map(n -> n.getNome()).collect(Collectors.joining("-")));
		str.append(" > R$ ");
		str.append(Double.parseDouble(Integer.toString(malhaAerea.getPartida().getValorMelhorRota())));

		return str.toString();		
	}

	public void addRota(Rota rota) throws IllegalAccessException {		
		try {
			malhaAerea.addRota(rota);
		} catch (FileNotFoundException e) {
			throw new TourException("Arquivo não foi encontrado!");
		}
	}	
}
