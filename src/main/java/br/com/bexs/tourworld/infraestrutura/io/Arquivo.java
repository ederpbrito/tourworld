package br.com.bexs.tourworld.infraestrutura.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import br.com.bexs.tourworld.domain.model.Rota;

public class Arquivo {

	private File file;

	public Arquivo() {

	}

	public Arquivo(String nomeArquivo) throws FileNotFoundException {
		this.file = new File(nomeArquivo);
	}

	public List<Rota> getRegistros() throws IllegalAccessException, FileNotFoundException, IOException {

		String linha = null;
		int numerolinha = 1;
		List<Rota> rotas = new ArrayList<>();
		Rota rota;

		try (FileReader fr = new FileReader(this.file); BufferedReader br = new BufferedReader(fr)) {

			while ((linha = br.readLine()) != null) {

				String[] campos = linha.split(",");
				try {
					validarCampos(campos);
				} catch (IllegalStateException ex) {
					throw new IllegalAccessException(ex.getMessage() + numerolinha);
				}

				rota = new Rota(campos[0], campos[1], Integer.parseInt(campos[2]));
				rotas.add(rota);
				numerolinha++;
			}
		}

		return rotas;
	}

	public void addRota(Rota rota) throws IllegalAccessException {
		try {
			String[] campos = new String[] { rota.getOrigem(), rota.getDestino(), String.valueOf(rota.getValor()) };

			validarCampos(campos);
			String linha = "";

			if (isArquivoVazio()) {
				linha = "\n" + rota.toString();
			} else {
				linha = rota.toString();
			}

			Files.write(Paths.get(this.file.getPath()), linha.getBytes(), StandardOpenOption.APPEND);

		} catch (IOException e) {
			throw new IllegalAccessException("Ocorreu um erro ao gravar os dados: " + e.getMessage());
		}
	}

	private void validarCampos(String[] campos) {
		if (!isStringValida(campos[0]) || !isStringValida(campos[1])) {
			throw new IllegalStateException("Linha mal formada. corrigir linha: ");
		}

		if (!isNumberValor(Integer.parseInt(campos[2]))) {
			throw new IllegalStateException("Linha mal formada. corrigir linha: ");
		}
	}

	private boolean isStringValida(String valor) {
		if (valor == null || valor == "")
			return false;

		return true;
	}

	private boolean isNumberValor(Integer valor) {
		if (valor <= 0 || valor == null)
			return false;

		return true;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isArquivoVazio() {
		return this.file.length() > 0;
	}
}
