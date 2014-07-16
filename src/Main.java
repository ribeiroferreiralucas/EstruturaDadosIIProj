import java.beans.Encoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import br.ufrrj.im.cc.ed2.join.base.Tupla;
import br.ufrrj.im.cc.ed2.juncao.HashJoin;
import br.ufrrj.im.cc.ed2.juncao.NestedLoop;
import br.ufrrj.im.cc.ed2.selecao.Selecao;


public class Main {
	
	public static void testaSelecao(){
		long tempo = System.currentTimeMillis();
		Selecao select = new Selecao("Cliente", "telefone", "=", "3399-1609");
		select.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) select.next()) != null) {
			System.out.println("["+tupla.getValorCampo("endereco")+"] : "+tupla.getValorCampo("telefone"));
			i++;
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	}
	
	public static void testaHashJoin(){
		long tempo = System.currentTimeMillis();
		HashJoin relation = new HashJoin("Cliente", "telefone", "Pedido", "telefone");
		relation.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) relation.next()) != null) {
			System.out.println("["+tupla.getValorCampo("nome") +" : "+ tupla.getValorCampo("telefone")+" : "+ tupla.getValorCampo("nome_pizza"));
			i++;
		}
		relation.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	}
	
	public static void testaSelectXJoin(){
		long tempo = System.currentTimeMillis();
		Selecao select = new Selecao("Cliente", "telefone", "=", "97689-1564");
		select.open();
		Tupla tupla;
		while ((tupla = (Tupla) select.next()) != null) {
			NestedLoop relation = new NestedLoop(tupla, "telefone", "Pedido", "telefone");
			relation.open();
			while ( (tupla = (Tupla) relation.next()) != null){
				System.out.println(tupla.getValorCampo("nome")+" : "+tupla.getValorCampo("nome_pizza"));
			}
			relation.close();
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
			
	}

	
	public static void testNestedLoop(){
		long tempo = System.currentTimeMillis();
		NestedLoop relation = new NestedLoop("Cliente", "telefone", "Pedido", "telefone");
		relation.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) relation.next()) != null  & i<3) {
			System.out.println("["+tupla.getValorCampo("nome")+"] : "+tupla.getValorCampo("nome_pizza"));
			i++;
		}
		relation.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	}
	
	
	public static void busca2(String telefone) throws ParseException{
		long tempo = System.currentTimeMillis();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		
		SortedMap<Date, Tupla> ordened = new TreeMap<Date, Tupla>();
		
		Selecao select = new Selecao("Cliente", "telefone", "=", telefone);
		select.open();
		Tupla tupla;
		while ((tupla = (Tupla) select.next()) != null) {
			NestedLoop relation = new NestedLoop(tupla, "telefone", "Pedido", "telefone");
			relation.open();
			while ( (tupla = (Tupla) relation.next()) != null){
				Date date = formatter.parse(tupla.getValorCampo("data"));
				ordened.put(date, tupla);
			}
			relation.close();
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		imprime(ordened);
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
			
	}
	public static void imprime(Map<Date, Tupla> map){
		Collection<Tupla> col = map.values();
		
		for (Tupla tupla : col) {
			System.out.println(tupla.getValorCampo("data")+ " : "+tupla.getValorCampo("nome_pizza"));
		}
	}
	public static void questao5(String ingredientes){
		long tempo = System.currentTimeMillis();
		Selecao select = new Selecao("Cardapio", "ingredientes", "like", ingredientes);
		select.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) select.next()) != null) {
			System.out.println("["+tupla.getValorCampo("nome_pizza")+"] : "+tupla.getValorCampo("ingredientes"));
			i++;
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	
	}

	public static void questao1(String tel){
		long tempo = System.currentTimeMillis();
		Selecao select = new Selecao("Cliente", "telefone", "=", tel);
		select.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) select.next()) != null) {
			System.out.println("["+tupla.getValorCampo("nome")+"] : "+tupla.getValorCampo("endereco"));
			i++;
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	
	}
	
	static public void questao3(String date){
		long tempo = System.currentTimeMillis();
		Selecao select = new Selecao("Pedido", "data", "=", date);
		select.open();
		Tupla tupla;
		int i = 0;
		while ((tupla = (Tupla) select.next()) != null) {
			NestedLoop relation = new NestedLoop(tupla, "telefone", "Cliente", "telefone");
			relation.open();
			while ( (tupla = (Tupla) relation.next()) != null){
				System.out.println(tupla.getValorCampo("data")+" : "+tupla.getValorCampo("nome")+" : "+tupla.getValorCampo("telefone"));
			}
			relation.close();
		}
		select.close();
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Junção com Tabela de Dispersão processada em "+tempo+"ms");
	
	}
	
	public static void main(String[] args) {
//		testaHashJoin();
//		testNestedLoop();
//		testaSelecao();
//		testaSelectXJoin();
//		questao5("atum");
		questao3("14/3/2014");
//		questao1("3393-1609");
//		try {
//			busca2("97689-1564");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
