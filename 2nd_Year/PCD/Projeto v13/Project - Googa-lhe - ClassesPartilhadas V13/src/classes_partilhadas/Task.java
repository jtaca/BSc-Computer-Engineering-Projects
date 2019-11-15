package classes_partilhadas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import classes_partilhadas.Message;
import classes_partilhadas.Noticia;
import classes_partilhadas.TipoDeMensagem;
import classes_partilhadas.TituloDeNoticia;

public class Task implements Runnable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3411427212746674486L;
	
	private int id;
	private String word;
	private Noticia noticia;
	private TituloDeNoticia result;
	
	public Task(int id, String word, Noticia noticia) {
		this.id = id;
		this.word = word;
		this.noticia = noticia;
	}
	
	@Override
	public void run() {
		word = word.toLowerCase();
//		List<TituloDeNoticia> titulos = new ArrayList<TituloDeNoticia>();
//		for(Noticia noticia : noticias) {
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		String parts = noticia.getTitleAndText().toLowerCase();
		int n = parts.indexOf(word, 0);
		while(n != -1) {
			ocurrencias.add(n);
			n = parts.indexOf(word, n + 1);
		}
		TituloDeNoticia titulo = new TituloDeNoticia(noticia.getTitle(), ocurrencias);
		addResult(titulo);
//			new TituloDeNoticia(noticia.getTitle(), ocurrencias));
//		sortByOcurrencies(titulos);
//		return turnListIntoArray(titulos);
//		TituloDeNoticia titulo = new TituloDeNoticia();
	}

	private void addResult(TituloDeNoticia message) {
		result = message;
//		DealWithConnection connection = Server.getInstance().getConnectionWithID(id);
//		System.out.println(id+ " Aqui" + connection);
//		if(connection != null) {
//			ResultsList resultsList = connection.getResultsListOfWord(word);
//			System.out.println(id+ " Aqui1" + resultsList);
//			if(resultsList != null) {
//				resultsList.addOcurrence(message);
////				System.out.println(id + " Aqui2" + ocurrence);
//			}
//		}
//		Server.getInstance().getServerGUI().removeTask();
	}
	
	public int getID() {
		return id;
	}
	
	public String getWord() {
		return word;
	}
	
	public TituloDeNoticia getResult() {
		return result;
	}
	

}
