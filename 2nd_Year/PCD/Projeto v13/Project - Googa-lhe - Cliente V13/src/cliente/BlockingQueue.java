package cliente;


import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	
	private Queue<T> queue = new LinkedList<T>();
//	private Queue<T> queue = new ArrayDeque<T>(); // tambem funcionava (nao testado, mas esta no enunciado)
	private int limite;

	public BlockingQueue() {
		limite = -1;
	}
	
	public BlockingQueue(int limite) {
		this.limite = limite;
	}
	
	public synchronized void offer(T obj) throws InterruptedException {
		while(limite != -1 && queue.size() >= limite) {
			wait();
		}
		queue.add(obj);
		notifyAll();
	}
	
	public synchronized T poll() throws InterruptedException {
		while(queue.size() == 0) {
			wait();
		}
		notifyAll();
		return queue.poll();
	}
	
	public int size() {
		return queue.size();
	}
	
	public synchronized void clear() {
		queue.clear();
	}

}
