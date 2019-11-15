package sets;

import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
		Set<Integer> set = new TreeSet<Integer>();
		Integer i = 1;
		set.add(i);
		set.add(i);
		set.add(new Integer(1));
		System.out.println(set);
		set.add(new Integer(2));
		System.out.println(set);
	}
}