package skiplists;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 A SkipList is a collection of sorted linked lists, which mimics, in a subtle way, a balanced search tree.

 To maintain the skiplist property, a node is not considered to be logically in the set until all references to it at all levels have been properly set.

 Each node has an additional flag, fullyLinked, set to true once it has been linked in all its levels

 We do not allow access to a node until it is fully linked
 */
public class LocalRecordList<T> {

    private final int MAX_LEVEL = Constants.MAX_LEVEL;
    final Node<T> head = new Node<T>(Integer.MIN_VALUE);
    final Node<T> tail = new Node<T>(Integer.MAX_VALUE);
    Node<T> current = null;
    private HashMap<Long,Recorder<T>> localRecorders = new HashMap<Long, Recorder<T>>();
    private Recorder<T> recorder = new Recorder<T>();

    public LocalRecordList() {
        for (int i = 0; i < head.next.length; i++)
            head.next[i] = new AtomicMarkableReference<Node<T>>(tail, false);
        for(long i = 0; i < 100; i++)
            localRecorders.putIfAbsent(i, new Recorder<T>());
    }

    public T getNextValue() {
        if(current == null)
            current = head.next[0].getReference();
        if(current == tail) {
            current = null;
            return null;
        }
        T currentValue = current.getValue();
        if (current.hasNext())
            current = current.next[0].getReference();
        return currentValue;
    }

    public boolean add(T x) {
        localRecorders.putIfAbsent(Thread.currentThread().getId(), new Recorder<T>());
        int topLevel = randomLevel();
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        while (true) {
            boolean found = find(x, preds, succs);
            if (found){
                localRecorders.get(Thread.currentThread().getId()).addSample(1, x, false);
                return false;
            } else {
                Node<T> newNode = new Node<T>(x, topLevel);
                for (int level = bottomLevel; level <= topLevel; level++) {
                    Node<T> succ = succs[level];
                    newNode.next[level].set(succ, false);
                }
                Node<T> pred = preds[bottomLevel];
                Node<T> succ = succs[bottomLevel];
                if(pred.next[bottomLevel].compareAndSet(succ, newNode, false, false)) 
                    localRecorders.get(Thread.currentThread().getId()).addSample(1, x, true);
                else
                    continue;
                for (int level = bottomLevel+1; level <= topLevel; level++) {
                    while (true) {
                        pred = preds[level];
                        succ = succs[level];
                        if (pred.next[level].compareAndSet(succ, newNode, false, false))
                            break;
                        find(x, preds, succs);
                    }
                }
                return true;
            }
        }
    }   

    public boolean populate(T x) {
        int topLevel = randomLevel();
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        while (true) {
            boolean found = find(x, preds, succs); 
            if (found)
                return false;
            else {
                Node<T> newNode = new Node<T>(x, topLevel);
                for (int level = bottomLevel; level <= topLevel; level++) {
                    Node<T> succ = succs[level];
                    newNode.next[level].set(succ, false);
                }
                Node<T> pred = preds[bottomLevel];
                Node<T> succ = succs[bottomLevel];
                if (!pred.next[bottomLevel].compareAndSet(succ, newNode, false, false))
                    continue;
                for (int level = bottomLevel+1; level <= topLevel; level++) {
                    while (true) {
                        pred = preds[level];
                        succ = succs[level];
                        if (pred.next[level].compareAndSet(succ, newNode, false, false))
                            break;
                        find(x, preds, succs);
                    }
                }
                return true;
            }
        }
    }

    private int randomLevel() {
        int lvl = (int)(Math.log(1.-Math.random())/Math.log(1.-0.5));
        return Math.min(lvl, MAX_LEVEL);
    }

    public boolean remove(T x) {
        localRecorders.putIfAbsent(Thread.currentThread().getId(), new Recorder<T>());
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T> succ;
        while (true) {
            boolean found = find(x, preds, succs);
            if(!found) {
                localRecorders.get(Thread.currentThread().getId()).addSample(2, x, false);
                return false;
            } else {
                Node<T> nodeToRemove = succs[bottomLevel];
                for (int level = nodeToRemove.topLevel; level >= bottomLevel+1; level--) {
                    boolean[] marked = {false};
                    succ = nodeToRemove.next[level].get(marked);
                    while (!marked[0]) {
                        nodeToRemove.next[level].compareAndSet(succ, succ, false, true);
                        succ = nodeToRemove.next[level].get(marked);
                    }
                }
                boolean[] marked = {false};
                succ = nodeToRemove.next[bottomLevel].get(marked);
                while (true) {
                    boolean iMarkedIt = nodeToRemove.next[bottomLevel].compareAndSet(succ, succ, false, true);
                    if (iMarkedIt)
                        localRecorders.get(Thread.currentThread().getId()).addSample(2, x, true);
                    succ = succs[bottomLevel].next[bottomLevel].get(marked);
                    if (iMarkedIt) {
                        find(x, preds, succs);
                        return true;
                    }
                    else if (marked[0])
                        return false;
                }
            }
        }
    }

    private boolean find(T x, Node<T>[] preds, Node<T>[] succs) {
        int bottomLevel = 0;
        int key = x.hashCode();
        boolean[] marked = {false};
        boolean snip;
        Node<T> pred = null, curr = null, succ = null;
        retry:
        while (true) {
            pred = head;
            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
                curr = pred.next[level].getReference();
                while (true) {
                    succ = curr.next[level].get(marked);
                    while (marked[0]) {
                        snip = pred.next[level].compareAndSet(curr, succ,false, false);
                        if (!snip)
                            continue retry;
                        curr = pred.next[level].getReference();
                        succ = curr.next[level].get(marked);
                    }
                    if (curr.key < key) {
                        pred = curr;
                        curr = succ;
                    } else
                        break;
                }
                preds[level] = pred;
                succs[level] = curr;
            }
            return (curr.key == key);
        }
    }

    public boolean contains(T x) {
        localRecorders.putIfAbsent(Thread.currentThread().getId(), new Recorder<T>());
        long linearizationTime = 0;
        int bottomLevel = 0;
        int v = x.hashCode();
        boolean[] marked = {false};
        Node<T> pred = head, curr = null, succ = null;
        for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
            curr = pred.next[level].getReference();
            linearizationTime = System.nanoTime();
            while (true) {
                succ = curr.next[level].get(marked);
                while (marked[0]) {
                    curr = pred.next[level].getReference();
                    linearizationTime = System.nanoTime();
                    succ = curr.next[level].get(marked);
                }
                if (curr.key < v) {
                    pred = curr;
                    curr = succ;
                } else
                    break;
            }
        }
        localRecorders.get(Thread.currentThread().getId()).addSample(linearizationTime, 0, x, (curr.key == v));
        return (curr.key == v);
    }

    public Recorder<T> getRecorder() {
        for (Long key : localRecorders.keySet()) {
            Recorder<T> localRecorder = localRecorders.get(key);
            for(Sample<T> s : localRecorder.getSamples())
                recorder.addSample(s);
        }
        recorder.sort();
        return recorder;
    }
}
