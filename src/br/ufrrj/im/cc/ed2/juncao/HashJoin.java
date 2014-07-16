package br.ufrrj.im.cc.ed2.juncao;

import br.ufrrj.im.cc.ed2.index.dispersao.TabelaDispersao;
import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class HashJoin implements Iterator {

	private Relacao relacaoConstrucao;
	private Relacao relacaoPoda;
	private String campoRelacao2;
	private String campoRelacao1;
	private TabelaDispersao<String, Tupla> tabela;
	private Tupla tupla;
	int modo;
	
	public HashJoin(String relacao1, String campoRelacao1, String relacao2,
			String campoRelacao2) {
		this.relacaoConstrucao = new Relacao(relacao1);
		this.relacaoPoda = new Relacao(relacao2);
		this.campoRelacao1 = campoRelacao1;
		this.campoRelacao2 = campoRelacao2;
		
		modo=0;
	}
	
	public HashJoin(Tupla tupla, String relacao2, String campoRelacao2){
		this.tupla = tupla;
		this.relacaoPoda = new Relacao(relacao2);
		this.campoRelacao2 = campoRelacao2;
		
		modo=1;
	}

	@Override
	public Iterator open() {
		
		if(modo==0){
		tabela = new TabelaDispersao<>();
		relacaoConstrucao.open();
		Tupla tupla;
		while ((tupla = (Tupla) relacaoConstrucao.next()) != null) {
			String valor = tupla.getValorCampo(campoRelacao1);
			tabela.adiciona(valor, tupla);
		}
		relacaoPoda.open();
		return this;
		}
		if(modo==1){
			relacaoPoda.open();
		}
		return this;
	}

	@Override
	public Iterator next() {
		Tupla tupla = (Tupla) relacaoPoda.next();
		if (tupla == null) {
			return null;
		}
		Tupla tuplaCorrespondente = tabela.recupera(tupla
				.getValorCampo(campoRelacao2));
		if (tuplaCorrespondente == null) {
			return new Tupla();
		} else {
			Tupla tuplaResultante = new Tupla();
			tuplaResultante.concatena(tupla);
			tuplaResultante.concatena(tuplaCorrespondente);
			return tuplaResultante;
		}
	}

	@Override
	public Iterator close() {
		relacaoConstrucao.close();
		relacaoPoda.close();
		return this;
	}

	@Override
	public long calculaCusto() {
		return relacaoConstrucao.calculaCusto() + relacaoPoda.calculaCusto();
	}

}
