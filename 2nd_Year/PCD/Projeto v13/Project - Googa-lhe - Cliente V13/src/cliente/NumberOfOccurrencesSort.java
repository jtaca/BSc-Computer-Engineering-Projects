package cliente;

import java.util.Comparator;

import classes_partilhadas.TituloDeNoticia;

public class NumberOfOccurrencesSort implements Comparator<TituloDeNoticia> {

	@Override
	public int compare(TituloDeNoticia arg0, TituloDeNoticia arg1) {
		Integer arg0number = arg0.getNumeroDeOcorrencias();
		Integer arg1number = arg1.getNumeroDeOcorrencias();
		
		return arg1number.compareTo(arg0number);
	}

}
