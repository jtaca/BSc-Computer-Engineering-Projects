package cliente_interfaces;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;

public interface GUI_Interface {
	
	public void writeOnTextArea(Noticia content);
	
	public void refresh(TituloDeNoticia[] titles);
	
	public void open();
	
}
