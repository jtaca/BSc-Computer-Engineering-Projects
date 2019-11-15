package cliente_interfaces;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;

public interface Search_Interface {
	
	public Noticia getNoticia(TituloDeNoticia noticia);
	
	public TituloDeNoticia[] makeSearchOfWord(String word);
	
}
