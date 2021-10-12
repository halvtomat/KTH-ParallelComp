package skiplists;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node<T> {

    private T value;
    int key = 0;
    private final int MAX_LEVEL = Constants.MAX_LEVEL;
    AtomicMarkableReference<Node<T>>[] next;
    int topLevel;

    // constructor for sentinel nodes
    public Node(int key) {
        this.value = null;
        this.key = key;
        this.next = (AtomicMarkableReference<Node<T>>[]) new AtomicMarkableReference[this.MAX_LEVEL + 1];
        for (int i = 0; i < this.next.length; i++) {
            next[i] = new AtomicMarkableReference<Node<T>>(null,false);
        }
        this.topLevel = this.MAX_LEVEL;
    }

    public boolean hasNext(){
        return next != null;
    }

    // constructor for ordinary nodes
    public Node(T x, int height) {
        this.value = x;
        this.key = x.hashCode();
        this.next = (AtomicMarkableReference<Node<T>>[]) new AtomicMarkableReference[height + 1];
        for (int i = 0; i < this.next.length; i++) {
            next[i] = new AtomicMarkableReference<Node<T>>(null,false);
        }
        this.topLevel = height;
    }

    public T getValue() {
        return value;
    }
}