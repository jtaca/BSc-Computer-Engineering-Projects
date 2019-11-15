package TiposDeDados;

public class DLIPriorityQueue {

	private static class Node {
		private Comparable item;
		private Node next;
	}
	
	private Node first;
	
	public void clear() {
		first = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public void insert(Comparable item) {
		Node previous = null;
		Node aux = first;
		while(aux != null && item.compareTo(aux.item) >= 0) {
			previous = aux;
			aux = aux.next;
		}
		
		Node newNode = new Node();
		newNode.item = item;
		newNode.next = aux;
		if(previous == null) {
			first = newNode;
		} else {
			previous.next = newNode;
				
		}
	}
	
	public Comparable peek() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");
			
		return first.item;
	}
	
	public void poll() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");

		first = first.next;
	} 

}