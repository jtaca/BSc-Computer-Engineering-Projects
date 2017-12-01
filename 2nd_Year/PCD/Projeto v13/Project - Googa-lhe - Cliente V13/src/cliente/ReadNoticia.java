package cliente;

import java.util.List;

import classes_partilhadas.Noticia;

public class ReadNoticia extends Thread {
	
	private String titulo;
	private List<Noticia> noticias;
	private Cliente client;
	
	public ReadNoticia(String titulo, List<Noticia> noticias, Cliente client) {
		this.titulo = titulo;
		this.noticias = noticias;
		this.client = client;
	}
	
	@Override
	public void run() {
		Noticia content = null;
		for(int i = 0 ; i < noticias.size() && content == null ; i++) {
			if(noticias.get(i).getTitle().equals(titulo)) {
				content = noticias.get(i);
			}
		}
		if(content != null) {
//			client.writeNewsOnTextArea(content);
		}
	}
	
}
