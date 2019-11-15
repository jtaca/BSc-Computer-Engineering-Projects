package list;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
	  public static void main(String[] args) {
	List<Integer> arrayList = new ArrayList<Integer>();
	List<Integer> linkedList = new LinkedList<Integer>();
	System.out.println("Test time Array List: " + testTime(arrayList));
	System.out.println( "Test time Linked List: " +
	testTime(linkedList));
	  }
	private static long testTime(List<Integer> list) {
	long startTime = System.currentTimeMillis();
	// Adição de elementos ao início da lista
	list.add(0);
	for (int i = 0; i != 100000000; i++)
	list.add(0,i);
	return System.currentTimeMillis() - startTime;
	}
}

