package br.com.bexs.tourworld.domain.model;

import java.util.List;

public class Branco extends Cor {

	public void busca(Aeroporto aeroporto, List<Aeroporto> list) {
		aeroporto.setCor(new Cinza(), list);
	}
}
