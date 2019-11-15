package interfaces_partilhadas;

import classes_partilhadas.TipoDeMensagem;

public interface Message_Interface<E> {
	
	public E getContent();
	
	public TipoDeMensagem getTipo();

}
