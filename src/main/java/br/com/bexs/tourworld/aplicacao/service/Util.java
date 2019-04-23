package br.com.bexs.tourworld.aplicacao.service;

public class Util {

	public static boolean isEntradaValida(String entrada) {
		return entrada.matches("[A-Z]{3}[-][A-Z]{3}");
	}	
}
