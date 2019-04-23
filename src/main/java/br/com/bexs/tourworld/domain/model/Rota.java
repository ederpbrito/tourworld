package br.com.bexs.tourworld.domain.model;

public class Rota {

	private String origem;
	private String destino;
	private int valor;	
	
	public Rota(String origem, String destino, int valor) {
		setOrigem(origem);
		setDestino(destino);
		setValor(valor);
	}

	public String getOrigem() {
		return origem;
	}
	
	private void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public String getDestino() {
		return destino;
	}
	
	private void setDestino(String destino) {
		this.destino = destino;
	}
	
	public int getValor() {
		return valor;
	}
	
	private void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return origem + "," + destino + "," + valor;
	}
}
