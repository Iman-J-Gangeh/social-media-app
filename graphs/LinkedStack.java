package graphs;

public class LinkedStack<T> implements StackInterface<T> {

	Node topNode; 
	
	public LinkedStack() {
		topNode = null; 
	}
	
	@Override
	public void push(T newEntry) {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			topNode = new Node(newEntry);
		}
		else {
			Node newNode = new Node(newEntry, topNode);
			topNode = newNode; 
		}
	}

	@Override
	public T pop() {
		if(!isEmpty()) {
			T removed; 
			removed = topNode.getData();
			topNode = topNode.getNextNode();
			return removed;
		}
		else 
			throw new EmptyStackException();
	}

	@Override
	public T peek() {
		if(!isEmpty()) 
			return topNode.getData();
		else 
			throw new EmptyStackException();
	}

	@Override
	public boolean isEmpty() {
		return topNode == null; 
	}

	@Override
	public void clear() {
		topNode = null;
	}
	
	private class Node {

        private T data; // entry in bag
        private Node next; // link to next node

        private Node(T dataPortion) {
            this(dataPortion, null);
        } // end constructor

        private Node(T dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        } // end constructor

        private T getData() {
            return data;
        } // end getData

        private void setData(T newData) {
            data = newData;
        } // end setData

        private Node getNextNode() {
            return next;
        } // end getNextNode

        private void setNextNode(Node nextNode) {
            next = nextNode;
        } // end setNextNode
    } // end Node

}
