package br.ufrrj.im.cc.ed2.catalogo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import br.ufrrj.im.cc.ed2.index.dispersao.TabelaDispersao;
import br.ufrrj.im.cc.ed2.join.base.Relacao;
import br.ufrrj.im.cc.ed2.join.base.Tupla;

public class Catalogo {
	
	private static Catalogo instance;
	private List<ItemCatalogo> itens;
	private HashMap<String, List<Coluna>> colunas = new HashMap<String, List<Coluna>>();
//	private List<Coluna> colunasRelacaoLivro;
//	private List<Coluna> colunasRelacaoCategoria;
	private List<Coluna> colunasRelacaoAutor;
	private TabelaDispersao<String, TabelaDispersao> tabelasDispersao;
	
//----------------------------------------------------	
	private Catalogo(){
		//os itens podem ser recuperados de um arquivo binario.
		//		colunasRelacaoLivro = new LinkedList<>();
		//		colunasRelacaoCategoria = new LinkedList<>();
		colunasRelacaoAutor = new LinkedList<>();

		criaColunas("telefone", 0, "Cliente");
		criaColunas("nome", 1, "Cliente");
		criaColunas("endereco", 2, "Cliente");
		 
		criaItemCatalogo("Cliente", "clientes.txt", colunas.get("Cliente"), 5000);
		
		criaColunas("nome_pizza", 0, "Cardapio");
		criaColunas("ingredientes", 1, "Cardapio");
		criaColunas("preco", 2, "Cardapio");
		criaItemCatalogo("Cardapio", "cardapio.txt", colunas.get("Cardapio"), 82720);
		
		criaColunas("telefone", 0,"Pedido");
		criaColunas("data", 1, "Pedido");
		criaColunas("hora", 2, "Pedido");
		criaColunas("nome_pizza", 3, "Pedido");
		criaColunas("quantidade", 4, "Pedido");
		criaItemCatalogo("Pedido", "pedidos.txt", colunas.get("Pedido"), 52);
		
		
	}

//	private void criaColunasLivro(String nomeColuna, int ordem) {
//		Coluna coluna = new Coluna(nomeColuna, "String", ordem);
//		colunasRelacaoLivro.add(coluna);
//	}
	
	private void criaColunas(String nomeColuna, int ordem, String nomeRelacao)
	{
		if(colunas.get(nomeRelacao) == null){
			List<Coluna> l = new LinkedList<>(); 
			colunas.put(nomeRelacao, l);
		}
		if(nomeRelacao.equals("Autor"))
			System.out.println("incrementa");
		colunas.get(nomeRelacao).add(new Coluna(nomeColuna, "String", ordem));
			
	}
	
	private void criaItemCatalogo(String nomeRelacao, String nomeArquivo, List<Coluna> colunas, int numeroLinhas){
		if(itens == null){
			itens = new LinkedList<>();
		}
		ItemCatalogo i = new ItemCatalogo(nomeRelacao, nomeArquivo, colunas, numeroLinhas);
		itens.add(i);
	}
//	private void criaColunasCategoria(String nomeColuna, int ordem) {
//		Coluna coluna = new Coluna(nomeColuna, "String", ordem);
//		colunasRelacaoCategoria.add(coluna);
//	}
//	
//	private void criaColunasAutor(String nomeColuna, int ordem) {
//		criaColunasAutor(nomeColuna, ordem, false);
//	}
	
	private void criaColunasAutor(String nomeColuna, int ordem, boolean valoresUnicos) {
		Coluna coluna = new Coluna(nomeColuna, "String", ordem, null, valoresUnicos);
		colunasRelacaoAutor.add(coluna);
	}
	
	private TabelaDispersao getHashTable(String nomeTabela, String campoRelacao){
		if(tabelasDispersao == null){
			tabelasDispersao = new TabelaDispersao<>(3);
		}
		
		if(tabelasDispersao.recupera(nomeTabela)==null){
			TabelaDispersao<String, Tupla> t = new TabelaDispersao<>();
			Relacao r = new Relacao(nomeTabela);
			r.open();
			Tupla tupla;
			while ((tupla = (Tupla) r.next()) != null) {
				String valor = tupla.getValorCampo(campoRelacao);
				t.adiciona(valor, tupla);
			}
			tabelasDispersao.adiciona(nomeTabela, t);
			return t;
		}else{
			return tabelasDispersao.recupera(nomeTabela);
		}
		
	}

	
	public static Catalogo getInstancia(){
		if(instance == null)
			instance = new Catalogo();
		return instance;
	}
//---------------------------------------------------

	public String recuperaNomeArquivo(String nomeRelacao) {
		for (ItemCatalogo item : itens) {
			if(item.comparaNomeRelacao(nomeRelacao))
				return item.getNomeArquivo();
		}
		return null;
	}

	public List<Coluna> recuperaColunas(String nomeRelacao) {
		for (ItemCatalogo item : itens) {
			if(item.comparaNomeRelacao(nomeRelacao)){
				return item.getColunas();

			}
		}
		return null;
	}

	public int recuperaNumeroLinhas(String nomeRelacao) {
		for (ItemCatalogo item : itens) {
			if(item.comparaNomeRelacao(nomeRelacao))
				return item.numeroLinhas();
		}
		return -1;
	}
	
	public int recuperaNumeroEstimadoDeLinhas(String nomeRelacao, String campo, String valorBuscado) {
		for (ItemCatalogo item : itens) {
			if(item.comparaNomeRelacao(nomeRelacao))
				return item.numeroEstimadoLinhas(campo, valorBuscado);
		}
		return -1;
	}


	public boolean campoComValorUnico(String nomeRelacao, String nomeCampo) {
		for (ItemCatalogo item : itens) {
			if(item.comparaNomeRelacao(nomeRelacao))
				return item.campoComValorUnico(nomeCampo);
		}
		return false;
	}


	
}
