package br.ufrrj.im.cc.ed2.catalogo;

import java.util.Map;

public class Coluna implements Comparable<Coluna>{
	private String nome;
	private String tipo;
	private int ordem;
	private boolean valoresUnicos;
	private Map<String, Integer> histograma;
	//outros metadados... histograma de valores, por exemplo.
	
	public Coluna(String nome, String tipo, int ordem, 
			Map<String, Integer> histograma, boolean valoresUnicos) {
		this.nome = nome;
		this.tipo = tipo;
		this.ordem = ordem;
		this.histograma = histograma;
		this.valoresUnicos = valoresUnicos;
	}
	
	public Coluna(String nome, String tipo, int ordem,
			Map<String, Integer> histograma) {
		this(nome, tipo, ordem, histograma, false);
	}
	
	public Coluna(String nome, String tipo, int ordem) {
		this(nome, tipo, ordem, null, false);
	}


	@Override
	public int compareTo(Coluna outraColuna) {
		if(this.ordem > outraColuna.ordem)
			return 1;
		else
			return -1;
	}
	
	public String getNomeColuna(){
		return nome;
	}
	
	public boolean possuiValoresUnicos(){
		return valoresUnicos;
	}

	public void adicionarHistograma(Map<String, Integer> histograma) {
		this.histograma = histograma;
	}

	public int numeroEstimadoDeLinhas(String valorBuscado) {
		return histograma.get(valorBuscado);
	}
	

}
