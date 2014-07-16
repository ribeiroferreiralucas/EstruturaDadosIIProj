package br.ufrrj.im.cc.ed2.selecao;

import br.ufrrj.im.cc.ed2.join.base.Iterator;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

/*´Vai retornar ou uma tupla ou uma relação.
 * 
 */
public class Selecao implements Iterator {
	
	private Relacao relacao;
	String campo;
	String condicao;
	String parametro;

	int modo;
	public Selecao(String relacao, String campo, String condicao, String parametro) {
		
		this.relacao= new Relacao(relacao);
		this.campo = campo;
		this.condicao = condicao;
		this.parametro = parametro;
		
	}
	@Override
	public Iterator open() {
		relacao.open();
		
		if(condicao.equals('=')){
			modo = 0;
		}else if(condicao.equals("like")){
			modo = 1;
		}
		
		return null;
	}

	@Override
	public Iterator next() {
		if(modo==0){
			Tupla tupla;
			while ((tupla = (Tupla) relacao.next()) != null) {
				if(tupla.getValorCampo(campo).equals(parametro)){
					return tupla;
				}
			}
		}else if(modo==1){
			Tupla tupla;
			while ((tupla = (Tupla) relacao.next()) != null) {
				if(tupla.getValorCampo(campo).contains(parametro)){
					return tupla;
				}
			}
		}
		
		return null;
	}

	@Override
	public Iterator close() {

		relacao.close();
		return null;
	}

	@Override
	public long calculaCusto() {
		// TODO Auto-generated method stub
		return 0;
	}

}
