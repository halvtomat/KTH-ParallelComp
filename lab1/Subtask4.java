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
			System.out.println(id + " is thinking...");
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
		System.out.println(id + " is eating...");
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
			System.out.println(id + " acquired chopstick " + num);
		}
	}

	private static void returnChopstick(int num, int id) {
		synchronized(lock[num]) {
			chopsticks[num] = false;
			System.out.println(id + " returned chopstick " + num);
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
