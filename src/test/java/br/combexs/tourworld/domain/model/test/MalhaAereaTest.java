package br.combexs.tourworld.domain.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import br.com.bexs.tourworld.domain.model.Aeroporto;
import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.domain.model.service.MalhaAerea;
import br.com.bexs.tourworld.infraestrutura.repository.Repository;
import br.combexs.tourworld.infraestrutura.test.RepositoryRotaTest;

public class MalhaAereaTest {

	private MalhaAerea malha;
	private Repository repository;
	private String partida;
	private String chegada;

	@Before
	public void inicializacao() throws IllegalAccessException {
		partida = "GRU";
		chegada = "CDG";

		malha = new MalhaAerea(partida, chegada);
	}

	@Test
	public void adicionar_Aeroporto_Origem_Malha_Aerea_Test() {

		malha.criarAeroporto(partida);
		Aeroporto aeroporto = malha.getAeroporto(partida);

		assertTrue(aeroporto.getNome().equals("GRU"));
		assertTrue(aeroporto.isOrigemInicial());
		assertFalse(aeroporto.isDestinoFinal());
	}

	@Test
	public void adicionar_Aeroporto_Destino_Malha_Aerea_Test() {
		malha.criarAeroporto(chegada);
		Aeroporto aeroporto = malha.getAeroporto(chegada);

		assertTrue(aeroporto.getNome().equals("CDG"));
		assertFalse(aeroporto.isOrigemInicial());
		assertTrue(aeroporto.isDestinoFinal());
	}	

	@Test
	public void adicionar_Aeroporto_Conexao_Malha_Aerea_Test() {
		
		malha.criarAeroporto("BRC");
		malha.criarAeroporto("SCL");
		
		Aeroporto aeroporto = malha.getAeroporto("BRC");
		Aeroporto conexao   = malha.getAeroporto("SCL");
				
		malha.addConexao(aeroporto, conexao, 10);
		
		Aeroporto nomeConexao = malha.getConexao(aeroporto, conexao.getNome());
		
		assertEquals("SCL", nomeConexao.getNome());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void adicionar_Aeroporto_Conexao_Destino_Iguais_Test() {
		malha.criarAeroporto("BRC");
		
		Aeroporto aeroporto = malha.getAeroporto("BRC");
		Aeroporto conexao   = malha.getAeroporto("BRC");
		
		malha.addConexao(aeroporto, conexao, 10);
	}

	@Test
	public void carregar_Aeroportos_Do_Repository() throws IllegalAccessException, IOException {
		repository = new RepositoryRotaTest();

		fixtures(repository);

		MalhaAerea malhaAerea = new MalhaAerea("GRU", "BRC");
		malhaAerea.setRepository(repository);

		int total = malhaAerea.getAllAeroporto().size();

		assertEquals(total, 5);

		assertEquals("GRU", malhaAerea.getAeroporto("GRU").getNome());
		assertEquals("BRC", malhaAerea.getAeroporto("BRC").getNome());
		assertEquals("SCL", malhaAerea.getAeroporto("SCL").getNome());
		assertEquals("ORL", malhaAerea.getAeroporto("ORL").getNome());
		assertEquals("CDG", malhaAerea.getAeroporto("CDG").getNome());

	}

	@Test
	public void obter_Rota_De_Menor_Valor() throws IllegalAccessException, IOException {
		repository = new RepositoryRotaTest();
		
		fixtures(repository);
		
		MalhaAerea malhaAerea=  new MalhaAerea("GRU", "CDG");
		malhaAerea.setRepository(repository);
		
		List<Aeroporto> melhorRota = malhaAerea.getMelhorRota();
		
		Aeroporto partida = malhaAerea.getAeroporto("GRU");
				
		assertEquals("GRU-BRC-SCL-ORL-CDG",formatarSaida(melhorRota));
		assertEquals(40, partida.getValorMelhorRota());
		
	}	
	
	@Test
	public void adicionar_Novas_Rotas_Obter_Menor_Valor() throws IllegalAccessException, IOException {
		repository = new RepositoryRotaTest();
		
		fixtures(repository);
		
		MalhaAerea malhaAerea=  new MalhaAerea("GRU", "CDG");
		
		repository.addRota(new Rota("BRC", "DMG", 2));
		repository.addRota(new Rota("DMG", "ORL", 7));
		
				
		malhaAerea.setRepository(repository);
		
		List<Aeroporto> melhorRota = malhaAerea.getMelhorRota();
		
		Aeroporto partida = malhaAerea.getAeroporto("GRU");
				
		assertEquals("GRU-BRC-DMG-ORL-CDG",formatarSaida(melhorRota));
		assertEquals(24, partida.getValorMelhorRota());
	}
	
	public String formatarSaida(List<Aeroporto> rotas) {

		StringBuilder str = new StringBuilder();
		str.append(rotas.stream().map(n -> n.getNome()).collect(Collectors.joining("-")));

		return str.toString();
	}

	public void fixtures(Repository repository) throws IllegalAccessException, FileNotFoundException {

		repository.addRota(new Rota("GRU", "BRC", 10));
		repository.addRota(new Rota("BRC", "SCL", 5));
		repository.addRota(new Rota("GRU", "CDG", 75));
		repository.addRota(new Rota("GRU", "SCL", 20));
		repository.addRota(new Rota("GRU", "ORL", 56));
		repository.addRota(new Rota("ORL", "CDG", 5));
		repository.addRota(new Rota("SCL", "ORL", 20));
	}

}
