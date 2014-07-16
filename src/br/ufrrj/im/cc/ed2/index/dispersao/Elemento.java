package br.ufrrj.im.cc.ed2.index.dispersao;

import java.util.SortedMap;
import java.util.TreeMap;

public class Elemento<K, E> {
	
	private SortedMap<K, E> lista;
	
	
	public Elemento(){
		lista = new TreeMap<K, E>();
	}
	
	public void adicionar(K k, E objeto){
		lista.put(k,objeto);
		
	}
	
	public E recuperar(K k){
		return lista.get(k);
	}
	
	public void imprimeTodos(){
			System.out.println(lista.toString());
		
	}

}
