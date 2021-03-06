public class HashSet<E> {
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final int INITIAL_SIZE = 17;
    // Instance Variables
    private HashEntry<E>[] elementData;
    private int size;

    // Constructor (creates an empty set of initial size capacity)
    @SuppressWarnings("unchecked")
    public HashSet() {
        elementData = (HashEntry<E>[])new HashEntry [INITIAL_SIZE];
        size = 0;
    }

    // Adds the given element to this set, if not already there
    public void add(E value) {
        if (!contains(value)) {
            if (loadFactor() >= MAX_LOAD_FACTOR) {
                rehash();
            }

            // insert at front of list
            int bucket = hashFunction(value);
            elementData[bucket] = new HashEntry<E>(value, elementData[bucket]);
            size++;
        }
    }

    // Removes all elements from the set
    public void clear() {
        for (int i = 0; i < elementData.length; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    // Return whether given value is found in the set
    public boolean contains(E value) {
        int bucket = hashFunction(value);
        HashEntry<E> current = elementData[bucket];
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Returns whether set is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Removes the given value if in the set; if not in set, does nothing
    public void remove(E value) {
        int bucket = hashFunction(value);
        if (elementData[bucket] != null) {
            // check front of list
            if (elementData[bucket].data.equals(value)) {
                elementData[bucket] = elementData[bucket].next;
                size--;
            } else {
                // check rest of list
                HashEntry<E> current = elementData[bucket];
                while (current.next != null && !current.next.data.equals(value)) {
                    current = current.next;
                }

                // if the element is found, remove it
                if (current.next != null && current.next.data.equals(value)) {
                    current.next = current.next.next;
                    size--;
                }
            }
        }
    }

    // Returns the number of elements in the set
    public int size() {
        return size;
    }

    // Returns a string representation of this set, such as "[10, 20, 30]"
    // The elements are not guaranteed to be listed in sorted order
    public String toString() {
        String result = "[";
        boolean first = true;
        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {
                HashEntry<E> current = elementData[i];
                while (current != null) {
                    if (!first) {
                        result += ", ";
                    }
                    result += current.data;
                    first = false;
                    current = current.next;
                }
            }
        }
        return result + "]";
    }

    // Returns the preferred hash bucket index for the given value
    private int hashFunction(E value) {
        return Math.abs(value.hashCode()) % elementData.length;
    }

    // Calculates the load factor (occupied percentage) for this hash set
    private double loadFactor() {
        return (double) size / elementData.length;
    }

    // Resizes the hash table to twice its former size, then rehashes data
    @SuppressWarnings("unchecked")
    private void rehash() {
        // replace element data array with a larger empty version
        HashEntry<E>[] oldElementData = elementData;
        elementData = (HashEntry<E>[])new HashEntry[2 * oldElementData.length];
        size = 0;

        // re-add all of the old data into the new array
        for (int i = 0; i < oldElementData.length; i++) {
            HashEntry<E> current = oldElementData[i];
            while (current != null) {
                add(current.data);
                current = current.next;
            }
        }
    }

    // Represents a single value in a chain stored in one hash bucket
    private static class HashEntry<E> {
        public E data;
        public HashEntry<E> next;

        public HashEntry(E data) {
            this(data, null);
        }

        public HashEntry(E data, HashEntry<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
