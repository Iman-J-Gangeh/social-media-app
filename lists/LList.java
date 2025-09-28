package lists;


public class LList<T> implements ListInterface<T>{

	private Node<T> head; 
	private int numberOfEntries; 
	
	public LList() {
		initializeDataFields();
	}
	
	
	@Override
	public void add(T newEntry) { 
		Node<T> newNode = new Node<T>(newEntry);
		if(isEmpty()) {
			head = newNode;
		}
		else {
			Node<T> ptr = getNodeAt(numberOfEntries); 
			ptr.setNext(newNode);
		}
		++numberOfEntries; 
	}

	@Override
	public void add(int newPosition, T newEntry) {
		if(newPosition >= 1 && newPosition <= numberOfEntries + 1) {
			Node<T> newNode = new Node<T>(newEntry);
			Node<T> ptr; 
			if(newPosition == 1) {
				newNode.setNext(head);
				head = newNode; 				
			}
			else if(newPosition == numberOfEntries + 1) {
				ptr = getNodeAt(newPosition - 1); 
				ptr.setNext(newNode);
			}
			else {
				ptr = getNodeAt(newPosition - 1); 
				newNode.setNext(ptr.getNext());
				ptr.setNext(newNode);
			}
			++numberOfEntries;
		}
		else 
			throw new IndexOutOfBoundsException(
					"Illegal position passed to add operation.");
	}

	@Override
	public T remove(int givenPosition) {
		if(givenPosition >= 1 && givenPosition <= numberOfEntries) {
			T removed; 
			if(givenPosition == 1) {
				removed = head.getData();
				head = head.getNext();
			}
			else {
				Node<T> ptr = getNodeAt(givenPosition - 1);
				removed = ptr.getNext().getData();
				ptr.setNext(ptr.getNext().getNext());
			}
			--numberOfEntries;
			return removed; 
		}
		else 
			throw new IndexOutOfBoundsException(
					"Illegal position given to remove operation.");
	}

	@Override
	public void clear() {
		initializeDataFields();		
	}

	@Override
	public T replace(int givenPosition, T newEntry) {
		if(givenPosition >= 1 && givenPosition <= numberOfEntries) {
			Node<T> newNode = new Node<T>(newEntry);
			Node<T> replaced; 
			if(givenPosition == 1) {
				replaced = head; 
				newNode.setNext(head.getNext());
				head = newNode;
			}
			else {
				Node<T> before = getNodeAt(givenPosition - 1);
				replaced = before.getNext();
				newNode.setNext(replaced.getNext());
				before.setNext(newNode);
			}
			return replaced.getData();
			
		}
		else 
			throw new IndexOutOfBoundsException(
					"Illegal position passed to replace operation.");
	}

	@Override
	public T getEntry(int givenPosition) {
		if(givenPosition >= 1 && givenPosition <= numberOfEntries) {
			return getNodeAt(givenPosition).getData();
		}
		else 
			throw new IndexOutOfBoundsException(
					"Illegal Position passed to getEntry operation.");
	}

	@Override
	public boolean contains(T anEntry) {
		Node<T> ptr = head; 
		boolean contains = false; 
		for(int i = 1; i <= numberOfEntries; ++i) {
			if(ptr.getData().equals(anEntry)) {
				contains = true; 
				break;
			}
			ptr = ptr.getNext();
		}
		return contains; 
	}

	@Override
	public int getLength() {
		return numberOfEntries; 
	}

	@Override
	public boolean isEmpty() {
		return numberOfEntries == 0; 
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[numberOfEntries];
		Node<T> ptr = head; 
		for(int i = 0; i < numberOfEntries; ++i) {
			if(ptr == null)
				break;
			array[i] = ptr.getData();
			ptr = ptr.getNext();
		}
		return array;
	}
	
	//Helper Functions 
	
	// Initializes the class's data fields to indicate an empty list.
	private void initializeDataFields() {
		head = null;
		numberOfEntries = 0; 
	}
	
	// Returns a reference to the node at a given position.
	// Precondition: The chain is not empty;
	// 1 <= givenPosition <= numberOfEntries.
	private Node<T> getNodeAt(int givenPosition) {
		assert !isEmpty() && (givenPosition >= 1) && (givenPosition <= numberOfEntries);
			
	
		Node<T> ptr = head; 
		for(int i = 1; i < givenPosition; ++i) {
			ptr = ptr.getNext();			
		}		
		return ptr;
	}
	
	private class Node<T> {
		private T data; 
		private Node<T> next; 
		
		Node(T data) {
			this.data = data; 
		}
		
		Node(T data, Node<T> next) {
			this.data = data; 
			this.next = next; 
		}
		
		//Getters
		private T getData() { return data; }
		private Node<T> getNext() { return next; }
		
		// Setters
		private void setData(T data) { this.data = data; }
		private void setNext(Node<T> next) {this.next = next; }
	}


}
