/**
 * Dining philosophers problem
 * This solution works for any amount of philosophers >= 2 (with less than 2 there are never 2 chopsticks --> deadlock)
 * The philosopher who returns a chopstick will alert the potentially waiting philosopher that the chopstick is up for grabs
 * Timers (Thread.sleep) are added to make the prints readable in real time and make the simulation a little more interesting.
 * The last philosopher is a lefty and picks up the right chopstick first, this eliminates a potential deadlock where everyone has their left chopstick
 * but the right one is occupied.
 * 
 * HOW TO RUN:
 * java Subtask4 <PHILOSOPHER COUNT> 
 */

package lab1;

import java.util.concurrent.ThreadLocalRandom;

public class Subtask4 implements Runnable {
	private static boolean chopsticks[];
	private static Object lock[];
	private boolean lefty;
	private int id;

	public Subtask4(boolean lefty, int id, int philosopherCount) {
		this.lefty = lefty;
		this.id = id;
		if(philosopherCount != -1){
			chopsticks = new boolean[philosopherCount];
			lock = new Object[philosopherCount];
			for(int i = 0; i < philosopherCount; i++)
				lock[i] = new Object();
		}
	}
	
	public void run() {
		while(true) {
			double thinkTime = ThreadLocalRandom.current().nextDouble(0.5, 4.0);
			System.out.println(id + " is THINKING...");
			try {
				Thread.sleep((long)(thinkTime * 1000));
			} catch(InterruptedException e) {}
			eat();
		}
	}

	private void eat() {
		int left = id;
		int right = id > 0 ? id - 1 : chopsticks.length - 1;
		double eatTime = ThreadLocalRandom.current().nextDouble(0.5, 2.0);
		if(lefty) {
			acquireChopstick(left, id);
			acquireChopstick(right, id);
		} else {
			acquireChopstick(right, id);
			acquireChopstick(left, id);
		}
		System.out.println(id + " is EATING...");
		try {
			Thread.sleep((long)(eatTime * 1000));
		} catch(InterruptedException e) {}
		returnChopstick(left, id);
		returnChopstick(right, id);
	}

	private static void acquireChopstick(int num, int id) {
		synchronized(lock[num]) {
			while(chopsticks[num]){
				try {
					lock[num].wait();
				} catch(InterruptedException e) {}
			}
			chopsticks[num] = true;
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {}
			System.out.println(id + " ACQUIRED chopstick " + num);
		}
	}

	private static void returnChopstick(int num, int id) {
		synchronized(lock[num]) {
			chopsticks[num] = false;
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {}
			System.out.println(id + " RETURNED chopstick " + num);
			lock[num].notify();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int philosopherCount = Integer.parseInt(args[0]);
		Thread philosophers[] = new Thread[philosopherCount];
		for(int i = 0; i < philosopherCount; i++) {
			if(i == philosopherCount - 1)
				philosophers[i] = new Thread(new Subtask4(true, i, philosopherCount));
			else
				philosophers[i] = new Thread(new Subtask4(false, i, -1));
		}
		for(Thread phil : philosophers) {
			phil.start();
		}
		for(Thread phil : philosophers) {
			phil.join();
		}
	}	
}
