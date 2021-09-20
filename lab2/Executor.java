package lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Executor {

	private static final int threadCount = Runtime.getRuntime().availableProcessors();
	private static final ExecutorService service = Executors.newFixedThreadPool(threadCount);

	private void createThreads() {} 
	//skapa threadCount threads med lika fördelning
	//kanske ha en counter för hur många delar och fortsätt breadth first 
	//tills det är threadCount stycken sen kör execute på dem
	//typ som breadth first search med en queue och sådär

	public void sort(int []arr, int low, int high) {

		service.execute(new quicksort(arr, low, high));
		service.shutdown();
		try{
			service.awaitTermination(1, TimeUnit.MINUTES);
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
		for(int i = 0; i < runs; i++) 
			duration += test(arrLength);
		duration /= runs;
		System.out.println("average execution time: " + duration/1000000 + " ms");
    }
}
