package br.combexs.tourworld.infraestrutura.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.bexs.tourworld.domain.model.Rota;
import br.com.bexs.tourworld.infraestrutura.io.Arquivo;

public class ArquivoTest {

	private static Arquivo arquivo;
	private static File file;
	
	@BeforeClass
	public static void inicializacao() throws IOException, IllegalAccessException  {
		
		file = File.createTempFile("input-routes", ".tmp"); 	
		arquivo = new Arquivo();
		arquivo.setFile(file);	
		fixtures();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void arquivo_Nao_Encontrado() throws IllegalAccessException, IOException {
		Arquivo arquivo = new Arquivo("ArquivoTeste");
		
		arquivo.getRegistros();
	}
	
	@Test(expected=IllegalStateException.class)
	public void adicionar_Registro_Arquivo_Sem_Origem() throws IllegalAccessException {
		Rota r1 = new Rota("", "DMG", 2);
		
		arquivo.addRota(r1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void adicionar_Registro_Arquivo_Sem_Destino() throws IllegalAccessException {
		Rota r1 = new Rota("BRC", "", 2);
		
		arquivo.addRota(r1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void adicionar_Registro_Arquivo_valor_Zerado() throws IllegalAccessException, FileNotFoundException {
		Rota r1 = new Rota("BRC", "DMG", 0);
		
		arquivo.addRota(r1);
	}
	
	@Test
	public void adicionar_Registro_Arquivo_Rota_Valida() throws IllegalAccessException, IOException {
		Rota r1 = new Rota("BRC", "DMG", 2);
		Rota r2 = new Rota("DMG", "ORL", 7);
		
		arquivo.addRota(r1);
		arquivo.addRota(r2);
		
		List<Rota> rotas = arquivo.getRegistros();
		
		assertEquals(9, rotas.size());
		
		Rota rota = rotas.stream().filter(n -> n.getOrigem().equals("DMG")).findFirst().orElse(null);
		
		assertEquals("DMG", rota.getOrigem());
		assertEquals("ORL", rota.getDestino());
	}
	
	public static void fixtures() throws IllegalAccessException {

		addRotaFile(new Rota("GRU", "BRC", 10));
		addRotaFile(new Rota("BRC", "SCL", 5));
		addRotaFile(new Rota("GRU", "CDG", 75));
		addRotaFile(new Rota("GRU", "SCL", 20));
		addRotaFile(new Rota("GRU", "ORL", 56));
		addRotaFile(new Rota("ORL", "CDG", 5));
		addRotaFile(new Rota("SCL", "ORL", 20));
	}
	
	
	private static void addRotaFile(Rota rota) throws IllegalAccessException {
		arquivo.addRota(rota);
	}
	
	@AfterClass
	public static void encerramento() {
		file.deleteOnExit();
	}

}
