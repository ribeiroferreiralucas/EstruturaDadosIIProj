package br.ufrrj.im.cc.ed2.juncao;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class NestedLoop implements Iterator {

	private Relacao relacaoDireita;
	private Relacao relacaoEsquerda;
	private String campoRelacaoD;
	private String campoRelacaoE;
	private Iterator tupla;
	private String valorE;
	private Tupla tuplaE;

	private int modo;
	public NestedLoop(String relacaoE, String campoRelacaoE, String relacaoD, String campoRelacaoD) {
		this.relacaoEsquerda = new Relacao(relacaoE);
		this.relacaoDireita = new Relacao(relacaoD);
		this.campoRelacaoD = campoRelacaoD;
		this.campoRelacaoE = campoRelacaoE;
		modo = 0;
	}
	
	public NestedLoop(Iterator tupla, String campoRelacaoE, String relacaoD, String campoRelacaoD){
		this.tuplaE = (Tupla) tupla;
		this.relacaoDireita = new Relacao(relacaoD);
		this.campoRelacaoD = campoRelacaoD;
		this.campoRelacaoE = campoRelacaoE;
		modo = 1;
	}

	@Override
	public Iterator open() {
		if(modo==0){
			relacaoDireita.open();
			relacaoEsquerda.open();
	
			tuplaE = (Tupla) relacaoEsquerda.next();
			if (tuplaE == null)
				return null;
			valorE = tuplaE.getValorCampo(campoRelacaoE);
		}
		if(modo==1){
			relacaoDireita.open();
			valorE = tuplaE.getValorCampo(campoRelacaoE);
		}
		
		return null;
	}

	@Override
	public Iterator next() {

		Tupla tuplaD;
		while ((tuplaD = (Tupla) relacaoDireita.next()) != null) {
			if (tuplaD.getValorCampo(campoRelacaoD).equals(valorE)) {
				Tupla tuplaResultante = new Tupla();
				tuplaResultante.concatena(tuplaE);
				tuplaResultante.concatena(tuplaD);
				return tuplaResultante;
			}
		}
		relacaoDireita.close();
		if(modo==0){
			relacaoDireita.open();
			tuplaE = (Tupla) relacaoEsquerda.next();
			if (tuplaE == null)
				return null;
			else{
				valorE = tuplaE.getValorCampo(campoRelacaoE);
				System.out.println(valorE);
				return next();
			}
		}
		return null;
	}

	@Override
	public Iterator close() {
		relacaoDireita.close();
		if(modo==0){
			relacaoEsquerda.close();
		}
		return null;
	}

	@Override
	public long calculaCusto() {
		// TODO Auto-generated method stub
		return 0;
	}

}
