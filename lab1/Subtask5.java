/**
 * Similar to Dining philosophers but only 2 philosophers/chopsticks.
 * Simulation of deadlock.
 * To debug this, start the program, wait for deadlock, send a SIGQUIT signal to the program and read the printout.
 * You will find something like "Thread-0" ... in Object.wait() java.lang.Thread.State: WAITING (on object monitor) ...
 * for both threads, meaning they are both waiting for the other thread to give back the resource -> deadlock.
 */

package lab1;

import java.util.concurrent.ThreadLocalRandom;

public class Subtask5 implements Runnable {
	private static Object lock[] = {new Object(), new Object()};
	private static boolean desirableObject[] = {false, false};
	private static int sleep = 1; // Set to 0 to remove all sleep (this almost certainly means instant deadlock)
	private int id;

	public Subtask5(int id) {
		this.id = id;
	}

	public void run() {
		while(true) {
			try {
				double chillTime = ThreadLocalRandom.current().nextDouble(1.0, 4.0);
				Thread.sleep((long)(chillTime*1000 * sleep));
			} catch(InterruptedException e) {}
			acquireDesirableObject(1 - id, id);
			acquireDesirableObject(id, id);
			try {
				double greedTime = ThreadLocalRandom.current().nextDouble(0.5, 2.0);
				Thread.sleep((long)(greedTime*1000 * sleep));
			} catch(InterruptedException e) {}
			returnDesirableObject(1 - id, id);
			returnDesirableObject(id, id);
		}
	}

	private static void acquireDesirableObject(int num, int id) {
		synchronized(lock[num]) {
			while(desirableObject[num] != false) {
				System.out.println(id + " is waiting for desirableObject[" + num + "]");
				try {
					lock[num].wait();
				} catch(InterruptedException e) {}
			}
			try {
				Thread.sleep(500 * sleep);
			} catch(InterruptedException e) {}
			System.out.println(id + " acquired desirableObject[" + num + "]");
			desirableObject[num] = true;
		}
	}
	
	private static void returnDesirableObject(int num, int id) {
		synchronized(lock[num]) {
			try {
				Thread.sleep(500 * sleep);
			} catch(InterruptedException e) {}
			System.out.println(id + " returned desirableObject[" + num + "]");
			desirableObject[num] = false;
			lock[num].notify();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread A = new Thread(new Subtask5(0));
		Thread B = new Thread(new Subtask5(1));
		A.start();
		B.start();
		A.join();
		B.join();
	}	
}
