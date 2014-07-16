package br.ufrrj.im.cc.ed2.catalogo;

import java.util.List;
import java.util.Map;


public class ItemCatalogo {
	
	private String nomeArquivo;
	private String nomeRelacao;
	private List<Coluna> colunas;
	private int numeroLinhas;
	//outros metadados... 
	
	public ItemCatalogo(String nomeRelacao, String nomeArquivo, List<Coluna> colunas, int numeroLinhas){
		this.nomeRelacao = nomeRelacao;
		this.nomeArquivo = nomeArquivo;
		this.colunas = colunas;
		this.numeroLinhas = numeroLinhas;
	}
	
	public boolean comparaNomeRelacao(String nomeRelacao) {
		return this.nomeRelacao.equals(nomeRelacao);
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public List<Coluna> getColunas() {
		return colunas;
	}

	public int numeroLinhas() {
		return numeroLinhas;
	}

	public boolean campoComValorUnico(String nomeCampo) {
		for (Coluna coluna : colunas) {
			if(coluna.getNomeColuna().equalsIgnoreCase(nomeCampo))
				return coluna.possuiValoresUnicos();
		}
		return false;
	}

	public void adicionaHistograma(String nomeCampo,
			Map<String, Integer> histograma) {
		for (Coluna coluna : colunas) {
			if(coluna.getNomeColuna().equalsIgnoreCase(nomeCampo))
				coluna.adicionarHistograma(histograma);
		}
		
	}

	public int numeroEstimadoLinhas(String nomeCampo, String valorBuscado) {
		for (Coluna coluna : colunas) {
			if(coluna.getNomeColuna().equalsIgnoreCase(nomeCampo)){
				return coluna.numeroEstimadoDeLinhas(valorBuscado);
			}
		}
		return -1;
	}
	
}
