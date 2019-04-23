package br.com.bexs.tourworld.main;

import java.util.Scanner;

import br.com.bexs.tourworld.aplicacao.service.RotaService;


public class Console {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IllegalAccessException {
		
		String nomeArquivo = args[0];
		
		System.out.println("please enter the route: Ex.: ABC-DEF");
		
		Scanner scanner = new Scanner(System.in);
		String rota = scanner.nextLine();
				
		RotaService rotaService = new RotaService(rota, nomeArquivo);

		String melhorRota = rotaService.getMelhorRota();
		
		System.out.println("best route: " + melhorRota);
	}	
}
