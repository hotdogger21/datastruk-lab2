import java.util.*;

// A priority queue.
public class PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	public void add(E x)
	{
		assert invariant() : showHeap();
		heap.add(x);
		siftUp(heap.indexOf(x));
		assert invariant() : showHeap();
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {
		assert invariant() : showHeap();
		if (size() == 0)
			throw new NoSuchElementException();

		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		if (heap.size() > 0) siftDown(0);
		assert invariant() : showHeap();
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {
		assert invariant() : showHeap();
		E value = heap.get(index);

		// do this until we reach the root
		while (parent(index) >= 0){
			// get value of parent
			E parentvalue = heap.get(parent(index));
			// get index of parent
			int parentindex = parent(index);

			// if parent is less than value replace node with parent then move one node up
			if (comparator.compare(value,parentvalue) < 0 && index != 0){
				heap.set(index, parentvalue);
				index = parentindex;
				//caca
			}
			// break if parent is greater than out value
			else break;

		}
		//replace node that we stopped on with our value
		heap.set(index, value);
		assert invariant() : showHeap();
	}
     
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
		assert invariant() : showHeap();
		E value = heap.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {
				heap.set(index, childValue);
				index = child;
			} else break;
		}

		heap.set(index, value);
		assert invariant() : showHeap();
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}

	public void update(E oldelem, E newelem){
		assert invariant() : showHeap();
		int index = heap.indexOf(oldelem);
		heap.set(index, newelem);
		if (heap.size() > 1){
			if (comparator.compare(newelem, heap.get(parent(index))) < 0){
				siftUp(index);
			}
			else if(comparator.compare(newelem, heap.get(leftChild(index))) > 0 || comparator.compare(newelem, heap.get(rightChild(index))) > 0){
				siftDown(index);
			}
		}
		assert invariant() : showHeap();
	}


	private boolean invariant() {
		// TODO: return true if and only if the heap invariant is true.
		if(heap.size() <= 1){
			return true;
		}

		for(int i = 0; i < (heap.size()/2) - 2; i++){
			if(comparator.compare(heap.get(i), heap.get(leftChild(i))) == -1){
				return false;
			}
			if(comparator.compare(heap.get(i), heap.get(rightChild(i))) == -1){
				return false;
			}
		}
		return true;
	}
	private String showHeap() {
		// TODO: return description of heap contents.
		return "showheap";
	}

}
