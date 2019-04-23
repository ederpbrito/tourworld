package br.combexs.tourworld.domain.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.bexs.tourworld.domain.model.Aeroporto;

public class AeroportoTest {

	@Test
	public void criar_Aeroporto_Origem_Viagem() {
		Aeroporto aeroporto = new Aeroporto("BRC", true, false);

		assertTrue(aeroporto.isOrigemInicial());
		assertFalse(aeroporto.isDestinoFinal());
	}

	@Test
	public void criar_Aeroporto_Destino_Viagem() {
		Aeroporto aeroporto = new Aeroporto("DMG", false, true);
		
		assertFalse(aeroporto.isOrigemInicial());
		assertTrue(aeroporto.isDestinoFinal());
	}
	
	@Test
	public void criar_Aeroporto_Conexao_Viagem() {
		Aeroporto aeroporto = new Aeroporto("ORL", false, false);
		
		assertFalse(aeroporto.isOrigemInicial());
		assertFalse(aeroporto.isDestinoFinal());
	}
	
	@Test
	public void adicionar_Conexao_Viagem() {
		Aeroporto aeroporto1 = new Aeroporto("GRU", true, false);
		Aeroporto aeroporto2 = new Aeroporto("BRC", false, false);
		Aeroporto aeroporto3 = new Aeroporto("SCL", false, false);
		Aeroporto aeroporto4 = new Aeroporto("ORL", false, false);
		Aeroporto aeroporto5 = new Aeroporto("CDG", false, true);
		
		aeroporto1.addAdjacentes(aeroporto2, 10);
		aeroporto1.addAdjacentes(aeroporto3, 20);
		aeroporto1.addAdjacentes(aeroporto4, 56);
		aeroporto1.addAdjacentes(aeroporto5, 75);		
		
		assertEquals(4,aeroporto1.getAdjacentes().size());
	}
	
	@Test
	public void obter_Rota_Mais_Barata() {
		Aeroporto aeroporto1 = new Aeroporto("GRU", true, false);
		Aeroporto aeroporto2 = new Aeroporto("BRC", false, false);
		Aeroporto aeroporto3 = new Aeroporto("SCL", false, false);
		Aeroporto aeroporto4 = new Aeroporto("ORL", false, false);
		Aeroporto aeroporto5 = new Aeroporto("CDG", false, true);
		
		aeroporto1.addAdjacentes(aeroporto2, 10);
		aeroporto1.addAdjacentes(aeroporto3, 20);
		aeroporto1.addAdjacentes(aeroporto4, 56);
		aeroporto1.addAdjacentes(aeroporto5, 75);		
		
		Aeroporto aeroportoDestino = aeroporto1.getMelhorRota();
		
		assertEquals("CDG",aeroportoDestino.getNome());
		assertEquals(75,aeroporto1.getValor(aeroportoDestino));
		assertTrue(aeroportoDestino.isRota());
	}

}
