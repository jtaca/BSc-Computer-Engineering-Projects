package testes;

public class Caixa <E>{
	private E elemento;
	
	@Override
	public String toString() {
		return "Caixa [elemento=" + elemento + "]";
	}


	public void setElemento(E elemento) {
						this.elemento = elemento;
	}


	public Caixa(E elemento){
		this.elemento = elemento;
	}

	public E getElemento() {
		return elemento;
	}

		
}
