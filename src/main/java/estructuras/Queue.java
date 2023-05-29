package estructuras;

public class Queue <T> implements IQueue <T> {

    public Node start;
    public int length;

    public Queue () {
        this.length = 0;
    }

    @Override
    public void enqueue(T data) {
        Node node = new Node (data);
        if (this.start != null) {
            node.next = this.start;
        }
        this.start = node;
        length ++;
    }
    @Override
    public T dequeue() {
        if (!this.isEmpty()) {
            Node current = start;
            Node previous = null;
            while (current.next != null) {
                previous = current;
                current = current.next;
            }
            if (previous != null) {previous.next = null;}
            else {start = null;}
            length --;
            return current.data;
        }
        return null;
    }

    @Override
    public T top() {
        return this.start.data;
    }

    @Override
    public boolean isEmpty() { return length == 0;}

    private class Node {
        public T data;
        public Node next;

        public Node (T data) {
            this.data = data;
            this.next = null;
        }
    }
}
