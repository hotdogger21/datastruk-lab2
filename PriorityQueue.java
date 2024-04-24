import java.util.*;

// A priority queue.
public class PriorityQueue<E> {
	//(You may assume that all comparisons and hash table operations take O(1) time.)
	private ArrayList<E> heap = new ArrayList<E>();
	private Map<E, Integer> indexmap = new HashMap<>();
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
		siftUp(heap.size()-1);
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
		indexmap.remove(heap.size()-1);

		if (heap.size() > 0) siftDown(0);
		assert invariant() : showHeap();
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {
		//complexity O(log n)
		E value = heap.get(index);

		// do this until we reach the root
		while (index > 0){
			// get value of parent
			// we can do this max log 2 n times
			E parentValue = heap.get(parent(index));
			// get index of parent
			int parentIndex = parent(index);

			// if parent is less than value replace node with parent then move one node up
			if (comparator.compare(value,parentValue) >= 0){
				break;
			}
			// break if parent is greater than out value
            heap.set(index, parentValue);
			indexmap.put(heap.get(index), index);
            index = parentIndex;

		}
		//replace node that we stopped on with our value
		heap.set(index, value);
		indexmap.put(heap.get(index), index);
	}
     
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
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
				indexmap.put(heap.get(index), index);
				index = child;
			} else break;
		}

		heap.set(index, value);
		indexmap.put(heap.get(index), index);
		assert invariant() : showHeap();
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		//complexity O(1)
		return 2*index+1;
	}

	private final int rightChild(int index) {
		//complexity O(1)
		return 2*index+2;
	}

	private final int parent(int index) {
		//complexity O(1)
		return (index-1)/2;
	}

	public void update(E oldelem, E newelem){
		//complexity O(1)

		assert invariant() : showHeap();
		//get index of our element to replace
		int index = indexmap.get(oldelem);
		//replace previous old element with new element
		heap.set(index, newelem);
		if (heap.size() > 1){
			//time to check the heap when we have replaced an element

			//get the index of parent of our current element
			int parentindex = parent(index);
			//get index left and right children
			int left = leftChild(index);
			int right = rightChild(index);


            //compare our current element with the parent to determine if we need to sift up
			//if parent doesn't exist index should we dont need to sift up
			if (comparator.compare(newelem, heap.get(parentindex)) < 0){
				siftUp(index);
			}
			//compare both children with current element so determine if we need so sift down
			else if (left < heap.size()) {
				if (comparator.compare(newelem, heap.get(left)) > 0) {
					siftDown(index);
				} else if (right < heap.size()) {
					if (comparator.compare(newelem, heap.get(right)) > 0) {
						siftDown(index);
					}
				}
			}
		}
		//update the hashmap with the new element
		indexmap.put(heap.get(index), index);
		assert invariant() : showHeap();
	}


	private boolean invariant() {
		// TODO: return true if and only if the heap invariant is true.
		// if heap is of size 1 the invariant is true
		// complexity O(n)
		if(heap.size() <= 1){
			return true;
		}

		for(int i = 0; i < heap.size(); i++){

            // get current element and corresponding children
			E currentelem = heap.get(i);
			int leftChildIndex = leftChild(i);
			int rightChildIndex = rightChild(i);

			// if current child indexes are outside heap we have reached a leaf, thus the invariant is true
			if(leftChildIndex >= heap.size() || rightChildIndex >= heap.size()){
				break;
			}
			// compare parent value with left child, return false if expected behaviour is not met. (depends on comperator)
			if(comparator.compare(heap.get(i), heap.get(leftChildIndex)) == 1){
				return false;
			}
			// vice versa with right child
			if(comparator.compare(heap.get(i), heap.get(rightChildIndex)) == 1){
				return false;
			}
			// check if the current element returns the same index else fail
			// will also fail if the current element is not in the hashmap since get returns null if an element is not listed as an key
			if (indexmap.get(currentelem) != i){
				return false;
			}
		}

		return true;
	}
	private String showHeap() {
		// TODO: return description of heap contents.
		// complexity O(n)
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < heap.size(); i++) {
			sb.append(heap.get(i));
		}
		return sb.toString();
	}

}
