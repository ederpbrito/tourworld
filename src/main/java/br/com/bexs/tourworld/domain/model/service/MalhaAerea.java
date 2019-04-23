package br.com.bexs.tourworld.domain.model.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.bexs.tourworld.domain.model.Aeroporto;
import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.infraestrutura.repository.Repository;
import br.com.bexs.tourworld.infraestrutura.repository.RepositoryRota;

public class MalhaAerea {

	private Aeroporto origem;
	private Aeroporto destino;
	private Set<Aeroporto> aeroportos;
	private Repository repository;
	
	public MalhaAerea(String nomeArquivo) throws FileNotFoundException {
		repository = new RepositoryRota(nomeArquivo);
	}
	
	public MalhaAerea(String nomeOrigem, String nomeDestino, String nomeArquivo) throws IllegalAccessException, IOException {
		repository   = new RepositoryRota(nomeArquivo);
		aeroportos   = new HashSet<>();
		this.origem  = new Aeroporto(nomeOrigem,true, false);
		this.destino = new Aeroporto(nomeDestino,false, true);
		
		carregarAeroportos();
	}
	
	public MalhaAerea(String nomeOrigem, String nomeDestino) throws IllegalAccessException {
		aeroportos = new HashSet<>();
		this.origem  = new Aeroporto(nomeOrigem,true, false);
		this.destino = new Aeroporto(nomeDestino,false, true);
	}

	public void criarAeroporto(String nome) {					
		Aeroporto no = new Aeroporto(nome, origem.getNome().equals(nome), destino.getNome().equals(nome));	
		aeroportos.add(no);
	}
	
	public void addConexao(Aeroporto origem, Aeroporto destino, int valor) {		
		if(origem.getNome().equals(destino.getNome())) {
			throw new IllegalArgumentException("Origem não pode ser igual à destino. Nome: " + origem.getNome());
		}
		
		origem.addAdjacentes(destino, valor);
	}
	
	public void addRota(Rota rota) throws IllegalAccessException, FileNotFoundException {
		repository.addRota(rota);
	}
	
	public Aeroporto getAeroporto(String nome) {
		return	aeroportos.stream()
				.filter(x -> x.getNome().equals(nome))
				.findFirst()
				.orElse(null);
	}
	
	public Aeroporto getConexao(Aeroporto aeroporto, String conexao) {
		return	aeroporto.getAdjacentes()
				.stream().filter(n -> n.getNome().equals(conexao))
				.findFirst()
				.orElse(null);
	}

	public Aeroporto getOrigem() {
		return origem;
	}
	
	public Aeroporto getDestino() {
		return destino;
	}
	
	public Aeroporto getPartida() {
		return getAeroporto(origem.getNome());
	}
	
	private void carregarAeroportos() throws IllegalAccessException, IOException {
		
		Aeroporto aeroportoOrigem;
		Aeroporto aeroportoDestino;	
		
		
		for(Rota rota: repository.getRotas()) {
			
			if(getAeroporto(rota.getOrigem()) == null) {
				aeroportoOrigem  = new Aeroporto(rota.getOrigem(), isPartida(rota.getOrigem()), isDestino(rota.getOrigem()));
				aeroportos.add(aeroportoOrigem);
			}else {
				aeroportoOrigem = getAeroporto(rota.getOrigem());				
			}
			
			if(getAeroporto(rota.getDestino()) == null) {
				aeroportoDestino = new Aeroporto(rota.getDestino(), isPartida(rota.getDestino()), isDestino(rota.getDestino()));
				aeroportos.add(aeroportoDestino);
			}else {
				aeroportoDestino = getAeroporto(rota.getDestino());
			}
			
			aeroportoOrigem.addAdjacentes(aeroportoDestino, rota.getValor());
		}
	}
	
	private boolean isPartida(String nome) {
		return this.origem.getNome().equals(nome);
	}
	
	private boolean isDestino(String nome) {
		return this.destino.getNome().equals(nome);
	}

	public List<Aeroporto> getMelhorRota() {
		
		List<Aeroporto> retorno = new ArrayList<>();
		Aeroporto partida = getAeroporto(origem.getNome());
		
		partida.buscaProfundidade(retorno);
		
		List<Aeroporto> removerDuplicados = retorno.stream().distinct().collect(Collectors.toList());
		Collections.reverse(removerDuplicados);
		
		return removerDuplicados;
	}

	public void setRepository(Repository repository) throws IllegalAccessException, IOException {
		this.repository = repository;
		carregarAeroportos();
	}

	public List<Aeroporto> getAllAeroporto() {		
		return Collections.unmodifiableList(aeroportos.stream().collect(Collectors.toList()));
	}
}
