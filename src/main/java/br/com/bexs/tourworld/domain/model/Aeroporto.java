package br.com.bexs.tourworld.domain.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Aeroporto {

	private Set<Aeroporto> adjacentes = new HashSet<>();
	private Map<Aeroporto, Integer> valores;
	private Cor cor;
	private boolean isRota = false;
	private String name;
	private boolean isOrigemInicial;
	private boolean isDestinoFinal;

	public Aeroporto(String name, boolean isInicio, boolean isFim) {
		valores = new HashMap<>();
		this.name = name;
		cor = new Branco();
		isOrigemInicial = isInicio;
		isDestinoFinal = isFim;
		isRota = isDestinoFinal;
	}

	public void buscaProfundidade(List<Aeroporto> list) {
		cor.busca(this, list);
	}

	public Set<Aeroporto> getAdjacentes() {
		return adjacentes;
	}

	public void addAdjacentes(Aeroporto adj, int valor) {
		adjacentes.add(adj);
		valores.put(adj, valor);
	}

	public void setCor(Cor cor, List<Aeroporto> list) {
		this.cor = cor;
		cor.assumiu(this, list);
	}

	public String getNome() {
		return name;
	}

	public int getValorMelhorRota() {

		int retorno = 0;

		if (this.getAdjacentes().size() != 0) {

			List<Aeroporto> rotas = getFiltrarPorRota();

			if (rotas.size() > 0) {
				retorno = valores.get(rotas.get(0));
			}
		}

		return retorno;
	}

	public Aeroporto getMelhorRota() {

		Aeroporto melhorRota = null;

		if (this.getAdjacentes().size() != 0) {

			List<Aeroporto> rotas = getFiltrarPorRota();

			if (rotas.size() > 0) {
				melhorRota = rotas.get(0);
			}
		}

		return melhorRota;
	}

	private List<Aeroporto> getFiltrarPorRota() {

		return valores.entrySet().stream().filter(n -> n.getKey().isRota())
				.sorted(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());
	}

	public void agregarValor(Aeroporto adj, int valor) {
		int valueOld = valores.get(adj);
		valores.replace(adj, valor + valueOld);
	}

	public int getValor(Aeroporto adj) {
		return valores.get(adj);
	}

	public boolean isRota() {
		return isRota;
	}

	public void setRota(boolean isRota) {
		this.isRota = isRota;
	}

	public boolean isOrigemInicial() {
		return isOrigemInicial;
	}

	public boolean isDestinoFinal() {
		return isDestinoFinal;
	}

	@Override
	public String toString() {
		return "No [name=" + name + " valores=" + valores + "]";
	}
}
