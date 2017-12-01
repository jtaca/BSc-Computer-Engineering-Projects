package classes_partilhadas;

import java.io.Serializable;

public class Noticia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1336865104274157755L;
	
	private String title;
	private String text;
	
	public Noticia(String titulo, String text) {
		this.title = titulo;
		this.text = text;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public String getTitleAndText() {
		return title + "\n" + text;
	}
	
	@Override
	public String toString() {
		return "<html><b>" + title + "</b><br><br>" + text + "</html>";
	}

}
