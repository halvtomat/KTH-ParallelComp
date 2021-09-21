package lab2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoin {

	private static int THREADCOUNT = Runtime.getRuntime().availableProcessors();
    private static ForkJoinPool POOL = new ForkJoinPool();
    private static final int THRESHOLD = 1000;

    public void sort(int []arr, int low, int high) {
        POOL.invoke(new quicksort(arr, low, high));
    }

    class quicksort extends RecursiveAction {
        private final int[] arr;
        private final int low;
        private final int high;
        quicksort(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        protected void compute() {
            if (high - low < THRESHOLD)
                new Sequential().quicksort(arr, low, high);
            else {
                int mid = Common.partition(arr, low, high);
                invokeAll(new quicksort(arr, low, mid - 1),
                        new quicksort(arr, mid + 1, high));
            }
        }
	}

	private static long test(int arrLength) {
		int[] arr = new int[arrLength];
        for(int i = 0; i < arrLength; i++)
            arr[i] = ThreadLocalRandom.current().nextInt(1001);
        long startTime = System.nanoTime();
        new ForkJoin().sort(arr, 0, arr.length - 1);
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