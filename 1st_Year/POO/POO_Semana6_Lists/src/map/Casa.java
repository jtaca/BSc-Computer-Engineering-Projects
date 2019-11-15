package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Casa {
	private Map<String, List<Eletrodomestico>> divisoes;

	public Casa() {
		this.divisoes = new HashMap<String,List<Eletrodomestico>>();
	}
	
	@Override
	public String toString() {
		return "Casa [divisoes=" + divisoes + "]";
	}

	public void addEletrodomestico(String nomeDivisao,Eletrodomestico e){
		if(this.divisoes.containsKey(nomeDivisao)){
			this.divisoes.get(nomeDivisao).add(e);
			
		}else{
			ArrayList<Eletrodomestico>lst =new ArrayList<>();
			lst.add(e);
			
			this.divisoes.put(nomeDivisao,lst);
		}
		
	}
	//ERRADO!!!!!
//	public void removeAll(String marca,String nomeDivisao){
//
//		if(divisoes.containsKey(nomeDivisao)){
//			for(Eletrodomestico e : this.divisoes.get(nomeDivisao)){
//				if(e.getMarca().contains(marca)){
//					this.divisoes.get(nomeDivisao).remove(e);
//					
//				}
//			}
//		}
//		
//	}
	//Com RemoveAll
	public void removeAll(String marca,String nomeDivisao){
		List<Eletrodomestico> lstAux =new ArrayList<>();
		
		if(divisoes.containsKey(nomeDivisao)){
			for(Eletrodomestico e : this.divisoes.get(nomeDivisao)){
				if(e.getMarca().contains(marca)){
					lstAux.add(e);
					
				}
			}
			this.divisoes.get(nomeDivisao).removeAll(lstAux);
		}
	}
	//Com Iterator
	public void removeAll1(String marca,String nomeDivisao){
		
		
		if(divisoes.containsKey(nomeDivisao)){
			Iterator<Eletrodomestico> it =this.divisoes.get(nomeDivisao).iterator();
			//for(Eletrodomestico e : this.divisoes.get(nomeDivisao)){
			while(it.hasNext()){
				Eletrodomestico e =it.next();
				if(e.getMarca().contains(marca)){
					it.remove();
					
				}
			}
			//this.divisoes.get(nomeDivisao).removeAll(iterator);
		}
	}
	
public void removeAll2(String marca,String nomeDivisao){
	
	this.divisoes.get(nomeDivisao).removeIf(new PredicateBosch());
	}
	
	

}
