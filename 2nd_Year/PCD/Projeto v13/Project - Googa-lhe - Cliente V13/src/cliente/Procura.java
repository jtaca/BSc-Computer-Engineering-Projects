package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;
import cliente_interfaces.Search_Interface;

public class Procura implements Search_Interface {
	
	private List<Noticia> noticias;
//	private Cliente cliente;
	
	private static final String folderName = "resources";
	
	public Procura() {
		this.noticias = new ArrayList<Noticia>();
//		this.cliente = cliente;
		loadNewsToList();
	}

	private void loadNewsToList() {
		List<String> strlist = new ArrayList<String>();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("./" + folderName);
		InputStreamReader in = new InputStreamReader(is);
		try(BufferedReader brdir = new BufferedReader(in);) {
		String fileStr = "";
		while((fileStr = brdir.readLine()) != null) {
			if(fileStr.endsWith(".txt")) {
				strlist.add(fileStr);
			}
		}
		in.close();
		is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String titulo = "";
		for(String fileS : strlist) {
			InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(folderName + "/" + fileS);
			try {
				InputStreamReader fileInput = new InputStreamReader(fileStream, "UTF-8");
				StringBuilder contentInFile = new StringBuilder();
				try(BufferedReader brFile = new BufferedReader(fileInput)) {
					String fileContent = brFile.readLine();
					if(fileContent != null) {
						System.out.println(fileContent);
						titulo = fileContent;
					}
					while(((fileContent = brFile.readLine()) != null)) {
						contentInFile.append(fileContent + "\n");
					}
					noticias.add(new Noticia(titulo, contentInFile.toString()));
					fileInput.close();
					fileStream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Noticia getNoticia(TituloDeNoticia noticia) {
		String titulo = noticia.getTitulo();
		Noticia content = null;
		for(int i = 0 ; i < noticias.size() && content == null ; i++) {
			if(noticias.get(i).getTitle().equals(titulo)) {
				content = noticias.get(i);
			}
		}
		
		return content;
		
	}
	
	public static void sortByOcurrencies(List<TituloDeNoticia> titulos) {
		titulos.sort(new NumberOfOccurrencesSort());
	}
	
	public static TituloDeNoticia[] turnListIntoArray(List<TituloDeNoticia> titulos) {
		TituloDeNoticia[] t = new TituloDeNoticia[titulos.size()];
		int i = 0;
		for(TituloDeNoticia titulo : titulos) {
			t[i] = titulo;
			i++;
		}
		return t;
	}
	
	
	@Override
	public TituloDeNoticia[] makeSearchOfWord(String word) {
		word = word.toLowerCase();
		List<TituloDeNoticia> titulos = new ArrayList<TituloDeNoticia>();
		for(Noticia noticia : noticias) {
			ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
			String parts = noticia.getTitleAndText().toLowerCase();
			int n = parts.indexOf(word, 0);
			while(n != -1) {
				ocurrencias.add(n);
				n = parts.indexOf(word, n + 1);
			}
			if(ocurrencias.size() != 0) {
				titulos.add(new TituloDeNoticia(noticia.getTitle(), ocurrencias));
			}
		}
		sortByOcurrencies(titulos);
		return turnListIntoArray(titulos);
	}

}
