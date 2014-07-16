package br.ufrrj.im.cc.ed2.join.base;

import java.util.LinkedList;
import java.util.List;

import br.ufrrj.im.cc.ed2.catalogo.Catalogo;
import br.ufrrj.im.cc.ed2.catalogo.Coluna;



public class Tupla implements Iterator{
	

	private List<ColunaTupla> listaColunas;
	private int contador;
	private long posicao;
	
	public Tupla(){
		listaColunas = new LinkedList<>();
	}
	
	public Tupla(String linha, String nomeRelacao, long posicao){
		this();
		String[] valores = linha.split("\t");
		List<Coluna> colunasRelacao = Catalogo.getInstancia().recuperaColunas(nomeRelacao);
		if(valores.length != colunasRelacao.size()){
			throw new RuntimeException("O número de colunas na descrição da relação "+nomeRelacao+" é incompatível com"
					+ " o lido do arquivo. Linha lida: "+linha);
		}
		for (int i = 0; i < valores.length; i++) {
			String valor = valores[i];
			String nomeColuna = colunasRelacao.get(i).getNomeColuna();
			ColunaTupla colunaTupla = new ColunaTupla(nomeColuna, valor);
			listaColunas.add(colunaTupla);
			this.posicao = posicao;
		}
	}

	@Override
	public Iterator open() {
		contador = 0;
		return null;
	}

	@Override
	public Iterator next() {
		if(contador < listaColunas.size())
			return listaColunas.get(contador++);
		return null;
	}

	@Override
	public Iterator close() {
		return null;
	}

	public void adicionaColuna(ColunaTupla colunaTupla) {
		listaColunas.add(colunaTupla);
	}
	
	@Override
	public String toString() {
		String resultado = "";
		for (ColunaTupla colunaTupla : listaColunas) {
			resultado += colunaTupla.toString();
		}
			return resultado;
	}

	public String getValorCampo(String campoRelacao) {
		for (ColunaTupla colunaTupla : listaColunas) {
			if(colunaTupla.getNomeColuna().equals(campoRelacao))
				return colunaTupla.getValor();
		}
		return null;
	}

	public void concatena(Tupla tupla) {
		listaColunas.addAll(tupla.listaColunas);
	}

	@Override
	public long calculaCusto() {
		return -1;
	}
	
	public void adicionaPosicaoNoArquivo(long posicao) {
		this.posicao = posicao;		
	}

	public long getPosicao() {
		return posicao;
	}
	
}
