package TiposDeDados;

public class SLAPriorityQueue {
	
	private static int MAX = 100;

	private Comparable[] items = new Comparable[100];
	
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
		items[indexOFHPI] = item;
	}
	
	public Comparable peek() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");

		Comparable hpi = items[0];
		for(int i = 1; i != indexOFHPI + 1; i++) {
			if(items[i].compareTo(hpi) < 0) {
				hpi = items[i];
			}
		}
		return hpi;
	}
	
	public void poll() {
		if(isEmpty())
			throw new IllegalStateException("Fila prioritária vazia!");

		int ihpi = 0;
		for(int i = 1; i != indexOFHPI + 1; i++) {
			if(items[i].compareTo(items[ihpi]) < 0) {
				ihpi = i;
			}
		}
		
		for(int i = ihpi; i != indexOFHPI; i++) {
			items[i] = items[i + 1];
		}
		
		--indexOFHPI;
	} 

}