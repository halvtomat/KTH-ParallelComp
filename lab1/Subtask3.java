package lab1;

public class Subtask3 implements Runnable {
	private final boolean isPrinter;
	private static long counter = 0;
	private static long target = 1000000;
	private static Object lock = new Object();

	public Subtask3(boolean isPrinter) {
		this.isPrinter = isPrinter;
	}

	public void run() {
		if(isPrinter)
			printer();
		else
			incrementer();
	}

	private static void printer() {
		synchronized(lock) {
			while(counter != target){
				try{
					lock.wait();
				} catch(InterruptedException e) {}
			}
			System.out.println("Counter = " + counter);
		}
	}

	private static void incrementer() {
		synchronized(lock) {
			for(int i = 0; i < target; i++)
				counter++;
			lock.notify();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread printer = new Thread(new Subtask3(true));
		Thread incrementer = new Thread(new Subtask3(false));

		incrementer.start();
		printer.start();
		incrementer.join();
		printer.join();
	}
}
