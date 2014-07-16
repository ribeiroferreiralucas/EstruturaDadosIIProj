package br.ufrrj.im.cc.ed2.index.dispersao;

public class TabelaDispersao<K,E> {
	
	private Elemento<K,E>[] tabela;
	
	@SuppressWarnings("unchecked")
	public TabelaDispersao(int tamanho){
		tabela = new Elemento[tamanho];
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = new Elemento<K,E>();			
		}
	}
	
	public TabelaDispersao(){
		this(40);
	}
	
	public void adiciona(K chave,E objeto){
		long index = chave.hashCode(); 
		if(index>=0){
			index = index % tabela.length;
		}else{
			index = -index %tabela.length;
		}
		tabela[(int)index].adicionar(chave,objeto);
	}
	
	public E recupera(K chave){
		long index = chave.hashCode(); 
		if(index>=0){
			index = index % tabela.length;
		}else{
			index = -index %tabela.length;
		}
		return tabela[(int)index].recuperar(chave);
	}

	
	public void imprimeTabela(){
		for (int i = 0; i < tabela.length; i++) {
			Elemento<K,E> elemento = tabela[i];
			elemento.imprimeTodos();
			System.out.println("------------");
		}
	}
	

}
