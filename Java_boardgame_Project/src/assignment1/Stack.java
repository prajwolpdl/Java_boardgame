package assignment1;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
	
	private List<T> container;
	private int top;
	
	public Stack() {
		container = new ArrayList<T>();
		top = -1;
	}
	
	public void push(T element) {
		container.add(element);
		top += 1;
	}
	
	public T pop() {
		T element =  container.get(top);
		top -= 1;
		return element;
	}
	
	public boolean isEmpty() {
		return top == -1;
	}

}
