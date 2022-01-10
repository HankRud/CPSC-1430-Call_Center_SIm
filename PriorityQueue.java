import java.util.Arrays;


public class PriorityQueue<E extends Comparable<E>> {
    private E[] elementData;
    private int size;

    // Constructs an empty queue.

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        elementData = (E[]) new Comparable [10];
        size = 0;
    }

    // Adds the given element to this queue.
    public void add(E value) {
        // resize if necessary
        if (size + 1 >= elementData.length) {
            elementData = Arrays.copyOf(elementData, elementData.length * 2);
        }

        // insert as new rightmost leaf
        elementData[size + 1] = value;

        // "bubble up" toward root as necessary to fix ordering
        int index = size + 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasParent(index)) {
            int parent = parent(index);
            if (elementData[index].compareTo(elementData[parent]) < 0) {
                swap(elementData, index, parent(index));
                index = parent(index);
            } else {
                found = true;  // found proper location; stop the loop
            }
        }

        size++;
    }



    // Returns the minimum value in the queue without modifying the queue.
    // If the queue is empty, throws a NoSuchElementException.
    public E peek() {

        return elementData[1];
    }

    // Removes and returns the minimum value in the queue.
    // If the queue is empty, throws a NoSuchElementException.
    public void remove() {
        E result = peek();

        // move rightmost leaf to become new root
        elementData[1] = elementData[size];
        size--;

        // "bubble down" root as necessary to fix ordering
        int index = 1;
        boolean found = false;   // have we found the proper place yet?
        while (!found && hasLeftChild(index)) {
            int left = leftChild(index);
            int right = rightChild(index);
            int child = left;
            if (hasRightChild(index) &&
                    elementData[right].compareTo(elementData[left]) < 0) {
                child = right;
            }

            if (elementData[index].compareTo(elementData[child]) > 0) {
                swap(elementData, index, child);
                index = child;
            } else {
                found = true;  // found proper location; stop the loop
            }
        }

    }

    // Returns the number of elements in the queue.
    public int size() {
        return size;
    }




    // helpers for navigating indexes up/down the tree
    private int parent(int index) {
        return index / 2;
    }

    // returns index of left child of given index
    private int leftChild(int index) {
        return index * 2;
    }

    // returns index of right child of given index
    private int rightChild(int index) {
        return index * 2 + 1;
    }

    // returns true if the node at the given index has a parent (is not the root)
    private boolean hasParent(int index) {
        return index > 1;
    }

    // returns true if the node at the given index has a non-empty left child
    private boolean hasLeftChild(int index) {
        return leftChild(index) <= size;
    }

    // returns true if the node at the given index has a non-empty right child
    private boolean hasRightChild(int index) {
        return rightChild(index) <= size;
    }

    // switches the values at the two given indexes of the given array
    private void swap(E[] a, int index1, int index2) {
        E temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }
}
