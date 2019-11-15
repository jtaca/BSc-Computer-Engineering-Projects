package TiposDeDados;

public class SLIPriorityQueue {
	
	private static int MAX = 100;

	private Comparable[] items = new Comparable[MAX];
	
	private int indexOFHPI = -1;
	
	public void clear() {
		indexOFHPI = -1;
	}
	
	public boolean isEmpty() {
		return indexOFHPI == -1;
	}
	
	public void insert(Comparable item) {
		if(indexOFHPI == MAX - 1)
			throw new IllegalStateException("Fila prioritária cheia!");
		
		++indexOFHPI;
		
		int i = indexOFHPI - 1;
		while(i != -1 && item.compareTo(items[i]) >= 0) {
			items[i + 1] = items[i];
			--i;
		}
		
		items[i + 1] = item;
	}
	
	public Comparable peek() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");
			
		return items[indexOFHPI];
	}
	
	public void poll() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");

		--indexOFHPI;
	} 

}
