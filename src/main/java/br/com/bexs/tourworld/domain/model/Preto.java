package br.com.bexs.tourworld.domain.model;

import java.util.List;

public class Preto extends Cor {

	public void assumiu(Aeroporto aeroporto, List<Aeroporto> list) {			
		
		if(aeroporto.getMelhorRota() != null) {
			list.add(aeroporto.getMelhorRota());
		}
		
		if(aeroporto.isOrigemInicial())
			list.add(aeroporto);
	}
}
