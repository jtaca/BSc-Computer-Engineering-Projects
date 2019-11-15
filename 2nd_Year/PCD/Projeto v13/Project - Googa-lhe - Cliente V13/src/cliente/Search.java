package cliente;

import java.util.ArrayList;
import java.util.List;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;

public class Search extends Thread {
	
	private Cliente client;
	private String word;
	private List<Noticia> noticias;
	
	public Search(String word, List<Noticia> noticias, Cliente client) {
		this.word = word.toLowerCase(); // Passa-mos para lowercase para poder encontrar todas as palavras, independentemente se comecam com letra maiuscula
		this.noticias = noticias;
		this.client = client; 
	}
	
	@Override
	public void run() {
		ArrayList<TituloDeNoticia> titulos = new ArrayList<TituloDeNoticia>();
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		for(Noticia noticia : noticias) {
			ocurrencias.clear();
			String[] parts = noticia.getTitleAndText().split(" ");
			for(int i = 0 ; i < parts.length ; i++) {
				if(parts[i].toLowerCase().contains(word)) { // Passa-mos para lowercase para poder encontrar todas as palavras, independentemente se comecam com letra maiuscula
					ocurrencias.add(i);
				}
			}
			if(ocurrencias.size() != 0) {
				titulos.add(new TituloDeNoticia(noticia.getTitle(), ocurrencias));
			}
		}
//		client.addNewsListToRefresh(titulos);
	}
	
	
}
