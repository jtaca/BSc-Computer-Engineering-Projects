package classes_partilhadas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TituloDeNoticia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2622037988072994588L;
	
	private String titulo;
	private List<Integer> numeroDeOcorrencias;
	
	public TituloDeNoticia(String titulo) {
		this(titulo, new ArrayList<Integer>());
	}
	
	public TituloDeNoticia(String titulo, List<Integer> numeroDeOcorrencias) {
		this.titulo = titulo;
		this.numeroDeOcorrencias = numeroDeOcorrencias;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public List<Integer> getOcorrencias() {
		return numeroDeOcorrencias;
	}
	
	public int getNumeroDeOcorrencias() {
		return numeroDeOcorrencias.size();
	}
	
	@Override
	public String toString() {
		if(getNumeroDeOcorrencias() == 0)
			return getTitulo();
		else
			return getNumeroDeOcorrencias() + " - " + titulo;
	}
	
}
