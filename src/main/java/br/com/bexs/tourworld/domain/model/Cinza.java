package br.com.bexs.tourworld.domain.model;

import java.util.List;

public class Cinza extends Cor {

	public void assumiu(Aeroporto aeroporto, List<Aeroporto> list) {		
		
		for (Aeroporto conexao : aeroporto.getAdjacentes()) {				
			conexao.buscaProfundidade(list);
			
			if(conexao.isDestinoFinal() || conexao.isRota()) {				
				aeroporto.setRota(true);
			}
			
			aeroporto.agregarValor(conexao, conexao.getValorMelhorRota());			
		}	
		
		Aeroporto melhorRota = aeroporto.getMelhorRota();
		
		if(melhorRota != null) {
			list.add(melhorRota);
		}
		
		aeroporto.setCor(new Preto(), list);
	}	
}
