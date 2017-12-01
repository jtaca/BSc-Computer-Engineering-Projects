package cliente;

import java.util.Comparator;

import classes_partilhadas.TituloDeNoticia;

public class AlphabeticSort implements Comparator<TituloDeNoticia> {
	
	@Override
	public int compare(TituloDeNoticia arg0, TituloDeNoticia arg1) {
		Integer arg1number = arg1.getNumeroDeOcorrencias();
		Integer arg0number = arg0.getNumeroDeOcorrencias();
		String arg1title = arg1.getTitulo();
		String arg0title = arg0.getTitulo();		
		int result = 0;
		int i = 1;
		if(arg1number.compareTo(arg0number) == 0) {
			while(arg1.getTitulo().toCharArray()[0] == ' ' && i != arg1.getTitulo().toCharArray().length) {
				arg1title = arg1.getTitulo().substring(i);
				i++;
			}
			i = 1;
			while(arg0.getTitulo().toCharArray()[0] == ' ' && i != arg0.getTitulo().toCharArray().length) {
				arg0title = arg0.getTitulo().substring(i);
				i++;
			}
			
			
			result = arg0title.compareTo(arg1title);
		}else {
			result = arg1number.compareTo(arg0number);
		}
		
		return result;
	}
	
	

}
