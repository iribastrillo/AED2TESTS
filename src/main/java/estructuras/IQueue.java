package estructuras;

public interface IQueue <T> {
    public void enqueue (T data);
    public T dequeue ();
    public T top ();
    public boolean isEmpty ();
}
