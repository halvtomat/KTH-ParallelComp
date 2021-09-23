package lecture8;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class lecture8 {
    final static int STEPS = 5000000;
 
    static int i = 0;
 
    static UnsafeCounter c = new UnsafeCounter ();
 
    static Dekker m = new Dekker();
 
    public static void main (String[] args) throws InterruptedException {
		System.out.println("Dekker Counter Demo");
	
		// Spawn THREADS threads...
		Thread[] thread = new Thread [2];
		for (i = 0; i < 2; i++) {
			thread[i] = new Thread (new Runnable () {
				int tid = i;
				public void run () {
					System.out.println("Running: "+tid);
					// ...each thread increments the counter STEP times
					for (int s = 0; s < STEPS; s++) {
						m.Pmutex(tid);
						c.increment();
						m.Vmutex(tid);
					}
				}
			});
			thread[i].start();
      }
 
		// Wait for the threads to terminate
		for (int i = 0; i < 2; i++) thread[i].join();
		// Print the result.
		System.out.println("The final value of the counter is " + c.get() + ".");
    }
}

class UnsafeCounter {
    private int count;
    void increment () { count ++; }
    int get () { return count; }
}

class Dekker {
	public Dekker () {
		flag.set(0,0); flag.set(1,0); turn = 0;
	}
 
  	public void Pmutex(int t) {
		int other;
	
		other = 1-t;
		flag.set(t,1);
		while (flag.get(other) == 1) {
			if (turn == other) {
				flag.set(t,0);
				while (turn == other)
				;
				flag.set(t,1);
			}
    	}
  	}
 
  	public void Vmutex(int t) {
		turn = 1-t;
		flag.set(t,0);
  	}
 
	private volatile int turn;
	private AtomicIntegerArray flag = new AtomicIntegerArray(2);
}