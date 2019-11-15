package cliente_interfaces;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;

public interface Client_Interface {
	
	public void addNewsListToRefresh(TituloDeNoticia[] TitlesOfNews);
	
	public void requestNews(String title);
	
	public void requestSearchOfWord(String word);
	
	public void writeNewsOnTextArea(Noticia content);
	
}
