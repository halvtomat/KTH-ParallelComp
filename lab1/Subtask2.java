package lab1;

public class Subtask2 implements Runnable {
	private final int incrementations;
	private static long counter = 0;
	private static Object lock = new Object();

	public Subtask2(int incrementations) {
		this.incrementations = incrementations;
	}

	public void run() {
		for(int i = 0; i < incrementations; i++)
			safeIncrement();
	}

	private static void safeIncrement() {
		synchronized(lock) { // Remove this line to unsync
			counter++;
		} // Also this line
	}

	public static void main(String[] args) throws InterruptedException {
		int increments = 1000000;
		int cores = Runtime.getRuntime().availableProcessors();
		Thread threads[] = new Thread[cores];
		for(int i = 0; i < cores; i++) {
			threads[i] = new Thread(new Subtask2(increments));
			threads[i].start();
		}
		for(Thread thread : threads)
			thread.join();
		System.out.println("Counter should be = " + increments*cores);
		System.out.println("Counter = " + counter);
	}
}
