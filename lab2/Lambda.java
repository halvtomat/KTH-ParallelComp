package lab2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class Lambda {

	private static int THREADCOUNT = Runtime.getRuntime().availableProcessors();
	private static ForkJoinPool POOL;

	private Queue<int []> createIntervals(int []arr) {
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
		return sortQueue;
	} 

	public void sort(int []arr, int low, int high) {
		Queue<int []> intervals = createIntervals(arr);
		try {
			POOL.submit(() -> 
				intervals.stream().parallel().forEach(interval -> new Sequential().quicksort(arr, interval[0], interval[1]))
			).get();
		} catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private static long test(int arrLength) {
		int[] arr = new int[arrLength];
        for(int i = 0; i < arrLength; i++)
            arr[i] = ThreadLocalRandom.current().nextInt(1001);
        long startTime = System.nanoTime();
        new Lambda().sort(arr, 0, arr.length - 1);
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
		POOL = new ForkJoinPool(THREADCOUNT);
		for(int i = 0; i < runs; i++) 
			duration += test(arrLength);
		duration /= runs;
		System.out.println("average execution time: " + duration/1000000 + " ms");
    }
}
