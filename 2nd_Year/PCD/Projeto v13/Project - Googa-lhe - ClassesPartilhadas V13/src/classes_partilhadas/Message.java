package classes_partilhadas;

import java.io.Serializable;

import interfaces_partilhadas.Message_Interface;

public class Message<E> implements Message_Interface, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1074969425861350879L;
	
	private TipoDeMensagem tipo;
	private E content;
	
	public Message(TipoDeMensagem tipo, E content) {
		this.tipo = tipo;
		this.content = content;
	}
	
	@Override
	public E getContent() {
		return content;
	}
	
	@Override
	public TipoDeMensagem getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return tipo + ": " + content;
	}
	
}
