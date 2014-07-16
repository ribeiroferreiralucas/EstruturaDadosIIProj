package br.ufrrj.im.cc.ed2.juncao;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class Join implements Iterator {
	private Tupla tuplaE;
	private Iterator relacaoDireita;
	private String campoRelacaoD;
	private String campoRelacaoE;
	
	
	public Join(Iterator tupla, String campoRelacaoE, String relacaoD, String campoRelacaoD){
		this.tuplaE = (Tupla) tupla;
		this.relacaoDireita = new Relacao(relacaoD);
		this.campoRelacaoD = campoRelacaoD;
		this.campoRelacaoE = campoRelacaoE;
	}
	@Override
	public Iterator open() {
		
		relacaoDireita.open();
		
		return null;
	}

	@Override
	public Iterator next() {
		
		
		return null;
	}

	@Override
	public Iterator close() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long calculaCusto() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
