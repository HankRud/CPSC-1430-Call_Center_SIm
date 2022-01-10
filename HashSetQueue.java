public class HashSetQueue<E> {
    private Node<E> front;
    private Node<E> rear;
    private int size;
    private final HashSet<E> set= new HashSet<>();


    /** checks if Queue is empty
     * @return
     * */
    public boolean isEmpty(){
        return front == null;
    }

    public E peek(){
        return  front.data;
    }

    /** adds the given data to to the rear
     *
     * @param data   the data
     */
    public void add(E data){
        if (! set.contains(data )) {
            set.add(data);
            Node<E> node = new Node <> (data);
            if (rear != null) {
                rear.next = node;
            }
            rear = node;
            if (front == null) {
                front = node;
            }
            size++;
        }

    }

    /** removes the front node if contained*/
    public void remove(){
            set.remove( front.data);
            front = front.next;
            if (front == null) {
                rear = null;
            }
            size--;

    }

    /** returns the size
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /** clears the Queue
     *
     */
    public void clear(){
        while (front != null && rear != null){
            remove();
        }
    }

    /**Internal Node class
     */
    private static class Node<E>{
        private E data;
        private Node<E> next;


        /** Node Constructor
         * @param data   the data
         */
        private Node( E data){
            this.data= data;
        }
    }

}
