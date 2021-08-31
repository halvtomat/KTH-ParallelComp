package lab1;

public class Subtask1 implements Runnable {
	private int threadNumber;
	private static Object lock = new Object();

	public Subtask1(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public void run() {
		printHello();
	}

	private void printHello() {
		synchronized(lock) {
			System.out.println("Hello World from " + threadNumber);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread threads[] = new Thread[5];
		for(int i = 0; i < 5; i++) { 
			threads[i] = new Thread(new Subtask1(i));
			threads[i].start();
		}
		for(Thread thread : threads) 
			thread.join();
	}
}