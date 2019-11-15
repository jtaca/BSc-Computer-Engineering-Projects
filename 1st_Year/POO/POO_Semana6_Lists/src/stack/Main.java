package stack;

import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		Integer i = 1;
		stack.add(i);
		stack.pop();
		stack.push(i+1);
		stack.peek();
		stack.add(new Integer(1));
		System.out.println(stack);
		stack.add(new Integer(2));
		System.out.println(stack);
	}
}
