package lab2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Executor {

	private static int THREADCOUNT = Runtime.getRuntime().availableProcessors();
	private static ExecutorService SERVICE; 

	private void createThreads(int []arr) {
		Queue<int []> sortQueue = new LinkedList<>();
		int []initialPoint = {0, arr.length - 1};
		sortQueue.add(initialPoint);

		while(sortQueue.size() < THREADCOUNT) {
			int []point = sortQueue.remove();
			if(point[1] < point[0])
				continue;
			int mid = Common.partition(arr, point[0], point[1]);
			int []newPoint1 = {point[0], mid - 1};
			int []newPoint2 = {mid + 1, point[1]};
			sortQueue.add(newPoint1);
			sortQueue.add(newPoint2);
		}
		for(int i = 0; i < THREADCOUNT; i++) {
			int []point = sortQueue.remove();
			SERVICE.execute(new quicksort(arr, point[0], point[1]));
		}
		SERVICE.shutdown();
	} 

	public void sort(int []arr, int low, int high) {
		createThreads(arr);
		try{
			SERVICE.awaitTermination(1, TimeUnit.MINUTES);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	class quicksort implements Runnable {
		final int[] arr;
		final int low;
		final int high;

		quicksort(int []arr, int low, int high) {
			this.arr = arr;
			this.low = low;
			this.high = high;
		}

		public void run() {
			new Sequential().quicksort(arr, low, high);
		}
	}

	private static long test(int arrLength) {
		SERVICE = Executors.newFixedThreadPool(THREADCOUNT);
		int[] arr = new int[arrLength];
        for(int i = 0; i < arrLength; i++)
            arr[i] = ThreadLocalRandom.current().nextInt(1001);
        long startTime = System.nanoTime();
        new Executor().sort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        return endTime - startTime;
	}

	public static void main(String[] args) {
		int runs = 10;
		int arrLength = 10000;
		long duration = 0;
		if(args.length > 0)
			arrLength = Integer.parseInt(args[0]);
		if(args.length > 1)
			runs = Integer.parseInt(args[1]);
		if(args.length > 2)
			THREADCOUNT = Integer.parseInt(args[2]);
		for(int i = 0; i < runs; i++) 
			duration += test(arrLength);
		duration /= runs;
		if(args.length > 3)
			System.out.println(duration/1000000);
		else
			System.out.println("average execution time: " + duration/1000000 + " ms");
    }
}
