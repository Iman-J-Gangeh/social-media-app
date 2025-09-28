package graphs;

public class LinkedQueue<T> implements QueueInterface<T> {
	
	Node head; 
	Node tail; 
	
	@Override	
	public void enqueue(T newEntry) {
		Node newNode = new Node(newEntry);
		if(isEmpty()) {
			head = newNode;
		}
		else if(tail == null) {
			tail = newNode;
			head.setNext(tail);
		}
		else {
			tail.setNext(newNode);
			tail = tail.getNext();
		}
	}

	@Override
	public T dequeue() {
		if(!isEmpty()) {
			T removed = head.getData();
			head = head.getNext();
			return removed;
		}
		else 
			throw new EmptyQueueException();
	}

	@Override
	public T getFront() {
		if(!isEmpty()) {
			return head.getData();
		}
		else 
			throw new EmptyQueueException();
	}

	@Override
	public boolean isEmpty() {
		return head == null; 
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		head = null;
		tail = null;
	}
	
	private class Node {
		
		private T data; 
		private Node next; 
		
		Node(T data) {
			this(data, null); 
		} 
		
		Node(T data, Node next) {
			this.data = data; 
			this.next = next;
		}
		
		private Node getNext() { return next; }
		private void setNext(Node next) { this.next = next; }
		
		private T getData() { return data; }
		private void setData(T data) { this.data = data; }		
	}

}
