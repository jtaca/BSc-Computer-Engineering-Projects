package TiposDeDados;

public class DLAPriorityQueue {

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
		Node newNode = new Node();
		newNode.item = item;
		newNode.next = first;
		first = newNode;
	}
	
	public Comparable peek() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritaria vazia!");
		
		Comparable highestPriorityItem = first.item;
		
		Node aux = first.next;
		while(aux != null) {
			if(aux.item.compareTo(highestPriorityItem) < 0) {
				highestPriorityItem = aux.item;
			}
			aux = aux.next;
		}
			
		return highestPriorityItem;
	}
	
	public void poll() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");

		Node previousHPI = null;
		Node currentHPI = first;
		
		Node previous = first;
		Node aux = first.next;
		while(aux != null) {
			if(aux.item.compareTo(currentHPI.item) < 0) {
				previousHPI = previous;
				currentHPI = aux;
			}
			previous = aux;
			aux = aux.next;
		}
		
		if(previousHPI == null) {
			first = currentHPI.next;
		} else {
			previousHPI.next = currentHPI.next;
		}
		
	} 

}